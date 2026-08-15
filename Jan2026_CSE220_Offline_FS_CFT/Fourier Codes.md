## Correct phase verification

Phase is meaningless where magnitude is approximately zero, so compare it only where the spectrum is significant:

```python
threshold = 1e-6 * max(

    np.max(np.abs(X)),

    np.max(np.abs(Y))

)
```

  

```python
mask = (

    (np.abs(X) > threshold)

    & (np.abs(Y) > threshold)

)
```

Calculate predicted phase:

predicted_phase = np.angle(Y_predicted)

measured_phase = np.angle(Y)

Because phase wraps between −π and π, calculate a wrapped difference:

```python
phase_error = np.angle(
    np.exp(
        1j * (
            measured_phase[mask]
            - predicted_phase[mask]
        )
    )
)

mse_phase = np.mean(phase_error**2)

print("Phase MSE:", mse_phase)
```
  


Do not directly subtract unwrapped and wrapped phases across the whole spectrum. That can produce a huge MSE even when the property is correct.

## 1D Fourier Transform Code
```python
import numpy as np
import matplotlib.pyplot as plt


class SignalGenerator:
    def __init__(self, t):
        self.t = t

    def gaussian(self, a, shift=0):
        return np.exp(-a * (self.t - shift)**2)


class CFTAnalyzer:
    def __init__(self, t, signal):
        self.t = t
        self.signal = signal

    def compute(self, frequencies):
        ft = frequencies[:, None] * self.t[None, :]
        kernel = np.exp(-1j * 2 * np.pi * ft)

        return np.trapezoid(
            self.signal[None, :] * kernel,
            self.t,
            axis=-1
        )

class InverseCFTAnalyzer:
    def __init__(self, frequencies, spectrum):
        self.frequencies = frequencies
        self.spectrum = spectrum

    def compute(self, t):

        ft = (
            t[:, None]
            * self.frequencies[None, :]
        )

        kernel = np.exp(
            1j * 2 * np.pi * ft
        )

        return np.trapezoid(
            self.spectrum[None, :] * kernel,
            self.frequencies,
            axis=-1
        )

t = np.linspace(-5, 5, 4001)
f = np.linspace(-10, 10, 2001)

t0 = 1

generator = SignalGenerator(t)

x = generator.gaussian(a=1)
y = generator.gaussian(a=1, shift=t0)

X = CFTAnalyzer(t, x).compute(f)
Y = CFTAnalyzer(t, y).compute(f)

# Theoretical prediction
Y_predicted = X * np.exp(-1j * 2 * np.pi * f * t0)
```

### Numpy Derivative
```python
import numpy as np

t = np.linspace(0, 10, 1000)
x = np.sin(t)

dx_dt = np.gradient(x, t)
```

# Fourier Series
### Time Shift

```python
def time_shift(t, x, t0):
    """
    y(t) = x(t - t0)

    Periodic version of interpolation for the SVG signal.
    """
    T = t[-1] - t[0]

    shifted_t = ((t - t0 - t[0]) % T) + t[0]

    return np.interp(shifted_t, t, x)
    
    
    
    
    t, z = load_svg_path(svg_path, num_points=1000)

t0 = 1.0


# ==========================================
# 1. Fourier series of the original signal
# ==========================================

fs_original = FourierEpicycles(
    t,
    z,
    n_harmonics=N_HARMONICS
)

fs_original.calculate_all_coefficients()


# ==========================================
# 2. Actually shift the signal
#    z_shifted(t) = z(t - t0)
# ==========================================

z_shifted = time_shift(t, z, t0)


# ==========================================
# 3. Fourier series of actually shifted signal
# ==========================================

fs_shifted = FourierEpicycles(
    t,
    z_shifted,
    n_harmonics=N_HARMONICS
)

fs_shifted.calculate_all_coefficients()


# ==========================================
# 4. Predict coefficients using property
#
# d_n = c_n * exp(-j*n*omega*t0)
# ==========================================

predicted_coeffs = {}

for n, c_n in fs_original.coeffs.items():

    predicted_coeffs[n] = (
        c_n
        * np.exp(
            -1j * n * fs_original.omega * t0
        )
    )


# ==========================================
# 5. Compare coefficients using MSE
# ==========================================

measured = np.array([
    fs_shifted.coeffs[n]
    for n in range(-N_HARMONICS, N_HARMONICS + 1)
])

predicted = np.array([
    predicted_coeffs[n]
    for n in range(-N_HARMONICS, N_HARMONICS + 1)
])

coefficient_mse = np.mean(
    np.abs(measured - predicted) ** 2
)

print("Coefficient MSE:", coefficient_mse)


# ==========================================
# 6. Compare reconstructed signals
# ==========================================

fs_predicted = FourierEpicycles(
    t,
    z,
    n_harmonics=N_HARMONICS
)

fs_predicted.coeffs = predicted_coeffs

measured_reconstruction = fs_shifted.approximate(t)
predicted_reconstruction = fs_predicted.approximate(t)

reconstruction_mse = np.mean(
    np.abs(
        measured_reconstruction
        - predicted_reconstruction
    ) ** 2
)

print("Reconstruction MSE:", reconstruction_mse)


# ==========================================
# 7. Save the predicted shifted animation
# ==========================================

save_outputs(
    fs_predicted,
    z_shifted,
    comparison_path,
    gif_path,
    num_frames=240
)
```

