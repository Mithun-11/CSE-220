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



# continuous plot
import numpy as np
import matplotlib.pyplot as plt

t = np.linspace(-4, 4, 400)
x = np.sin(2*np.pi*0.5*t)

fig, ax = plt.subplots(figsize=(8, 4))

# every common per-line control in one plot() call:
ax.plot(t, x,
        color="#B5192E",          # "red" | "r" | "#B5192E"
        linestyle="--",           # "-" solid | "--" dashed | ":" dotted | "-." dashdot
        linewidth=2.5,            # line thickness
        marker="o",               # o s ^ v D * x +   (omit for no markers)
        markersize=5,             # marker size
        markevery=20,             # a marker every 20th point (declutter dense curves)
        markerfacecolor="white",  # marker fill
        markeredgecolor="#B5192E",# marker border
        alpha=0.9,                # 0=transparent .. 1=opaque
        label="styled x(t)",      # legend text
        zorder=3)                 # draw order, higher = on top

# shorthand string does color + linestyle + marker at once:
ax.plot(t, 0.5*np.cos(2*np.pi*0.5*t), "g:s", markevery=25, label="shorthand 'g:s'")

ax.set_title("Title", fontsize=13, fontweight="bold")
ax.set_xlabel("t"); ax.set_ylabel("Amplitude")
ax.grid(True, linestyle=":", alpha=0.4)   # the grid takes styles too
ax.legend()
plt.show()



fig, axes = plt.subplots(2, 2)
axes[0, 0].plot(...)   # top-left
axes[0, 1].plot(...)   # top-right
axes[1, 0].plot(...)   # bottom-left
axes[1, 1].plot(...)   # bottom-right



