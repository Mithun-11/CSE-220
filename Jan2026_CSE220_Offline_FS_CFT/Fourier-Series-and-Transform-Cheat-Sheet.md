# Fourier Series & Fourier Transform Cheat Sheet

## 0. Notation

| Symbol | Meaning |
|---|---|
| $T$ | Fundamental period |
| $\omega_0=\dfrac{2\pi}{T}$ | Fundamental angular frequency |
| $k\in\mathbb Z$ | Harmonic index |
| $a_k$ | Fourier-series coefficient |
| $X(j\omega)$ | Fourier transform of $x(t)$ |
| $\lvert a_k\rvert,\ \lvert X(j\omega)\rvert$ | Magnitude or strength |
| $\angle a_k,\ \angle X(j\omega)$ | Phase |
| $j$ | $\sqrt{-1}$ |

---

# Part A: Fourier Series

Fourier series represents a periodic signal:

$$
x(t+T)=x(t)
$$

using discrete harmonic frequencies:

$$
\omega=k\omega_0.
$$

## 1. Complex-exponential Fourier-series pair

### Synthesis: reconstruct the signal

$$
\boxed{x(t)=\sum_{k=-\infty}^{\infty}a_k e^{jk\omega_0t}}
$$

### Analysis: calculate the coefficients

$$
\boxed{a_k=\frac{1}{T}\int_{\text{one period}}x(t)e^{-jk\omega_0t}\,dt}
$$

Each $a_k$ determines the magnitude and phase of the component at frequency $k\omega_0$.

## 2. DC coefficient and average

For $k=0$:

$$
\boxed{a_0=\frac{1}{T}\int_{\text{one period}}x(t)\,dt}
$$

Therefore:

$$
\boxed{a_0=\text{average value of }x(t)}
$$

## 3. Magnitude and phase spectra

At each discrete frequency $\omega=k\omega_0$:

$$
|a_k|=\text{strength},
\qquad
\angle a_k=\text{phase}.
$$

## 4. Euler identities

$$
\boxed{e^{j\theta}=\cos\theta+j\sin\theta}
$$

$$
\boxed{\cos\theta=\frac12e^{j\theta}+\frac12e^{-j\theta}}
$$

$$
\boxed{\sin\theta=\frac{e^{j\theta}-e^{-j\theta}}{2j}}
$$

For $A\cos(k\omega_0t+\phi)$:

$$
a_k=\frac A2e^{j\phi},
\qquad
a_{-k}=\frac A2e^{-j\phi}.
$$

## 5. Real-signal symmetry

If $x(t)$ is real:

$$
\boxed{a_{-k}=a_k^*}
$$

Hence:

$$
|a_{-k}|=|a_k|,
\qquad
\angle a_{-k}=-\angle a_k.
$$

---

# Fourier-Series Properties

Assume:

$$
x(t)\longleftrightarrow a_k,
\qquad
y(t)\longleftrightarrow b_k,
$$

using the same fundamental-frequency ruler $\omega_0$.

## 6. Linearity

$$
\boxed{Ax(t)+By(t)\longleftrightarrow Aa_k+Bb_k}
$$

## 7. Time shifting

Delay by $t_0$:

$$
\boxed{x(t-t_0)\longleftrightarrow a_ke^{-jk\omega_0t_0}}
$$

Advance by $t_0$:

$$
\boxed{x(t+t_0)\longleftrightarrow a_ke^{jk\omega_0t_0}}
$$

A time shift preserves $|a_k|$ and changes the phase.

## 8. Time reversal

$$
\boxed{x(-t)\longleftrightarrow a_{-k}}
$$

Positive- and negative-frequency coefficients exchange positions.

## 9. Positive time scaling

For:

$$
y(t)=x(\alpha t),\qquad \alpha>0,
$$

the new period and fundamental frequency are:

