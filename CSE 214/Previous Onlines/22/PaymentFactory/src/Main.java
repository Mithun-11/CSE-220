// Product
interface Payment {
    void processPayment(double amount);
}

// Concrete Products
class CreditCardPayment implements Payment {
    public void processPayment(double amount) {
        System.out.println("Payment of $" + amount + " successful via Credit Card.");
    }
}

class PayPalPayment implements Payment {
    public void processPayment(double amount) {
        System.out.println("Payment of $" + amount + " successful via PayPal.");
    }
}

class BitcoinPayment implements Payment {
    public void processPayment(double amount) {
        System.out.println("Payment of $" + amount + " successful via Bitcoin.");
    }
}

// Creator
abstract class PaymentProcessor {
    public abstract Payment createPayment();   // factory method

    public void pay(double amount) {           // client-facing code, never changes
        Payment payment = createPayment();
        payment.processPayment(amount);
    }
}

// Concrete Creators
class CreditCardProcessor extends PaymentProcessor {
    public Payment createPayment() { return new CreditCardPayment(); }
}

class PayPalProcessor extends PaymentProcessor {
    public Payment createPayment() { return new PayPalPayment(); }
}

class BitcoinProcessor extends PaymentProcessor {
    public Payment createPayment() { return new BitcoinPayment(); }
}

public class Main {
    public static void main(String[] args) {
        String choice = "PayPal";   // user selection

        PaymentProcessor processor;
        if (choice.equalsIgnoreCase("CreditCard"))   processor = new CreditCardProcessor();
        else if (choice.equalsIgnoreCase("PayPal"))  processor = new PayPalProcessor();
        else                                         processor = new BitcoinProcessor();

        processor.pay(250.0);
    }
}