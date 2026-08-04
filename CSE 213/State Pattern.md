## Template code
```Java
// ---------- State interface ----------

interface OrderState {
    void pay(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
    String getName();
}


// ---------- Context ----------

class Order {
    private OrderState state;

    public Order() {
        state = new PendingState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void pay() {
        state.pay(this);
    }

    public void ship() {
        state.ship(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public void showState() {
        System.out.println(
                "Current state: " + state.getName()
        );
    }
}


// ---------- Concrete states ----------

class PendingState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("Payment accepted");
        order.setState(new PaidState());
    }

    @Override
    public void ship(Order order) {
        System.out.println(
                "Cannot ship before payment"
        );
    }

    @Override
    public void deliver(Order order) {
        System.out.println(
                "Cannot deliver before shipping"
        );
    }

    @Override
    public void cancel(Order order) {
        System.out.println(
                "Pending order cancelled"
        );

        order.setState(new CancelledState());
    }

    @Override
    public String getName() {
        return "Pending";
    }
}


class PaidState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println(
                "Order has already been paid"
        );
    }

    @Override
    public void ship(Order order) {
        System.out.println("Order shipped");
        order.setState(new ShippedState());
    }

    @Override
    public void deliver(Order order) {
        System.out.println(
                "Order must be shipped first"
        );
    }

    @Override
    public void cancel(Order order) {
        System.out.println(
                "Order cancelled; refund issued"
        );

        order.setState(new CancelledState());
    }

    @Override
    public String getName() {
        return "Paid";
    }
}


class ShippedState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println(
                "Order was already paid"
        );
    }

    @Override
    public void ship(Order order) {
        System.out.println(
                "Order has already been shipped"
        );
    }

    @Override
    public void deliver(Order order) {
        System.out.println("Order delivered");
        order.setState(new DeliveredState());
    }

    @Override
    public void cancel(Order order) {
        System.out.println(
                "Cannot cancel after shipping"
        );
    }

    @Override
    public String getName() {
        return "Shipped";
    }
}


class DeliveredState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println(
                "Delivered order was already paid"
        );
    }

    @Override
    public void ship(Order order) {
        System.out.println(
                "Order has already been delivered"
        );
    }

    @Override
    public void deliver(Order order) {
        System.out.println(
                "Order has already been delivered"
        );
    }

    @Override
    public void cancel(Order order) {
        System.out.println(
                "Cannot cancel a delivered order"
        );
    }

    @Override
    public String getName() {
        return "Delivered";
    }
}


class CancelledState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println(
                "Cannot pay for a cancelled order"
        );
    }

    @Override
    public void ship(Order order) {
        System.out.println(
                "Cannot ship a cancelled order"
        );
    }

    @Override
    public void deliver(Order order) {
        System.out.println(
                "Cannot deliver a cancelled order"
        );
    }

    @Override
    public void cancel(Order order) {
        System.out.println(
                "Order has already been cancelled"
        );
    }

    @Override
    public String getName() {
        return "Cancelled";
    }
}


// ---------- Client ----------

public class Main {
    public static void main(String[] args) {
        Order order = new Order();

        order.showState();

        order.ship();       // Invalid while Pending
        order.pay();        // Pending -> Paid
        order.showState();

        order.pay();        // Already paid
        order.ship();       // Paid -> Shipped
        order.showState();

        order.cancel();     // Invalid after shipping
        order.deliver();    // Shipped -> Delivered
        order.showState();

        order.cancel();     // Invalid after delivery
    }
}
```

## How to recognize State Pattern in an exam

Look for statements such as:

- “The object behaves differently depending on its current state.”
- “Behavior changes when internal condition changes.”
- “The system moves through several stages.”
- “Actions cause transitions from one state to another.”
- “Avoid large `if-else` or `switch` statements based on status.”
- “Each state allows or rejects different operations.”
- “The object appears to change its class at runtime.”
- “Valid operations depend on the current mode.”
- “State transitions must follow specific rules.”

Common scenarios include:

- Order: Pending, Paid, Shipped, Delivered
- Document: Draft, Moderation, Published
- Vending machine: No Coin, Has Coin, Dispensing, Sold Out
- Music player: Playing, Paused, Stopped
- ATM: No Card, Card Inserted, PIN Verified
- Traffic light: Red, Green, Yellow
- Network connection: Disconnected, Connecting, Connected
- Game character: Normal, Stunned, Dead
- Support ticket: Open, Assigned, Resolved, Closed

The strongest giveaway is:

> The same action has a different result depending on the object’s current condition.