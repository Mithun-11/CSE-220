import numpy as np
import matplotlib.pyplot as plt


n = np.arange(-10, 11)
#generating discrete signal
def x_of_n(n):
    xn = np.zeros_like(n, dtype=float)
    m = (n >= 0) & (n <= 4)
    xn[m] = 4 - n[m] # 4,3,2,1,0 at n=0..4
    return xn




def time_reverse(n, x):
    def sample(idx):                     # x at index values, 0 if outside window
        v = np.zeros(len(idx))
        ok = (idx >= n[0]) & (idx <= n[-1])
        v[ok] = x[idx[ok] - n[0]]
        return v
    return sample(-n)                     # y[n] = x[-n]

def reverse_new_axis(n, x):
    n_new = -n[::-1]     # the true reversed index set
    x_new = x[::-1]      # values flip to match
    return n_new, x_new



#use this one
def time_shift_signal(x, k):
    if k > 0: # delay: zeros in from the left, tail drops off
        return np.concatenate((np.zeros(k), x[:-k]))
    elif k < 0: # advance
        return np.concatenate((x[-k:], np.zeros(-k)))
    return x.copy()

#x[nk]
def downsample_np(n, x, k):
    def sample(idx):                      # read x at index VALUES, 0 if outside
        v = np.zeros(len(idx))
        ok = (idx >= n[0]) & (idx <= n[-1])
        v[ok] = x[idx[ok] - n[0]]
        return v
    return sample(k * n)                   # x[k*n] for every n at once


#x[n/k]
def time_scale_signal_interpolate(n, x, k):
    m = n / k
    m_lo = np.floor(m).astype(int)
    m_hi = np.ceil(m).astype(int)
    def sample(idx):
        v = np.zeros(len(idx))
        ok = (idx >= n[0]) & (idx <= n[-1])
        v[ok] = x[idx[ok] - n[0]]
        return v
    lo, hi = sample(m_lo), sample(m_hi)
    return np.where(m_lo == m_hi, lo, (lo + hi) / 2)

#using interp
def upsample_interp(n, x, k):
    src_f = n / k                      # fractional source index
    return np.interp(src_f, n, x, left=0.0, right=0.0)

#missing value 0
def upsample_zeros(n, x, k):
    y = np.zeros_like(x, dtype=float)
    divisible = (n % k == 0)          # x[n/k] only exists when k divides n
    src = n // k                       # integer source index
    pos = src - n[0]                   # index value -> array position
    inside = (src >= n[0]) & (src <= n[-1])
    take = divisible & inside
    y[take] = x[pos[take]]
    return y

#or
def upsample_zeros(n, x, k):
    def sample(idx):                                  # safe lookup, 0 if outside
        v = np.zeros(len(idx))
        ok = (idx >= n[0]) & (idx <= n[-1])
        v[ok] = x[idx[ok] - n[0]]
        return v

    divisible = (n % k == 0)                          # x[n/k] exists only when k | n
    src = np.where(divisible, n // k, n[0] - 1)       # source index; dummy where not divisible
    return np.where(divisible, sample(src), 0.0)      # keep divisible slots, else 0


#all at once

def transform(n, x, alpha, beta, gap='zero'):
    """
    y[n] = x[alpha*n + beta].
    gap (for non-integer source indices):
      'zero'    -> 0 where undefined      (pure x[n/k] upsampling)
      'nearest' -> round to closest index
      'avg'     -> flat 50/50 of the two neighbours
      'interp'  -> linear interpolation (np.interp)
    """
    def sample(idx):
        v = np.zeros(len(idx))
        ok = (idx >= n[0]) & (idx <= n[-1])
        v[ok] = x[idx[ok] - n[0]]
        return v

    src = alpha * n + beta
    is_int = np.isclose(src, np.round(src))

    if gap == 'zero':
        idx = np.where(is_int, np.round(src), n[0] - 1).astype(int)
        return np.where(is_int, sample(idx), 0.0)
    if gap == 'nearest':
        return sample(np.round(src).astype(int))
    if gap == 'avg':
        lo = np.floor(src).astype(int)
        hi = np.ceil(src).astype(int)
        return np.where(lo == hi, sample(lo), 0.5 * (sample(lo) + sample(hi)))
    if gap == 'interp':
        return np.interp(src, n, x, left=0.0, right=0.0)
    raise ValueError("gap must be 'zero', 'nearest', 'avg', or 'interp'")


def interp_manual(n, x, alpha, beta, gap='interp'):
    """
    y[n] = x[alpha*n + beta], using manual linear interpolation
    (no np.interp). Same 5 parameters as transform().
    """
    def sample(idx):                                 # x at index values, 0 if outside
        v = np.zeros(len(idx))
        ok = (idx >= n[0]) & (idx <= n[-1])
        v[ok] = x[idx[ok] - n[0]]
        return v

    src = alpha * n + beta                           # source index (may be fractional)
    lo = np.floor(src).astype(int)                   # integer below src
    hi = np.ceil(src).astype(int)                    # integer above src
    frac = src - lo                                  # how far past lo (0..1)

    y = sample(lo) * (1 - frac) + sample(hi) * frac  # distance-weighted blend
    inside = (src >= n[0]) & (src <= n[-1])          # src itself must be in range
    return np.where(inside, y, 0.0)

def x_at_discrete(n, x, k):
    """
    x[k] for a discrete signal defined on index array n.
    k may be a scalar or an array, and may be negative or out of range.
    Returns 0 for any index not on the axis.
    """
    k = np.atleast_1d(np.asarray(k))
    v = np.zeros(len(k), dtype=float)
    ok = (k >= n[0]) & (k <= n[-1])       # is this index on our axis?
    v[ok] = x[k[ok] - n[0]]               # index value -> array position
    return v if v.size > 1 else v[0]