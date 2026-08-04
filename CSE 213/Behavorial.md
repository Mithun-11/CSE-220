##  How to recognize Mediator in an exam

Look for statements such as:

- “Many objects communicate with each other.”
- “Components are tightly coupled.”
- “Avoid direct communication between components.”
- “Components should be reusable independently.”
- “A central controller should coordinate interactions.”
- “When one component changes, several others must react.”
- “Adding a new component requires modifying many existing components.”
- “Reduce chaotic many-to-many dependencies.”
- “All communication must pass through a central object.”

Common scenarios include:

- UI forms and dialogs
- Air-traffic control
- Chat rooms
- Smart-home controllers
- Workflow coordinators
- Railway-control systems
- Multiplayer-game lobbies

The strongest giveaway is:

> Many peer objects are directly connected, and a central coordinator should manage their interactions.


## Handwritten-exam skeleton

```Java
interface Mediator {
    void notify(Component sender, String event);
}

abstract class Component {
    protected Mediator mediator;

    public Component(Mediator mediator) {
        this.mediator = mediator;
    }
}

class ComponentA extends Component {
    public ComponentA(Mediator mediator) {
        super(mediator);
    }

    public void action() {
        mediator.notify(this, "A_EVENT");
    }

    public void react() {
        // Component A's own action
    }
}

class ComponentB extends Component {
    public ComponentB(Mediator mediator) {
        super(mediator);
    }

    public void action() {
        mediator.notify(this, "B_EVENT");
    }

    public void react() {
        // Component B's own action
    }
}

class ConcreteMediator implements Mediator {
    private ComponentA a;
    private ComponentB b;

    public ConcreteMediator() {
        a = new ComponentA(this);
        b = new ComponentB(this);
    }

    @Override
    public void notify(
            Component sender,
            String event) {

        if (sender == a
                && event.equals("A_EVENT")) {

            b.react();
        }

        if (sender == b
                && event.equals("B_EVENT")) {

            a.react();
        }
    }
}
```

Memorize the direction:

```
Component knows Mediator
        ↓
Component reports an event
        ↓
ConcreteMediator knows all Components
        ↓
Mediator decides who reacts
```

The single most important line is:

```
mediator.notify(this, "event");
```

It means:

> “I will not contact my colleague directly. I will tell the coordinator what happened and let it manage the interaction.”

Final memory sentence:

> Mediator removes direct component-to-component communication by placing their interaction rules inside one central coordinating object.
