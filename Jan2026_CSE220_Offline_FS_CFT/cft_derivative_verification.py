import numpy as np
import matplotlib.pyplot as plt


def numerical_cft(signal, t, frequencies):
    """Numerically evaluate X(f) = integral x(t)e^(-j2*pi*f*t) dt."""
    ft = frequencies[:, None] * t[None, :]
    kernel = np.exp(-1j * 2 * np.pi * ft)
    return np.trapezoid(signal[None, :] * kernel, t, axis=-1)


def wrapped_phase_mse(direct, predicted, magnitude_threshold=1e-6):
    """Phase MSE only where phase is meaningful, using wrapped angle error."""
    scale = max(np.max(np.abs(direct)), np.max(np.abs(predicted)))
    mask = (np.abs(direct) > magnitude_threshold * scale) & (
        np.abs(predicted) > magnitude_threshold * scale
    )
    if not np.any(mask):
        return np.nan

    phase_error = np.angle(
        np.exp(1j * (np.angle(direct[mask]) - np.angle(predicted[mask])))
    )
    return np.mean(phase_error**2)


def main():
    # x(t) has common period pi. Observe 20 complete periods.
    observation_length = 20 * np.pi
    t = np.linspace(
        -observation_length / 2,
        observation_length / 2,
        12001,
        endpoint=True,
    )

    # Frequencies k/L make the complex kernels equal at both window endpoints.
    k = np.arange(-100, 101)
    f = k / observation_length

    # Signal and its first three analytical derivatives.
    x = 0.5 * np.cos(4 * t) + 0.5 * np.sin(6 * t)
    y1 = -2 * np.sin(4 * t) + 3 * np.cos(6 * t)
    y2 = -8 * np.cos(4 * t) - 18 * np.sin(6 * t)
    y3 = 32 * np.sin(4 * t) - 108 * np.cos(6 * t)

    X = numerical_cft(x, t, f)
    direct_transforms = [
        numerical_cft(y1, t, f),
        numerical_cft(y2, t, f),
        numerical_cft(y3, t, f),
    ]

    # Derivative theorem: F{x^(m)(t)} = (j*2*pi*f)^m X(f)
    predicted_transforms = [
        (1j * 2 * np.pi * f) ** order * X for order in range(1, 4)
    ]

    print("Derivative-property verification")
    for order, (direct, predicted) in enumerate(
        zip(direct_transforms, predicted_transforms), start=1
    ):
        magnitude_mse = np.mean((np.abs(direct) - np.abs(predicted)) ** 2)
        phase_mse = wrapped_phase_mse(direct, predicted)
        complex_mse = np.mean(np.abs(direct - predicted) ** 2)
        print(
            f"Order {order}: magnitude MSE={magnitude_mse:.6e}, "
            f"phase MSE={phase_mse:.6e}, complex MSE={complex_mse:.6e}"
        )

    # Plot X(f).
    fig_x, ax_x = plt.subplots(figsize=(9, 4))
    ax_x.plot(f, np.abs(X), color="black")
    ax_x.set_title(r"Numerical magnitude spectrum $|X(f)|$")
    ax_x.set_xlabel("Frequency f (Hz)")
    ax_x.set_ylabel("Magnitude")
    ax_x.grid(alpha=0.3)
    fig_x.tight_layout()
    fig_x.savefig("x_magnitude_spectrum.png", dpi=160)

    # Required magnitude and phase overlays for derivatives 1, 2 and 3.
    fig, axes = plt.subplots(3, 2, figsize=(12, 11), sharex=True)
    for row, (direct, predicted) in enumerate(
        zip(direct_transforms, predicted_transforms), start=0
    ):
        order = row + 1

        axes[row, 0].plot(f, np.abs(direct), label=rf"$|Y_{order}(f)|$")
        axes[row, 0].plot(
            f,
            np.abs(predicted),
            "--",
            label=rf"$|(j2\pi f)^{order}X(f)|$",
        )
        axes[row, 0].set_ylabel("Magnitude")
        axes[row, 0].set_title(f"Derivative order {order}: magnitude")
        axes[row, 0].legend()
        axes[row, 0].grid(alpha=0.3)

        # Hide phase where the spectrum is numerically zero.
        phase_scale = max(np.max(np.abs(direct)), np.max(np.abs(predicted)))
        mask = (np.abs(direct) > 1e-6 * phase_scale) & (
            np.abs(predicted) > 1e-6 * phase_scale
        )
        axes[row, 1].plot(
            f[mask], np.angle(direct[mask]), "o", ms=4, label=rf"$\angle Y_{order}(f)$"
        )
        axes[row, 1].plot(
            f[mask],
            np.angle(predicted[mask]),
            "x",
            ms=5,
            label=rf"$\angle (j2\pi f)^{order}X(f)$",
        )
        axes[row, 1].set_ylabel("Phase (radians)")
        axes[row, 1].set_title(f"Derivative order {order}: phase")
        axes[row, 1].set_ylim(-np.pi - 0.2, np.pi + 0.2)
        axes[row, 1].legend()
        axes[row, 1].grid(alpha=0.3)

    axes[-1, 0].set_xlabel("Frequency f (Hz)")
    axes[-1, 1].set_xlabel("Frequency f (Hz)")
    fig.tight_layout()
    fig.savefig("derivative_cft_overlays.png", dpi=160)
    plt.show()


if __name__ == "__main__":
    main()
