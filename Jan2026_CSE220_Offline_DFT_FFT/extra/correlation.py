import numpy as np


def cross_correlation(reference, shifted, engine):
    """Return the circular cross-correlation of two equal-length 1D signals."""
    reference = np.asarray(reference)
    shifted = np.asarray(shifted)

    if reference.ndim != 1 or shifted.ndim != 1:
        raise ValueError("inputs must be one-dimensional")
    if len(reference) == 0 or len(reference) != len(shifted):
        raise ValueError("inputs must be non-empty and have equal lengths")

    reference_spectrum = engine.transform(reference)
    shifted_spectrum = engine.transform(shifted)

    correlation = engine.inverse(
        np.conjugate(reference_spectrum) * shifted_spectrum
    )

    if np.isrealobj(reference) and np.isrealobj(shifted):
        return np.real_if_close(correlation)

    return correlation


def find_shift(reference, shifted, engine):
    """Return how many positions ``reference`` was circularly shifted right."""
    correlation = cross_correlation(reference, shifted, engine)
    return int(np.argmax(correlation.real))


def reverse_shift(shifted, shift):
    """Undo a detected right circular shift."""
    return np.roll(shifted, -shift)


# Example:
# reference = np.array([1, 2, 3, 4])
# shifted = np.roll(reference, 2)       # [3, 4, 1, 2]
# shift = find_shift(reference, shifted, engine)
# restored = reverse_shift(shifted, shift)
