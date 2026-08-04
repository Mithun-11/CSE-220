```Java
interface FlyBehavior {
    void fly();
}

class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("Flying with wings");
    }
}

class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("Cannot fly");
    }
}

class FlyRocketPowered implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("Flying with a rocket");
    }
}

abstract class Duck {
    protected FlyBehavior flyBehavior;

    public void performFly() {
        flyBehavior.fly();
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void swim() {
        System.out.println("Swimming");
    }

    public abstract void display();
}

class MallardDuck extends Duck {
    public MallardDuck() {
        flyBehavior = new FlyWithWings();
    }

    @Override
    public void display() {
        System.out.println("Mallard duck");
    }
}

class ModelDuck extends Duck {
    public ModelDuck() {
        flyBehavior = new FlyNoWay();
    }

    @Override
    public void display() {
        System.out.println("Model duck");
    }
}

public class Main {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();

        mallard.performFly();

        Duck model = new ModelDuck();

        model.performFly();

        model.setFlyBehavior(
                new FlyRocketPowered()
        );

        model.performFly();
    }
}
```