## Time Reversal
```python
def time_reverse(t, x):
    T = t[-1] - t[0]

    reversed_t = (-t) % T

    return np.interp(
        reversed_t,
        t,
        x
    )
```


## Time Scale
```python
def time_scale(t, x, a):

    if a == 0:
        raise ValueError("a cannot be zero")

    T_original = t[-1] - t[0]

    # New period
    T_scaled = T_original / abs(a)

    # Sample exactly one period of the scaled signal
    t_scaled = np.linspace(
        0,
        T_scaled,
        len(t)
    )

    # y(t) = x(a*t), wrapped periodically
    original_times = (a * t_scaled) % T_original

    x_scaled = np.interp(
        original_times,
        t,
        x
    )

    return t_scaled, x_scaled
    
    
    t, z = load_svg_path(svg_path, num_points=1000)

a = 2.0


# ==========================================
# 1. Fourier series of original signal
# ==========================================

fs_original = FourierEpicycles(
    t,
    z,
    n_harmonics=N_HARMONICS
)

fs_original.calculate_all_coefficients()


# ==========================================
# 2. Actually scale the signal
#    y(t) = z(a*t)
# ==========================================

t_scaled, z_scaled = time_scale(
    t,
    z,
    a
)


# ==========================================
# 3. Calculate FS of scaled signal
# ==========================================

fs_scaled = FourierEpicycles(
    t_scaled,
    z_scaled,
    n_harmonics=N_HARMONICS
)

fs_scaled.calculate_all_coefficients()


# ==========================================
# 4. Predict the scaled coefficients
# ==========================================

predicted_coeffs = {}

for n in range(-N_HARMONICS, N_HARMONICS + 1):

    if a > 0:
        # Positive scaling: coefficients stay the same
        predicted_coeffs[n] = fs_original.coeffs[n]

    else:
        # Negative scaling also causes time reversal
        predicted_coeffs[n] = fs_original.coeffs[-n]


# ==========================================
# 5. Compare measured and predicted
# ==========================================

measured_list = []
predicted_list = []

for n in range(-N_HARMONICS, N_HARMONICS + 1):

    measured_c_n = fs_scaled.coeffs[n]
    predicted_c_n = predicted_coeffs[n]

    measured_list.append(measured_c_n)
    predicted_list.append(predicted_c_n)

measured = np.array(measured_list)
predicted = np.array(predicted_list)

coefficient_mse = np.mean(
    np.abs(measured - predicted) ** 2
)

print("Coefficient MSE:", coefficient_mse)
```
## Integration
```python
def integrate_signal(t, signal):

    integrated = np.zeros_like(
        signal,
        dtype=complex
    )

    for i in range(1, len(t)):

        integrated[i] = np.trapezoid(
            signal[:i + 1],
            t[:i + 1]
        )

    return integrated
```
