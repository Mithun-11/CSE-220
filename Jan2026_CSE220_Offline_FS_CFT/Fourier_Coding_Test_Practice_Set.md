# Fourier Series and CFT Coding-Test Practice Set

These questions are based on the two offline assignments:

- complex continuous Fourier series and reconstruction;
- numerical continuous Fourier transforms using `np.trapezoid`;
- forward/inverse transforms, frequency-domain filtering, and transform properties.

## How to practise

For each mock test, set a timer for **40 minutes**. Do not use `np.fft`, `scipy.fft`, or a symbolic integrator. Complete only the marked `TODO` sections. Unless stated otherwise, assume all input arrays are valid and uniformly sampled.

The tests intentionally use several boilerplate styles. Tests 1 and 6 resemble the offline code. Tests 2-5 use unfamiliar layouts so that you practise recognizing the mathematics instead of memorizing method names.

---

# Mock Test 1 - Familiar Fourier-series boilerplate

**Time:** 35 minutes  
**Marks:** 20

A periodic complex signal is sampled over one complete closed interval, so `t[0] = 0`, `t[-1] = T`, and `signal[-1] = signal[0]`.

Complete the four methods. Then compute the reconstruction MSE. Negative harmonics must be included.

```python
import numpy as np

class FourierSeries:
    def __init__(self, t, signal, N):
        # TODO 1 (4 marks): store inputs, calculate T and omega0,
        # and make an empty dictionary.
        pass

    def coefficient(self, k):
        # TODO 2 (6 marks):
        # c_k = (1/T) integral signal(t) exp(-j k omega0 t) dt
        pass

    def calculate_all(self):
        # TODO 3 (3 marks): store every coefficient from -N through +N.
        pass

    def reconstruct(self, query_t):
        # TODO 4 (5 marks): must accept a scalar or a NumPy array.
        pass

t = np.linspace(0, 2 * np.pi, 1500)
x = 1 + 2 * np.cos(t) - 0.5 * np.sin(3 * t)

fs = FourierSeries(t, x, N=5)
fs.calculate_all()
x_hat = fs.reconstruct(t)

# TODO 5 (2 marks): print mean squared reconstruction error.
```

Sanity checks: `c[0]` should be approximately `1`, reconstruction error should be extremely small, and `reconstruct(0.5)` must work.

---

# Mock Test 2 - New Fourier-series boilerplate and differentiation

**Time:** 40 minutes  
**Marks:** 25

This program stores harmonics and coefficients in arrays instead of a dictionary. Complete all TODOs.

For

$$
x(t)=\sum_{k=-K}^{K}c_k e^{jk\omega_0t},
$$

the derivative is

$$
\frac{dx}{dt}=\sum_{k=-K}^{K}(jk\omega_0)c_k e^{jk\omega_0t}.
$$

```python
import numpy as np

class PeriodicAnalyzer:
    def __init__(self, samples, values, max_harmonic):
        self.samples = samples
        self.values = values
        self.K = max_harmonic
        self.period = samples[-1] - samples[0]
        self.w0 = 2 * np.pi / self.period
        self.k = np.arange(-self.K, self.K + 1)
        self.c = None

    def analyze(self):
        # TODO 1 (10 marks): compute all 2K+1 coefficients.
        # self.c[i] must correspond to harmonic self.k[i].
        pass

    def synthesize(self, times):
        # TODO 2 (7 marks): return x_hat at all supplied times.
        pass

    def synthesize_derivative(self, times):
        # TODO 3 (6 marks): reconstruct dx/dt from the coefficients.
        pass

t = np.linspace(-np.pi, np.pi, 2001)
x = 3 * np.sin(2 * t) + 0.25 * np.cos(5 * t)

model = PeriodicAnalyzer(t, x, max_harmonic=7)
model.analyze()
dx_hat = model.synthesize_derivative(t)

# TODO 4 (2 marks): compare dx_hat against
# 6*cos(2t) - 1.25*sin(5t) using MSE.
```

Do not use `np.diff` or `np.gradient`. Both synthesis methods must support an array of times.

---

# Mock Test 3 - Numerical CFT and the time-shift property

**Time:** 35 minutes  
**Marks:** 25

Use


$$X(f)=\int_{-\infty}^{\infty}x(t)e^{-j2\pi ft}\,dt.$$


If `y(t) = x(t - t0)`, then


$$Y(f)=X(f)e^{-j2\pi f t_0}.$$


Complete the program without loops over individual frequency values.

```python
import numpy as np

def cft(signal, t, frequencies):
    # TODO 1 (10 marks): build a matrix with shape
    # (len(frequencies), len(t)), integrate over time,
    # and return shape (len(frequencies),).
    pass

def gaussian(t, delay=0.0):
    return np.exp(-2 * (t - delay) ** 2)

t = np.linspace(-8, 8, 4000)
f = np.linspace(-5, 5, 1200)
t0 = 1.25
x = gaussian(t)
y = gaussian(t, delay=t0)
X = cft(x, t, f)
Y = cft(y, t, f)

# TODO 2 (4 marks): calculate Y_predicted using the shift property.
# TODO 3 (3 marks): calculate magnitude MSE.
# TODO 4 (8 marks): compare phase only where both spectra are significant.
# Use a relative threshold of 1e-6 times the largest magnitude across X and Y.
# Calculate wrapped phase difference in [-pi, pi], then phase MSE.
```

Both MSE values should be close to zero apart from numerical truncation error.

---

# Mock Test 4 - Time scaling and reversal in the CFT

**Time:** 40 minutes  
**Marks:** 25

For `y(t)=x(at)`,


$$Y(f)=\frac{1}{|a|}X\left(\frac{f}{a}\right).$$


