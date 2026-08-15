import numpy as np

measured = np.deg2rad(-179)
predicted = np.deg2rad(179)

ordinary_difference = measured - predicted

wrapped_difference = np.angle(
    np.exp(1j * ordinary_difference)
)

print(np.rad2deg(ordinary_difference))
print(np.rad2deg(wrapped_difference))


