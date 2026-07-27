def cross_correlation(self, input_signal: DiscreteSignal):
        # 1. BOUNDARIES CHANGE
        # Instead of adding start to start and end to end, 
        # we subtract the template's max reach from the signal's start, and vice versa.
        r_start = input_signal.start_time - self.impulse_response.end_time
        r_end = input_signal.end_time - self.impulse_response.start_time

        r = DiscreteSignal(r_start, r_end)

        for n in range(r_start, r_end + 1):
            correlation_sum = 0.0

            for k in range(input_signal.start_time, input_signal.end_time + 1):
                x_k = input_signal.get_value_at_time(k)
                
                # 2. NO FLIP! 
                # The index is (k - n) instead of (n - k)
                h_k_minus_n = self.impulse_response.get_value_at_time(k - n)

                correlation_sum += x_k * h_k_minus_n

            r.set_value_at_time(n, correlation_sum)

        print(f"Output time range: {r_start} to {r_end}")
        print(f"Output signal values: {r.values}")
        
        return r