A negative `a` performs both scaling and time reversal.

```python
import numpy as np

def cft(x, t, f):
    # TODO 1 (8 marks): implement a vectorized numerical CFT.
    pass

def interpolate_complex(x_known, z_known, x_query):
    # TODO 2 (5 marks): interpolate real and imaginary parts separately.
    # Use left=0 and right=0 for both.
    pass

t = np.linspace(-10, 10, 5000)
f = np.linspace(-8, 8, 2001)
x = np.exp(-t**2) * (1 + 0.4 * t)

a = -2.0
y = np.exp(-(a * t)**2) * (1 + 0.4 * a * t)  # exactly x(a*t)

X = cft(x, t, f)
Y = cft(y, t, f)

# TODO 3 (7 marks): construct predicted Y(f).
# You need X evaluated at f/a, so use interpolate_complex.
# TODO 4 (5 marks): print mean(abs(Y - Y_predicted)**2).
```

Written follow-up: if `a = -1`, what happens to the magnitude spectrum, and which direction is the spectrum reversed?

---

# Mock Test 5 - Modulation and frequency-domain filtering

**Time:** 40 minutes  
**Marks:** 30

For


$$y(t)=x(t)\cos(2\pi f_c t),$$


the modulation property predicts


$$Y(f)=\frac12X(f-f_c)+\frac12X(f+f_c).$$


```python
import numpy as np

class TransformLab:
    def __init__(self, t, f):
        self.t = t
        self.f = f

    def forward(self, signal):
        # TODO 1 (8 marks): vectorized numerical CFT.
        pass

    def inverse(self, spectrum):
        # TODO 2 (8 marks): inverse CFT using the positive exponent.
        # Return the real part.
        pass

    def band_pass(self, spectrum, center, half_width):
        # TODO 3 (5 marks): keep frequencies satisfying
        # abs(abs(f) - center) <= half_width.
        # Do not modify the original array.
        pass

t = np.linspace(-4, 4, 3000)
f = np.linspace(-20, 20, 1600)
fc = 7.0
x = np.exp(-t**2) * (np.cos(2*np.pi*1.5*t) + 0.5*np.cos(2*np.pi*3*t))
y = x * np.cos(2*np.pi*fc*t)

lab = TransformLab(t, f)
X = lab.forward(x)
Y = lab.forward(y)

# TODO 4 (5 marks): predict Y from X using the modulation property.
# Interpolate real and imaginary parts separately; use zero outside the range.
# TODO 5 (2 marks): calculate complex-spectrum MSE.
# TODO 6 (2 marks): retain only the pair of bands centered at fc + 3 Hz,
# then inverse-transform into recovered_component.
```

Written follow-up: why does multiplication by one cosine create two shifted spectrum copies?

---

# Mock Test 6 - Reusing the old 2D image boilerplate

**Time:** 35 minutes  
**Marks:** 25

Assume the old `ContinuousImage`, `CFT2D`, and `InverseCFT2D` classes are provided and correct. Replace the old high-pass filter and extend spectrum visualization.

```python
import numpy as np
import matplotlib.pyplot as plt

class FrequencyFilter:
    def low_pass(self, real, imag, cutoff):
        # TODO 1 (7 marks): preserve points inside the central circle
        # and zero points outside. Do not modify inputs.
        pass

    def band_pass(self, real, imag, inner_cutoff, outer_cutoff):
        # TODO 2 (8 marks): preserve points whose center-distance is
        # between the cutoffs, inclusive. Zero everything else.
        pass

def plot_spectrum(real, imag):
    # TODO 3 (5 marks): display log(1 + |F|), with origin="lower"
    # and a colorbar.
    pass

def normalize_for_display(reconstructed):
    # TODO 4 (5 marks): take magnitude and normalize to [0,1]
    # without division by zero. Do not invert colors.
    pass
```

Written questions:

1. What visual result do you expect after a strong low-pass filter?
2. What happens as a high-pass cutoff is increased?
3. In `spectrum[row, col]`, which index represents vertical frequency and which represents horizontal frequency?

---

# Bonus debugging sprint

**Time:** 20 minutes  
**Marks:** 15

This program should calculate and reconstruct a continuous Fourier series, but contains at least **seven independent errors**. Identify and correct them.

```python
import numpy as np

class BrokenFS:
    def __init__(self, t, x, N):
        self.t = t
        self.x = x
        self.N = N
        self.T = t[-1]
        self.w0 = self.T / (2 * np.pi)
        self.coeffs = []

    def coefficient(self, k):
        kernel = np.exp(1j * k * self.w0 * self.t)
        return np.trapezoid(self.x * kernel) / self.T

    def calculate(self):
        for k in range(-self.N, self.N):
            self.coeffs[k] = self.coefficient(k)

    def reconstruct(self, query_t):
        answer = 0
        for k, ck in self.coeffs:
            answer += ck * np.exp(-1j * k * self.w0 * query_t)
        return answer
```

---

# Coverage and recommended order

| Skill | Tests |
|---|---:|
| FS coefficients and reconstruction | 1, 2, Bonus |
| Negative harmonics and scalar/array safety | 1, 2, Bonus |
| FS differentiation | 2 |
| Vectorized numerical CFT | 3, 4, 5 |
| Time shifting | 3 |
| Scaling and reversal | 4 |
| Modulation/frequency shifting | 5 |
| Inverse CFT and frequency masks | 5, 6 |
| Wrapped-phase verification | 3 |
| 2D indexing and visualization | 6 |

Recommended order: **1 -> 3 -> 2 -> 4 -> 5 -> 6 -> Bonus**.

Solutions are deliberately omitted so these can be used as genuine mock tests.
