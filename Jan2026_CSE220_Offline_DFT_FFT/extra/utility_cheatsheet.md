# DFT/FFT Offline Utility Cheat Sheet

This sheet covers the helpers you call while completing `transforms.py`,
`bigmul.py`, and `image_conv.py`. You do not need to memorize their internals.

## Provided I/O helpers (`io_utils.py`)

### `read_operands(path)`

- Parameters: `path` — input text-file path.
- Returns: `(text_a, text_b)`, two signed decimal strings. Blank lines and
  comments beginning with `#` are ignored.
- Example: `a, b = read_operands("inputs/1.txt")`

### `random_decimal(num_digits, seed)`

- Parameters: exact digit count and a reproducible random seed.
- Returns: a positive decimal string with no leading zero.
- Example: `a = random_decimal(200, seed=12)`

### `write_text(path, text)`

- Parameters: destination path and text.
- Returns: the same `path`; creates parent directories and adds a final newline.
- Example: `write_text("outputs/product.txt", product)`

### `write_report(path, lines)`

- Parameters: destination path and a sequence of values/lines.
- Returns: the same `path`; writes one item per line.
- Example: `write_report("outputs/report.txt", ["method: fft", "MATCH"])`

## Provided image helpers (`image_utils.py`)

### `load_image(path, as_gray=False)`

- Parameters: image path; `as_gray=True` requests grayscale.
- Returns: `float64` array in `[0, 1]`, shape `(H, W)` for grayscale or
  `(H, W, 3)` for RGB.
- Example: `image = load_image("images/skyline512.png", as_gray=True)`

### `save_image(array, path)`

- Parameters: grayscale/RGB numeric array and destination path.
- Returns: the path; clips values to `[0, 1]` and saves an 8-bit image.
- Example: `save_image(blurred, "outputs/blurred.png")`

### `make_kernel(name, **parameters)`

- Parameters: kernel name plus its settings.
- Returns: a normalized 2D `float64` kernel whose sum is 1.
- Examples:

```python
bokeh = make_kernel("bokeh", radius=9)
gaussian = make_kernel("gaussian", size=21)
box = make_kernel("box", size=9)
motion = make_kernel("motion", length=41, angle=30.0)
```

### `save_comparison(images, titles, out_path, suptitle=None)`

- Parameters: image list, matching title list, output path, optional main title.
- Returns: the output path; saves the images side by side.
- Example: `save_comparison([original, blurred], ["original", "blurred"], "comparison.png")`

### `save_kernel_preview(kernel, out_path, title="kernel")`

- Parameters: kernel array, output path, optional title.
- Returns: the output path; saves a magnified kernel visualization.
- Example: `save_kernel_preview(kernel, "kernel.png", title="bokeh kernel")`

## Provided benchmark helpers (`bench_utils.py`)

### `time_best(fn, repeats=3)`

- Parameters: zero-argument function and number of repetitions.
- Returns: smallest measured running time in seconds.
- Example: `seconds = time_best(lambda: multiply(a, b, "fft"), repeats=2)`

### `plot_runtime_curve(series, out_path, title, xlabel, ylabel=..., references=())`

- Parameters: `series` maps labels to `(x_values, y_values)`; remaining
  parameters configure the saved graph. Reference choices include `"n"`,
  `"nlogn"`, `"n2"`, `"n3"`, and `"n4"`.
- Returns: the output path.
- Example:

```python
plot_runtime_curve(
    {"FFT": ([128, 256], [0.001, 0.002])},
    "runtime.png",
    title="Runtime",
    xlabel="input size",
    references=("nlogn",),
)
```

### `timing_table_lines(series, size_label="size")`

- Parameters: the same `series` dictionary used for plotting and a first-column label.
- Returns: list of formatted strings for `report.txt`.
- Example: `lines = timing_table_lines(series, size_label="digits")`

## Transform helpers (`transforms.py`)

### `next_power_of_two(n)`

- Parameters: integer `n`.
- Returns: smallest power of two greater than or equal to `n` (at least 1).
- Example: `next_power_of_two(99)  # 128`

### `engine.transform(x)`

- Parameters: one-dimensional real or complex sequence.
- Returns: `complex128` frequency spectrum of the same length.
- Example: `X = FFTTransformer().transform(x)`
- Note: `FFTTransformer` requires a nonzero power-of-two length;
  `DFTAnalyzer` and `ArbitraryLengthFFT` accept other lengths.

### `engine.inverse(spectrum)`

- Parameters: one-dimensional complex spectrum.
- Returns: reconstructed `complex128` sequence of the same length, including
  the required `1/N` scaling.
- Example: `x_reconstructed = engine.inverse(X)`

## NumPy essentials used in the TODO code

### `np.asarray(value, dtype=None)`

- Parameters: array-like value and optional data type.
- Returns: a NumPy array, avoiding a copy when possible.
- Example: `x = np.asarray(x, dtype=np.complex128)`

### `np.array(value, dtype=None)`

