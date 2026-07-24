import numpy as np
import matplotlib.pyplot as plt
class DiscreteSignal:
    def __init__(self,start_time,end_time):        
        self.start_time=start_time
        self.end_time=end_time
        self.values=np.zeros(end_time-start_time+1)

    def set_value_at_time(self,t, value):
        self.values[t-self.start_time]=value

    def get_value_at_time(self, t):
        if t < self.start_time or t > self.end_time:
            return 0.0
        return self.values[t - self.start_time]

    def shift(self,k):
        shifted_signal= DiscreteSignal(self.start_time+k,self.end_time+k)
        shifted_signal.values=np.copy(self.values)
        return shifted_signal

    def print_signal(self):
        print(self.values)




my_signal=DiscreteSignal(-1,1)
my_signal.set_value_at_time(-1,2)
my_signal.set_value_at_time(0,1)
my_signal.set_value_at_time(1,3)

my_signal.print_signal()

class LTISystem:
    def __init__(self,impulse_response: DiscreteSignal):
        self.impulse_response=impulse_response

    def output(self,input_signal: DiscreteSignal):
        y_start=input_signal.start_time+self.impulse_response.start_time
        y_end=input_signal.end_time+ self.impulse_response.end_time

        y=DiscreteSignal(y_start,y_end)

        for n in range(y_start,y_end+1):
            convolution_sum=0.0

            for k in range(input_signal.start_time,input_signal.end_time+1):
                x_k=input_signal.get_value_at_time(k)
                h_n_miuns_k = self.impulse_response.get_value_at_time(n-k)

                convolution_sum+=x_k*h_n_miuns_k

            y.set_value_at_time(n,convolution_sum)


        print(f"Output time range: {y_start} to {y_end}")
        print(f"Output signal values: {y.values}")


# 1. Setup the input signal x[n] from the example
x = DiscreteSignal(0, 2)
x.set_value_at_time(0, 1)
x.set_value_at_time(1, 2)
x.set_value_at_time(2, 1)

# 2. Setup the impulse response h[n] from the example
h = DiscreteSignal(0, 1)
h.set_value_at_time(0, 1)
h.set_value_at_time(1, -1)

# 3. Create the system and compute the output
system = LTISystem(h)
y = system.output(x)