$$
\boxed{T'=\frac{T}{\alpha}},
\qquad
\boxed{\omega_0'=\alpha\omega_0}.
$$

Relative to the new fundamental frequency:

$$
\boxed{b_k=a_k}
$$

Thus the coefficient sequence stays the same, but the physical frequency represented by index $k$ changes to $k\omega_0'$.

## 10. Negative time scaling

For $\alpha<0$, scaling also reverses time:

$$
\boxed{x(\alpha t)\longleftrightarrow a_{-k}}
$$

with:

$$
\boxed{\omega_0'=|\alpha|\omega_0}.
$$

## 11. Differentiation

$$
\boxed{x'(t)\longleftrightarrow jk\omega_0a_k}
$$

Consequences:

- Higher frequencies are multiplied more strongly.
- Multiplication by $j$ changes phase by $90^\circ$.
- The DC coefficient disappears:

$$
\boxed{b_0=0}.
$$

## 12. Integration

If $y'(t)=x(t)$, then for $k\ne0$:

$$
\boxed{b_k=\frac{a_k}{jk\omega_0}}
$$

Important conditions:

- $x(t)$ must have zero average for its integral to remain periodic:

$$
\boxed{a_0=0}.
$$

- Integration cannot determine $b_0$; it must be found separately from the average of $y(t)$.

## 13. Multiplication

If $z(t)=x(t)y(t)$, then:

$$
\boxed{c_k=\sum_{p=-\infty}^{\infty}a_pb_{k-p}}
$$

or:

$$
\boxed{c_k=(a*b)_k}.
$$

Therefore:

$$
\boxed{\text{multiplication in time}\longleftrightarrow\text{convolution of coefficients}}
$$

For a fixed $k$, $c_k$ collects all products $a_pb_q$ whose indices satisfy $p+q=k$.

## 14. Parseval's relation / average power

$$
\boxed{P=\frac{1}{T}\int_{\text{one period}}|x(t)|^2\,dt}
$$

and:

$$
\boxed{P=\sum_{k=-\infty}^{\infty}|a_k|^2}.
$$

---

# Impulses in Fourier Series

## 15. Sifting property

$$
\boxed{\int_{-\infty}^{\infty}f(t)\delta(t-t_0)\,dt=f(t_0)}
$$

## 16. Derivative at a jump

If $x(t)$ jumps at $t=t_0$, its derivative contains:

$$
\boxed{\left[x(t_0^+)-x(t_0^-)\right]\delta(t-t_0)}
$$

A rising jump of height $A$ gives $+A\delta(t-t_0)$; a falling jump of height $A$ gives $-A\delta(t-t_0)$.

## 17. Periodic impulse train

$$
x(t)=\sum_{m=-\infty}^{\infty}\delta(t-mT)
$$

has coefficients:

$$
\boxed{a_k=\frac1T\quad\text{for every }k}.
$$

---

# Important Fourier-Series Examples

## 18. Periodic centered rectangular pulse

For:

$$
x(t)=
\begin{cases}
1,&|t|<T_1,\\
0,&T_1<|t|<T/2,
\end{cases}
$$

the coefficients are:

$$
\boxed{a_0=\frac{2T_1}{T}}
$$

and, for $k\ne0$:

$$
\boxed{a_k=\frac{\sin(k\omega_0T_1)}{k\pi}}.
$$

## 19. Special square wave with $T_1=T/4$

$$
\boxed{a_0=\frac12}
$$

$$
\boxed{a_k=\frac{\sin(k\pi/2)}{k\pi},\qquad k\ne0}.
$$

Only odd-index coefficients are nonzero.

## 20. Periodic sawtooth

For:

$$
x(t)=t,\qquad0\le t<1,\qquad x(t+1)=x(t),
$$

where $T=1$ and $\omega_0=2\pi$:

$$
\boxed{
a_k=
\begin{cases}
\dfrac12,&k=0,\\[6pt]
\dfrac{j}{2\pi k},&k\ne0.
\end{cases}}
$$

---

# Part B: Fourier Transform

The Fourier transform generally represents aperiodic signals using the continuous frequency variable:

$$
-\infty<\omega<\infty.
$$

## 21. Fourier-transform pair

### Analysis

$$
\boxed{X(j\omega)=\int_{-\infty}^{\infty}x(t)e^{-j\omega t}\,dt}
$$

### Synthesis / inverse transform

$$
\boxed{x(t)=\frac{1}{2\pi}\int_{-\infty}^{\infty}X(j\omega)e^{j\omega t}\,d\omega}
$$

Notation:

$$
\boxed{x(t)\longleftrightarrow X(j\omega)}
$$

## 22. Meaning of $X(j\omega)$

For every possible frequency $\omega$:

- $|X(j\omega)|$ gives the magnitude or frequency-density strength.
- $\angle X(j\omega)$ gives the phase.

A tiny frequency interval contributes:

$$
\boxed{\frac{1}{2\pi}X(j\omega)e^{j\omega t}\,d\omega}.
$$

The inverse transform combines all these infinitesimal frequency contributions.

---

# Fourier Series to Fourier Transform

## 23. Periodic-extension idea

Repeat an isolated signal every $T$ seconds:

$$
\boxed{x_T^e(t)=\sum_{m=-\infty}^{\infty}x(t-mT)}.
$$

As $T\to\infty$, the neighboring copies move infinitely far away:

$$
x_T^e(t)\to x(t).
$$

## 24. Frequency spacing

$$
\boxed{\Delta\omega=\omega_0=\frac{2\pi}{T}}.
$$

As $T\to\infty$:

$$
\boxed{\Delta\omega\to0},
$$

so discrete spectral lines become a continuous frequency axis.

## 25. Connection between $a_k$ and $X(j\omega)$

$$
\boxed{a_k=\frac1T X(jk\omega_0)}
$$

or:

$$
\boxed{Ta_k=X(jk\omega_0)}.
$$

Thus $Ta_k$ gives samples of the continuous Fourier-transform curve.

Since:

$$
\frac1T=\frac{\Delta\omega}{2\pi},
$$

we also have:

$$
\boxed{a_k=\frac{\Delta\omega}{2\pi}X(jk\omega_0)}.
$$

---

# Fourier-Transform Properties Studied

Assume:

$$
x(t)\longleftrightarrow X(j\omega),
\qquad
y(t)\longleftrightarrow Y(j\omega).
$$

## 26. Linearity

$$
\boxed{Ax(t)+By(t)\longleftrightarrow AX(j\omega)+BY(j\omega)}
$$

## 27. Time shifting

$$
\boxed{x(t-t_0)\longleftrightarrow e^{-j\omega t_0}X(j\omega)}
$$

A time shift preserves magnitude and adds phase $-\omega t_0$ because:

$$
|e^{-j\omega t_0}|=1.
$$

## 28. Frequency shifting

$$
\boxed{x(t)e^{j\omega_ct}\longleftrightarrow X\!\left(j(\omega-\omega_c)\right)}
$$

This shifts the spectrum right by $\omega_c$.

Similarly:

$$
\boxed{x(t)e^{-j\omega_ct}\longleftrightarrow X\!\left(j(\omega+\omega_c)\right)}
$$

This shifts the spectrum left by $\omega_c$.

## 29. Cosine modulation

Because:

$$
\cos(\omega_ct)=\frac12e^{j\omega_ct}+\frac12e^{-j\omega_ct},
$$

we obtain:

$$
\boxed{x(t)\cos(\omega_ct)
\longleftrightarrow
\frac12X\!\left(j(\omega-\omega_c)\right)
+\frac12X\!\left(j(\omega+\omega_c)\right)}.
$$

This creates two half-sized spectral copies centered at $+\omega_c$ and $-\omega_c$.

## 30. Filtering

For an LTI filter with frequency response $H(j\omega)$:

$$
\boxed{Y(j\omega)=H(j\omega)X(j\omega)}.
$$

| Value of $H(j\omega)$ | Effect |
|---:|---|
| $0$ | Remove the frequency |
| $0.5$ | Halve it |
| $1$ | Preserve it |
| $2$ | Double it |

The output signal is:

$$
\boxed{y(t)=\mathcal F^{-1}\{Y(j\omega)\}}.
$$

---

# Important Fourier-Transform Pairs

## 31. Centered rectangle

$$
r(t)=
\begin{cases}
1,&-\dfrac T2<t<\dfrac T2,\\[4pt]
0,&\text{otherwise}.
\end{cases}
$$

Then:

$$
\boxed{R(j\omega)=\frac{2\sin(\omega T/2)}{\omega}}.
$$

At zero:

$$
\boxed{R(0)=T}.
$$

First zeros:

$$
\boxed{\omega=\pm\frac{2\pi}{T}}.
$$

Main-lobe width:

$$
\boxed{\frac{4\pi}{T}}.
$$

Therefore:

$$
\boxed{\text{wider in time}\Longrightarrow\text{narrower in frequency}}.
$$

## 32. General shifted rectangle

For a height-$A$, width-$T$ rectangle centered at $t_0$:

$$
\boxed{X(j\omega)=Ae^{-j\omega t_0}\frac{2\sin(\omega T/2)}{\omega}}.
$$

## 33. Rectangle from $4$ to $10$

Here $T=6$ and $t_0=7$, so:

$$
\boxed{X(j\omega)=e^{-j7\omega}\frac{2\sin(3\omega)}{\omega}}.
$$

## 34. Lego-block signal

For:

$$
x(t)=
\begin{cases}
1,&1<t<2,\\
1.5,&2<t<3,\\
1,&3<t<4,\\
0,&\text{otherwise},
\end{cases}
$$

decompose it into a height-$1$, width-$3$ rectangle and a height-$0.5$, width-$1$ rectangle, both centered at $2.5$:

$$
\boxed{X(j\omega)=e^{-j2.5\omega}
\left[
\frac{2\sin(1.5\omega)}{\omega}
+\frac{\sin(0.5\omega)}{\omega}
\right]}.
$$

At zero:

$$
\boxed{X(0)=3.5}.
$$

## 35. Unit impulse

$$
\boxed{\delta(t)\longleftrightarrow1}
$$

A perfectly localized impulse in time contains all frequencies equally.

## 36. Shifted impulse

$$
\boxed{\delta(t-t_0)\longleftrightarrow e^{-j\omega t_0}}
$$

Its magnitude is flat, while its phase is $-\omega t_0$.

## 37. Constant signal

$$
\boxed{1\longleftrightarrow2\pi\delta(\omega)}
$$

More generally:

$$
\boxed{C\longleftrightarrow2\pi C\delta(\omega)}.
$$

## 38. Complex exponential

$$
\boxed{e^{j\omega_ct}\longleftrightarrow2\pi\delta(\omega-\omega_c)}
$$

## 39. Infinite-duration cosine

$$
\boxed{\cos(\omega_ct)
\longleftrightarrow
\pi\delta(\omega-\omega_c)+\pi\delta(\omega+\omega_c)}.
$$

## 40. Windowed cosine

Let:

$$
x(t)=r(t)\cos(\omega_ct),
$$

where $r(t)$ is a width-$T$ rectangle. Then:

$$
\boxed{X(j\omega)=
\frac12R\!\left(j(\omega-\omega_c)\right)
+\frac12R\!\left(j(\omega+\omega_c)\right)}.
$$

Substituting the rectangle transform:

$$
\boxed{X(j\omega)=
\frac{\sin\left((\omega-\omega_c)T/2\right)}{\omega-\omega_c}
+\frac{\sin\left((\omega+\omega_c)T/2\right)}{\omega+\omega_c}}.
$$

This produces two sinc-shaped copies centered at $\pm\omega_c$.

---

# Useful Special Checks

## 41. Transform at zero frequency

$$
\boxed{X(0)=\int_{-\infty}^{\infty}x(t)\,dt}
$$

Therefore:

$$
\boxed{X(0)=\text{signed area under }x(t)}.
$$

Do not confuse:

$$
\boxed{\text{Fourier series: }a_0=\text{average}}
$$

with:

$$
\boxed{\text{Fourier transform: }X(0)=\text{total area}}.
$$

## 42. Real-signal symmetry in the Fourier transform

If $x(t)$ is real:

$$
\boxed{X(-j\omega)=X^*(j\omega)}.
$$

Hence:

$$
|X(-j\omega)|=|X(j\omega)|,
\qquad
\angle X(-j\omega)=-\angle X(j\omega).
$$

## 43. Time-frequency trade-off

$$
\boxed{\text{narrow in time}\Longrightarrow\text{wide in frequency}}
$$

$$
\boxed{\text{wide in time}\Longrightarrow\text{narrow in frequency}}
$$

Examples:

- Impulse in time $\rightarrow$ flat spectrum.
- Infinite cosine $\rightarrow$ sharp frequency impulses.
- Short cosine burst $\rightarrow$ wide sinc-shaped peaks.
- Long cosine burst $\rightarrow$ narrow peaks.

---

# Fourier Series vs Fourier Transform

| Concept | Fourier series | Fourier transform |
|---|---|---|
| Main signal type | Periodic | Aperiodic/general |
| Frequency variable | $k\omega_0$ | Continuous $\omega$ |
| Representation | Sequence $a_k$ | Function $X(j\omega)$ |
| Analysis interval | One period | All time |
| Reconstruction | Sum | Integral |
| DC meaning | $a_0=$ average | $X(0)=$ total area |
| Spectrum | Discrete lines | Continuous curve or impulses |
| Frequency amount | Individual coefficient | Frequency density |

---

# Most Confusable Formula Pairs

## Time shift

Fourier series:

$$
x(t-t_0)\longleftrightarrow a_ke^{-jk\omega_0t_0}
$$

Fourier transform:

$$
x(t-t_0)\longleftrightarrow e^{-j\omega t_0}X(j\omega)
$$

## Multiplication

Fourier series:

$$
x(t)y(t)\longleftrightarrow\sum_{p=-\infty}^{\infty}a_pb_{k-p}
$$

Filtering with an LTI system:

$$
Y(j\omega)=H(j\omega)X(j\omega)
$$

The second formula corresponds to convolution with the system in the time domain.

## Periodic versus continuous frequencies

Fourier series:

$$
\omega=k\omega_0
$$

Fourier transform:

$$
-\infty<\omega<\infty
$$

---

# Practical Digital Version

A computer uses the Discrete Fourier Transform, normally calculated efficiently with the FFT:

$$
\boxed{X[k]=\sum_{n=0}^{N-1}x[n]e^{-j2\pi kn/N}}.
$$

Frequency-bin spacing:

$$
\boxed{\Delta f=\frac{f_s}{N}}.
$$

For real sampled audio, the unique frequency range is approximately:

$$
0\le f\le\frac{f_s}{2}.
$$

Practical workflow:

$$
\boxed{\text{samples}\rightarrow\text{FFT}\rightarrow\text{modify/analyze bins}\rightarrow\text{inverse FFT}}.
$$

For time-varying audio, use short overlapping windows:

$$
\boxed{\text{STFT}\rightarrow\text{spectrogram}}.
$$

---

# Ultra-Short Memory Sheet

$$
\boxed{x(t)=\sum_ka_ke^{jk\omega_0t}}
$$

$$
\boxed{a_k=\frac1T\int_Tx(t)e^{-jk\omega_0t}\,dt}
$$

$$
\boxed{X(j\omega)=\int_{-\infty}^{\infty}x(t)e^{-j\omega t}\,dt}
$$

$$
\boxed{x(t)=\frac1{2\pi}\int_{-\infty}^{\infty}X(j\omega)e^{j\omega t}\,d\omega}
$$

$$
\boxed{\text{Time shift}\Longrightarrow\text{phase change}}
$$

$$
\boxed{\text{Time scaling}\Longrightarrow\text{frequency scaling}}
$$

$$
\boxed{\text{Differentiation}\Longrightarrow\times j\omega}
$$

$$
\boxed{\text{Integration}\Longrightarrow\div j\omega}
$$

$$
\boxed{\text{Multiply by cosine}\Longrightarrow\text{copies at }\pm\omega_c}
$$

$$
\boxed{\text{Rectangle in time}\Longrightarrow\text{sinc in frequency}}
$$

$$
\boxed{\text{Narrow in time}\Longrightarrow\text{wide in frequency}}
$$
