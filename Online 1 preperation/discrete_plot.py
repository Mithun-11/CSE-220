import numpy as np 
import matplotlib.pyplot as plt

n = np.arange(-10, 11)
xn = np.cos(np.pi/4 * n)

fig, ax = plt.subplots(figsize=(7, 3))
ax.stem(n, xn,
        linefmt="C1-",      # stem line: color + style
        markerfmt="C1o",    # marker: color + shape
        basefmt=" ",        # baseline: " " hides it
        label="x[n]")       # legend text -- same as in plot()
ax.legend()                 # needed to actually show the label
ax.grid(True, alpha=0.3)
ax.set_xlabel("n"); ax.set_ylabel("Amplitude")
plt.show()
