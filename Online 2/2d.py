import numpy as np

def convolve_2d(input_matrix, kernel):
    """
    Performs 2D convolution between an input matrix and a kernel.
    """
    # 1. Get the dimensions of both matrices
    input_height, input_width = input_matrix.shape
    kernel_height, kernel_width = kernel.shape
    
    # 2. Calculate the dimensions of the output matrix
    # If a 5x5 image has a 3x3 kernel, it can only slide 3 times horizontally and vertically.
    # Output size = (5 - 3 + 1) = 3x3
    output_height = input_height - kernel_height + 1
    output_width = input_width - kernel_width + 1
    
    # Create an empty output matrix filled with zeros
    output_matrix = np.zeros((output_height, output_width))
    
    # 3. Slide the kernel across the input matrix
    for row in range(output_height):
        for col in range(output_width):
            
            # Extract the specific "patch" of the input that the kernel is currently covering
            patch = input_matrix[row : row + kernel_height, col : col + kernel_width]
            
            # Multiply the patch by the kernel (element-wise) and sum all the values
            convolved_value = np.sum(patch * kernel)
            
            # Store the resulting single number in the output matrix
            output_matrix[row, col] = convolved_value
            
    return output_matrix