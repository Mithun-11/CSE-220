import numpy as np

from transforms import next_power_of_two


def circular_convolution(a, b, engine):
    """
    Return the exact N-point circular convolution, where

        N = max(len(a), len(b))

    engine may be:
        DFTAnalyzer()
        FFTTransformer()
        ArbitraryLengthFFT()
    """
    a = np.asarray(a)
    b = np.asarray(b)

    if a.ndim != 1 or b.ndim != 1:
        raise ValueError("a and b must be one-dimensional")

    if len(a) == 0 or len(b) == 0:
        raise ValueError("a and b cannot be empty")

    real_inputs = np.isrealobj(a) and np.isrealobj(b)

    a = a.astype(np.complex128)
    b = b.astype(np.complex128)

    N = max(len(a), len(b))

    # DFT and arbitrary-length FFT can transform at exactly N.
    # Radix-2 FFT can also do this when N is a power of two.
    if engine.name != "fft" or next_power_of_two(N) == N:
        padded_a = np.zeros(N, dtype=np.complex128)
        padded_b = np.zeros(N, dtype=np.complex128)

        padded_a[:len(a)] = a
        padded_b[:len(b)] = b

        A = engine.transform(padded_a)
        B = engine.transform(padded_b)

        result = engine.inverse(A * B)

    else:
        # Exact N-point circular convolution using a radix-2 FFT:
        # compute linear convolution, then wrap it to period N.
        linear_length = len(a) + len(b) - 1
        fft_length = next_power_of_two(linear_length)

        padded_a = np.zeros(fft_length, dtype=np.complex128)
        padded_b = np.zeros(fft_length, dtype=np.complex128)

        padded_a[:len(a)] = a
        padded_b[:len(b)] = b

        A = engine.transform(padded_a)
        B = engine.transform(padded_b)

        linear = engine.inverse(A * B)[:linear_length]

        result = np.zeros(N, dtype=np.complex128)

        for index, value in enumerate(linear):
            result[index % N] += value

    if real_inputs:
        return np.real_if_close(result)

    return result