- Parameters: value and optional data type.
- Returns: a new NumPy array (normally makes a copy).
- Example: `limbs = np.array([6789, 2345, 1], dtype=np.int64)`

### `np.zeros(shape, dtype=float)`

- Parameters: output shape and optional data type.
- Returns: a new zero-filled array.
- Example: `padded = np.zeros(128, dtype=np.complex128)`

### `np.zeros_like(array, dtype=None)`

- Parameters: model array and optional replacement data type.
- Returns: zero-filled array with the model's shape.
- Example: `result = np.zeros_like(image, dtype=np.float64)`

### `np.empty_like(array, dtype=None)`

- Parameters: model array and optional replacement data type.
- Returns: uninitialized array with the model's shape; fill every entry before reading it.
- Example: `spectrum = np.empty_like(plane, dtype=np.complex128)`

### `np.arange(stop)` / `np.arange(start, stop, step)`

- Parameters: integer range boundaries and optional step.
- Returns: evenly spaced array, excluding `stop`.
- Example: `indices = np.arange(N)  # [0, 1, ..., N-1]`

### `np.exp(values)`

- Parameters: real or complex array/scalar.
- Returns: elementwise exponential; used to build DFT twiddle factors.
- Example: `twiddles = np.exp(-2j * np.pi * np.arange(N) / N)`

### `np.conjugate(values)`

- Parameters: numeric array/scalar.
- Returns: complex conjugate (`a + bj` becomes `a - bj`).
- Example: `x = np.conjugate(engine.transform(np.conjugate(X))) / len(X)`

### `np.concatenate(arrays)`

- Parameters: sequence of arrays; optional `axis` defaults to 0.
- Returns: arrays joined along the chosen axis.
- Example: `result = np.concatenate((even + odd, even - odd))`

### `np.roll(array, shift, axis=None)`

- Parameters: array, circular shift amount(s), and axis/axes.
- Returns: shifted copy with values wrapping around the boundaries.
- Example: `kernel = np.roll(kernel, shift=(-(kh // 2), -(kw // 2)), axis=(0, 1))`

### `np.rint(values)`

- Parameters: numeric array/scalar.
- Returns: floating-point values rounded to the nearest integers.
- Example: `np.rint([2.00000001, 2.99999999])  # [2., 3.]`

### `array.astype(dtype)`

- Parameters: target data type.
- Returns: a copy converted to that type.
- Example: `coefficients = rounded.astype(np.int64)`

### `array.real`

- Parameters: none; this is an attribute, not a function.
- Returns: the real components of a complex array.
- Example: `real_result = convolution.real`

### `np.abs(values)` and `np.max(values)`

- Parameters: numeric array/scalar.
- Returns: elementwise magnitude, and the largest array entry, respectively.
- Example: `error = np.max(np.abs(spectral - direct))`

### `np.real_if_close(values)`

- Parameters: complex-looking numeric result.
- Returns: a real array if imaginary parts are only tiny rounding noise;
  otherwise returns the complex array.
- Example: `result = np.real_if_close(result)`

### `np.clip(values, low, high)`

- Parameters: array and lower/upper limits.
- Returns: array with every value restricted to the interval.
- Example: `safe_image = np.clip(image, 0.0, 1.0)`

## Small Python/path helpers

### `len(value)`

- Parameter: sequence/array.
- Returns: number of items along its first dimension.
- Example: `result_length = len(a) + len(b) - 1`

### `enumerate(sequence)`

- Parameter: iterable; optional starting index.
- Returns: `(index, value)` pairs.
- Example: `for i, limb in enumerate(a): ...`

### `reversed(sequence)`

- Parameter: reversible sequence.
- Returns: iterator from last item to first.
- Example: `for limb in reversed(limbs): ...`

### `divmod(value, base)`

- Parameters: dividend and divisor.
- Returns: `(quotient, remainder)`; useful for carry propagation.
- Example: `carry, digit = divmod(12345, 10000)  # (1, 2345)`

### `zip(a, b)`

- Parameters: two or more iterables.
- Returns: tuples containing corresponding items; stops at the shortest input.
- Example: `for size, seconds in zip(sizes, times): ...`

### `os.path.join(*parts)`

- Parameters: path components.
- Returns: one platform-correct path string.
- Example: `path = os.path.join(out_dir, "report.txt")`

### `os.path.basename(path)`

- Parameter: path.
- Returns: final filename component.
- Example: `os.path.basename("images/skyline512.png")  # "skyline512.png"`

### `os.makedirs(path, exist_ok=True)`

- Parameters: directory path and whether an existing directory is acceptable.
- Returns: `None`; creates missing parent directories.
- Example: `os.makedirs(out_dir, exist_ok=True)`

## Three lines worth remembering

```python
# Frequency-domain convolution
result = engine.inverse(engine.transform(a) * engine.transform(b))

# Remove floating-point noise when exact integer coefficients are expected
coefficients = np.rint(result.real).astype(np.int64)

# Verification error for two numeric arrays
maximum_error = np.max(np.abs(actual - expected))
```
