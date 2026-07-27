import numpy as np

def deconvolve_1d(y, h):
    """
    Performs 1D deconvolution to find x given y and h.
    Assumes y = convolution(x, h).
    """
    # h[0] cannot be zero, otherwise we divide by zero
    if h[0] == 0:
        raise ValueError("The first element of the impulse response h[0] cannot be zero.")
        
    # The length of the original signal x is derived from the convolution length formula:
    # len(y) = len(x) + len(h) - 1  =>  len(x) = len(y) - len(h) + 1
    len_x = len(y) - len(h) + 1
    
    if len_x <= 0:
        raise ValueError("Length of y must be greater than or equal to length of h.")
        
    x = np.zeros(len_x)
    
    # Forward substitution
    for n in range(len_x):
        # Start with the current output value
        current_y = y[n]
        
        # Subtract the overlapping effects of previously calculated x values
        overlap_sum = 0
        for k in range(1, min(n + 1, len(h))):
            overlap_sum += x[n - k] * h[k]
            
        x[n] = (current_y - overlap_sum) / h[0]
        
    return x

# --- Testing the Logic ---

# 1. Define an original signal and an impulse response
original_x = np.array([2.0, 4.0, -1.0])
h = np.array([1.0, 3.0, 2.0])

# 2. Convolve them to create y
# np.convolve does exactly what your LTI system does
y = np.convolve(original_x, h) 
print(f"Convolved output y: {y}")

# 3. Deconvolve y and h to get x back
recovered_x = deconvolve_1d(y, h)
print(f"Recovered input x:  {recovered_x}")