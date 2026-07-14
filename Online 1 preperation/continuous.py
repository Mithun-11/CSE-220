import numpy as np

T_MIN, T_MAX, N = -4.0, 4.0, 801
t = np.linspace(T_MIN, T_MAX, N)


def time_shift(t, x, t0):
    """y(t) = x(t - t0).   t0 > 0 -> delay (right).  t0 < 0 -> advance (left)."""
    return np.interp(t - t0, t, x, left=0.0, right=0.0)


def time_scale(t, x, a):
    """
    y(t) = x(a*t).
      a > 1     -> compression (narrower)
      0 < a < 1 -> expansion / stretching (wider)
    For x(t/k), pass a = 1/k.
    """
    return np.interp(a * t, t, x, left=0.0, right=0.0)


def time_reverse(t, x):
    """y(t) = x(-t)."""
    return np.interp(-t, t, x, left=0.0, right=0.0)


def even_odd_decompose(t, x):
    """Return (xe, xo) with xe + xo = x."""
    xr = time_reverse(t, x)
    return 0.5 * (x + xr), 0.5 * (x - xr)


def transform(t, x, A, alpha, beta):
    """General: y(t) = A * x(alpha*t + beta). Covers all of the above."""
    return A * np.interp(alpha * t + beta, t, x, left=0.0, right=0.0)



def sinusoid(t, A, omega, phi):
    """x(t) = A * cos(omega*t + phi)"""
    return A * np.cos(omega * t + phi)


def phase_shift(t, A, omega, phi, phi0):
    """y(t) = A * cos(omega*t + phi + phi0)   -- add phi0 to the phase."""
    return A * np.cos(omega * t + phi + phi0)


def time_shift_sinusoid(t, A, omega, phi, t0):
    """y(t) = x(t - t0) = A * cos(omega*(t - t0) + phi)"""
    return A * np.cos(omega * (t - t0) + phi)



def my_interp(tq, t, x, left=0.0, right=0.0):
    """
    Manual linear interpolation (drop-in for np.interp).
    Read signal x (values on grid t) at any query times tq.
      t     : sample coordinates, must be INCREASING
      x     : values at t
      left  : value for tq < t[0]    (0.0 = signal is zero outside)
      right : value for tq > t[-1]
    """
    tq = np.asarray(tq, dtype=float)
    t  = np.asarray(t,  dtype=float)
    x  = np.asarray(x,  dtype=float)

    i = np.searchsorted(t, tq)          # index of the grid point just right of tq
    i = np.clip(i, 1, len(t) - 1)       # keep i-1 and i valid

    t0, t1 = t[i-1], t[i]               # bracketing times
    x0, x1 = x[i-1], x[i]               # bracketing values

    slope = (x1 - x0) / (t1 - t0)       # rise over run
    y = x0 + slope * (tq - t0)          # straight line through the two points

    y = np.where(tq < t[0],  left,  y)  # zero-fill outside the range
    y = np.where(tq > t[-1], right, y)
    return y





def my_interp_only_continuous(tq, t, x):
    """
    Linear interpolation on a UNIFORM grid t (from np.linspace).
    Reads x at query times tq. Returns 0 outside the signal's range.
    """
    dt = t[1] - t[0]                 # grid spacing (uniform, so one number)
    p = (tq - t[0]) / dt             # fractional POSITION of each query

    lo = np.floor(p).astype(int)     # position of the sample on the left
    frac = p - lo                    # how far past it (0..1)

    lo = np.clip(lo, 0, len(t) - 2)  # keep lo and lo+1 valid
    y = x[lo] * (1 - frac) + x[lo + 1] * frac   # blend the two neighbours

    inside = (tq >= t[0]) & (tq <= t[-1])       # queries outside -> 0
    return np.where(inside, y, 0.0)








def x_at_continuous(t, x, tq):
    """
    x(tq) for a continuous signal sampled on grid t.
    tq may be a scalar or an array, and may be negative, fractional,
    or out of range. Interpolates between samples; 0 outside the range.
    """
    return np.interp(tq, t, x, left=0.0, right=0.0)

import numpy as np


def x_at_continuous(t, x, tq, gap='interp'):
    """
    x(tq) for a continuous signal sampled on grid t.
    gap = how to get the value when tq lands BETWEEN grid samples:
      'interp'  -> linear interpolation, distance-weighted   (default, correct)
      'avg'     -> flat 50/50 of the two neighbouring samples
      'nearest' -> snap to the closest grid sample
      'zero'    -> only exact grid hits are valid, else 0
    Returns 0 for any tq outside the grid.
    """
    tq = np.atleast_1d(np.asarray(tq, dtype=float))
    dt = t[1] - t[0]                       # uniform grid spacing
    p  = (tq - t[0]) / dt                  # fractional position on the grid

    lo   = np.clip(np.floor(p).astype(int), 0, len(t) - 2)   # left neighbour
    hi   = lo + 1                                            # right neighbour
    frac = np.clip(p - lo, 0.0, 1.0)                         # how far past lo (0..1)

    if gap == 'interp':
        y = x[lo] * (1 - frac) + x[hi] * frac
    elif gap == 'avg':
        y = np.where(np.isclose(frac, 0.0), x[lo], 0.5 * (x[lo] + x[hi]))
    elif gap == 'nearest':
        y = np.where(frac < 0.5, x[lo], x[hi])
    elif gap == 'zero':
        y = np.where(np.isclose(frac, 0.0), x[lo], 0.0)
    else:
        raise ValueError("gap must be 'interp', 'avg', 'nearest', or 'zero'")

    inside = (tq >= t[0]) & (tq <= t[-1])   # outside the grid -> 0
    y = np.where(inside, y, 0.0)
    return y if y.size > 1 else y[0]