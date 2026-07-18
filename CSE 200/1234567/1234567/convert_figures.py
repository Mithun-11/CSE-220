#!/usr/bin/env python3
"""
convert_figures.py

Converts the original arXiv figure PDFs (from figure.zip) into the PNG files
that main.tex expects, using the exact filenames referenced in the document.

Some slots in main.tex correspond to MULTIPLE source PDFs, because the paper
composes them into one figure (multi-panel plots, or a plot plus a shared
legend strip). Those are stitched together here.

USAGE
-----
    pip install pymupdf pillow
    python convert_figures.py

It renders with PyMuPDF if installed, and otherwise falls back to `pdftoppm`
(from poppler-utils, which ships with most TeX/Linux setups and is available on
macOS via `brew install poppler`). So you only strictly need Pillow.

By default it reads from ./figure/ and writes to ./figures/.
Override with:
    python convert_figures.py --src path/to/figure --dst path/to/figures

DPI: 300 by default (crisp in print). Use --dpi 200 for a smaller PDF.
"""

import argparse
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path

try:
    from PIL import Image
except ImportError:
    sys.exit("Missing dependency. Run:  pip install pillow")

# Prefer PyMuPDF; fall back to poppler's pdftoppm if it isn't installed.
try:
    import fitz  # PyMuPDF

    _BACKEND = "pymupdf"
except ImportError:
    if shutil.which("pdftoppm"):
        _BACKEND = "poppler"
    else:
        sys.exit(
            "Need a PDF renderer. Either:\n"
            "  pip install pymupdf\n"
            "or install poppler-utils (provides pdftoppm):\n"
            "  Linux: sudo apt install poppler-utils\n"
            "  macOS: brew install poppler"
        )


# ---------------------------------------------------------------------------
# Mapping: output PNG  ->  (list of source PDFs, stacking direction)
#
#   "v" = stack vertically (panels above/below, or plot + legend underneath)
#   "h" = stack horizontally (panels side by side)
#
# A single-element list is just a straight conversion; direction is ignored.
# ---------------------------------------------------------------------------
FIGURE_MAP = {
    # Fig 1: transformer diagram with the compute/memory/network legend beneath
    "fig1_transformer.png": (["model", "nano-batch"], "v"),

    # Fig 2: network time vs compute time heatmap
    "fig2_net_vs_compute.png": (["Ratio_compute_network_old"], "v"),

    # Fig 3: compute time vs memory time heatmap
    "fig3_mem_vs_compute.png": (["classification_new"], "v"),

    # Fig 4: sequential pipeline of existing systems ("WASTED" bubbles)
    "fig4_existing_pipeline.png": (["traditional_pipeline"], "v"),

    # Fig 5: GEMM/GEMV interference curve
    "fig5_interference.png": (["gemm_gemv_512_4096_4096_384_new"], "v"),

    # Fig 6: NanoFlow's generated LLaMA-2-70B pipeline
    "fig6_nanoflow_pipeline.png": (["70B_pipeline"], "v"),

    # Fig 7: offline throughput -- legend on top, then (a) constant, (b) dataset
    "fig7_throughput.png": ([
        "offline_throughput_legend",
        "llama-2-70b-8GPU_offline_throughput",
        "llama-2-70b-8GPU_offline_dataset_throughput",
    ], "v"),

    # Fig 8: latency -- legend on top, then the three datasets side by side
    "fig8_latency.png": ([
        "online_throughput_legend",
        [
            "llama-2-70b-8GPU-Splitwise_online_throughput",
            "llama-2-70b-8GPU-LMSYS-Chat_online_throughput",
            "llama-2-70b-8GPU-ShareGPT_online_throughput",
        ],
    ], "v"),

    # Fig 9: ablation study
    "fig9_ablation.png": (["llama-2-70b-8GPU_ablation_study"], "v"),

    # Fig 10: resource usage -- non-overlap baseline vs NanoFlow, side by side
    "fig10_resource_usage.png": ([
        "non_overlap_baseline_resources_usage",
        "nanoflow_resources_usage",
    ], "h"),

    # Fig 11: performance on other LLMs
    "fig11_other_models.png": (["porting_comparison"], "v"),
}

