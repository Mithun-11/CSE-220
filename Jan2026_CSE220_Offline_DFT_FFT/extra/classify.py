def classify_inputs(*inputs):
    arrays = [np.asarray(x) for x in inputs]

    for array in arrays:
        if not np.issubdtype(array.dtype, np.number):
            raise TypeError("all inputs must contain numeric values")

    has_complex_values = any(
        np.any(np.imag(array) != 0)
        for array in arrays
    )

    all_integer_valued = all(
        np.all(np.isfinite(array))
        and np.all(np.imag(array) == 0)
        and np.all(np.real(array) == np.rint(np.real(array)))
        for array in arrays
    )

    if has_complex_values:
        return "complex"

    if all_integer_valued:
        return "integer"

    return "real"


input_type = classify_inputs(a, b)

result = engine.inverse(A * B)

if input_type == "integer":
    result = np.rint(result.real).astype(np.int64)

elif input_type == "real":
    result = np.real_if_close(result)

# For complex inputs, leave result as complex128.