WHITE = (255, 255, 255)
PAD = 12  # px of whitespace between stitched panels


def render(pdf_path: Path, dpi: int) -> Image.Image:
    """Render page 1 of a PDF to a white-background RGB PIL image."""
    if _BACKEND == "pymupdf":
        doc = fitz.open(pdf_path)
        pix = doc[0].get_pixmap(dpi=dpi, alpha=False)
        img = Image.frombytes("RGB", (pix.width, pix.height), pix.samples)
        doc.close()
        return img

    # poppler fallback
    with tempfile.TemporaryDirectory() as tmp:
        prefix = Path(tmp) / "page"
        subprocess.run(
            ["pdftoppm", "-png", "-r", str(dpi),
             "-f", "1", "-l", "1", str(pdf_path), str(prefix)],
            check=True, capture_output=True,
        )
        produced = sorted(Path(tmp).glob("page*.png"))
        if not produced:
            raise RuntimeError(f"pdftoppm produced no output for {pdf_path.name}")
        return Image.open(produced[0]).convert("RGB")


def stack(images, direction: str) -> Image.Image:
    """Stack images vertically ('v') or horizontally ('h'), centered, padded."""
    images = [im for im in images if im is not None]
    if not images:
        raise ValueError("nothing to stack")
    if len(images) == 1:
        return images[0]

    if direction == "h":
        height = max(im.height for im in images)
        width = sum(im.width for im in images) + PAD * (len(images) - 1)
        canvas = Image.new("RGB", (width, height), WHITE)
        x = 0
        for im in images:
            canvas.paste(im, (x, (height - im.height) // 2))  # vertically center
            x += im.width + PAD
    else:
        width = max(im.width for im in images)
        height = sum(im.height for im in images) + PAD * (len(images) - 1)
        canvas = Image.new("RGB", (width, height), WHITE)
        y = 0
        for im in images:
            canvas.paste(im, ((width - im.width) // 2, y))  # horizontally center
            y += im.height + PAD

    return canvas


def build(entry, src: Path, dpi: int) -> Image.Image:
    """
    Build one output image from a spec entry.

    A spec item is either a stem (str) or a nested list of stems, in which case
    that group is stacked along the OPPOSITE axis first. This is what lets Fig 8
    place a full-width legend above a row of three side-by-side panels.
    """
    stems, direction = entry
    opposite = "h" if direction == "v" else "v"

    parts = []
    for item in stems:
        if isinstance(item, list):
            group = [render(src / f"{s}.pdf", dpi) for s in item]
            parts.append(stack(group, opposite))
        else:
            parts.append(render(src / f"{item}.pdf", dpi))

    return stack(parts, direction)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--src", default="figure", help="folder of source PDFs")
    ap.add_argument("--dst", default="figures", help="folder for output PNGs")
    ap.add_argument("--dpi", type=int, default=300, help="render resolution")
    args = ap.parse_args()

    src, dst = Path(args.src), Path(args.dst)
    if not src.is_dir():
        sys.exit(f"Source folder not found: {src.resolve()}")
    dst.mkdir(parents=True, exist_ok=True)

    ok = 0
    for out_name, entry in FIGURE_MAP.items():
        # Check every source PDF this slot needs actually exists
        needed = []
        for item in entry[0]:
            needed.extend(item if isinstance(item, list) else [item])
        missing = [s for s in needed if not (src / f"{s}.pdf").is_file()]
        if missing:
            print(f"SKIP  {out_name:<28} missing: {', '.join(missing)}")
            continue

        try:
            img = build(entry, src, args.dpi)
            img.save(dst / out_name, "PNG", optimize=True)
            note = f"({len(needed)} panels stitched)" if len(needed) > 1 else ""
            print(f"OK    {out_name:<28} {img.width}x{img.height}  {note}")
            ok += 1
        except Exception as exc:
            print(f"FAIL  {out_name:<28} {exc}")

    print(f"\n{ok}/{len(FIGURE_MAP)} figures written to {dst.resolve()}")


if __name__ == "__main__":
    main()
