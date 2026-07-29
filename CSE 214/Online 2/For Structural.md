## Adapter Pattern
###  Exam Cheat Sheet: Keywords to Look For

When reading a problem statement, look for these specific clues that scream "Adapter Pattern":

- "We have a **legacy system** or **third-party library** (vendor code) that we need to integrate..."
- "...but its **interface is incompatible** with our current code."
- "We **cannot modify the existing source code** of the library."
- "Write a **wrapper** to translate the requests from our system to the new library."
- "Make Class X **work with** Class Y.
#### Template Code for adapter pattern
```Java
// ==========================================
// 1. THE TARGET
// ==========================================
// This is the interface your existing system (the Client) expects to use.
public interface Target {
    void request(); 
}

// (Optional) An existing class in your system that already implements the Target
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("Normal request processed.");
    }
}

// ==========================================
// 2. THE ADAPTEE (The Service / Vendor Code)
// ==========================================
// This is the useful class you want to integrate, but its interface 
// is completely incompatible with the Target interface.
public class Adaptee {
    public void specificRequest() {
        System.out.println("Adaptee's specific request processed.");
    }
}

// ==========================================
// 3. THE ADAPTER
// ==========================================
// This is the bridge you write. It MUST implement the Target interface
// and it MUST hold a reference to the Adaptee (Composition).
public class Adapter implements Target { // Step 1: Implement Target[cite: 1]
    
    private Adaptee adaptee; // Step 2: Hold a reference to Adaptee[cite: 1]
    
    // Step 3: Inject the Adaptee via the constructor[cite: 1]
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }
    
    // Step 4: Translate the Target's method call into the Adaptee's method call[cite: 1]
    @Override
    public void request() {
        // You might need to do some data conversion or loop multiple times here
        // before or after delegating to the adaptee.
        adaptee.specificRequest(); 
    }
}

// ==========================================
// 4. THE CLIENT
// ==========================================
// The Client only knows how to talk to objects that implement the Target interface.
public class Client {
    
    public static void main(String[] args) {
        // Scenario A: Using the system normally with expected objects
        Target normalTarget = new ConcreteTarget();
        clientLogic(normalTarget);
        
        // Scenario B: Using the new Adaptee via our Adapter
        Adaptee usefulService = new Adaptee();
        Target adapter = new Adapter(usefulService); // Wrap it![cite: 1]
        
        // The client logic accepts the Adapter because it implements Target.
        // It has no idea it is actually talking to the Adaptee behind the scenes.[cite: 1]
        clientLogic(adapter);
    }
    
    // This method represents your existing system that expects the Target interface[cite: 1]
    public static void clientLogic(Target target) {
        target.request(); 
    }
}
```

## Bridge Pattern
#### Template for Bridge Pattern
```Java
// ==========================================
// 1. THE IMPLEMENTATION HIERARCHY
// ==========================================
// This interface defines the low-level operations.
public interface Color {
    void applyColor();
}

// Concrete Implementation A
public class Red implements Color {
    @Override
    public void applyColor() {
        System.out.println("applying red color.");
    }
}

// Concrete Implementation B
public class Blue implements Color {
    @Override
    public void applyColor() {
        System.out.println("applying blue color.");
    }
}

// ==========================================
// 2. THE ABSTRACTION HIERARCHY
// ==========================================
// This provides the high-level control logic.
public abstract class Shape {
    // THIS IS THE BRIDGE! 
    // We use composition to hold a reference to the Implementation.
    protected Color color; 
    
    // The implementation is injected via the constructor
    public Shape(Color color) {
        this.color = color;
    }
    
    abstract void draw();
}

// Refined Abstraction A
public class Circle extends Shape {
    
    public Circle(Color color) {
        super(color); // Pass the implementation up to the base class
    }
    
    @Override
    public void draw() {
        System.out.print("Drawing a Circle and ");
        color.applyColor(); // Delegating the low-level work
    }
}

// Refined Abstraction B
public class Square extends Shape {
    
    public Square(Color color) {
        super(color);
    }
    
    @Override
    public void draw() {
        System.out.print("Drawing a Square and ");
        color.applyColor(); // Delegating the low-level work
    }
}

// ==========================================
// 3. THE CLIENT
// ==========================================
public class Client {
    public static void main(String[] args) {
        // The client creates the implementation object...
        Color red = new Red();
        Color blue = new Blue();
        
        // ...and passes it to the abstraction object.
        Shape redCircle = new Circle(red);
        Shape blueSquare = new Square(blue);
        
        // The client only interacts with the high-level abstraction.
        redCircle.draw();   // Output: Drawing a Circle and applying red color.
        blueSquare.draw();  // Output: Drawing a Square and applying blue color.
    }
}
```

### Exam Cheat Sheet: Keywords to Look For

When reading a problem statement, these are the massive red flags that the question wants the Bridge pattern:

- "The system is varying in **two independent dimensions**."
- "We want to avoid a **class explosion** or a geometric progression of subclasses."
- "We need to decouple the **abstraction** from its **implementation** so they can be developed independently."
- "We are building a **cross-platform application** (or UI) that needs to run on multiple operating systems."

## Decorator Pattern
```Java
// ==========================================
// 1. THE COMPONENT INTERFACE
// ==========================================
// Both the core object and the decorators must implement this.
public interface Component {
    void execute();
}

// ==========================================
// 2. THE CONCRETE COMPONENT
// ==========================================
// This is the core object being wrapped. It does the basic work.
public class ConcreteComponent implements Component {
    @Override
    public void execute() {
        System.out.println("Doing the core, basic work.");
    }
}

// ==========================================
// 3. THE BASE DECORATOR
// ==========================================
// This class is the secret sauce. It implements the interface AND
// holds a reference to an object of that same interface.
public abstract class BaseDecorator implements Component {
    // The "wrappee" is the inner object we are decorating
    protected Component wrappee; 

    // We PASS the inner object in through the constructor
    public BaseDecorator(Component c) {
        this.wrappee = c;
    }

    // Default behavior: just pass the request down the chain to the wrappee
    @Override
    public void execute() {
        wrappee.execute(); 
    }
}

// ==========================================
// 4. CONCRETE DECORATORS
// ==========================================
public class ConcreteDecorator1 extends BaseDecorator {
    
    // Pass the wrappee up to the BaseDecorator's constructor
    public ConcreteDecorator1(Component c) {
        super(c); 
    }

    @Override
    public void execute() {
        // 1. Call super.execute(). This travels up to BaseDecorator, 
        // which tells the inner 'wrappee' to execute first.
        super.execute(); 
        
        // 2. Do this decorator's extra work!
        extra(); 
    }

    private void extra() {
        System.out.println("Adding extra behavior from Decorator 1!");
    }
}

public class ConcreteDecorator2 extends BaseDecorator {
    
    public ConcreteDecorator2(Component c) {
        super(c);
    }

    @Override
    public void execute() {
        super.execute(); 
        extra(); 
    }

    private void extra() {
        System.out.println("Adding extra behavior from Decorator 2!");
    }
}

// ==========================================
// 5. THE CLIENT
// ==========================================
public class Client {
    public static void main(String[] args) {
        
        // Step 1: Create the core object
        Component a = new ConcreteComponent();
        
        // Step 2: Pass 'a' into Decorator1. 
        // Now 'b' is a Decorator1 that wraps 'a'.
        Component b = new ConcreteDecorator1(a);
        
        // Step 3: Pass 'b' into Decorator2.
        // Now 'c' is a Decorator2 that wraps 'b' (which wraps 'a').
        Component c = new ConcreteDecorator2(b);
        
        // Step 4: Execute the outermost layer.
        c.execute();
    }
}
```

### Key Phrases to Look For

- **"Add/attach new behaviors dynamically"** or **"at runtime":** The core intent of the Decorator is to let you attach new behaviors to objects on the fly. If the problem asks you to add features to an object while the program is running, it is likely a Decorator.
- **"Wrapper"** or **"Wrap an object":** The problem might literally tell you to place objects inside special wrapper objects that contain the new behaviors.
- **"Without altering existing code":** The problem states you need to add new responsibilities or behaviors to a class, but you are strictly forbidden from changing the existing class's source code.
- **"Same interface"** or **"Mirrors the type":** You need to add functionality to an object, but the client code must be able to treat the modified object exactly the same as the original, unmodified object. The decorator mirrors the type of the object it is decorating.
### Decorator vs. Adapter (A Quick Reminder)

Exams love to try and trick you into confusing these two because both involve "wrapping" an object.
- If you are wrapping an object to **convert its interface** so it can talk to something else, use an **Adapter**.
- If you are wrapping an object to **extend its behavior** but keeping the exact same interface, use a **Decorator**.
## Composite Pattern
```Java
import java.util.ArrayList;
import java.util.List;

// ==========================================
// 1. THE COMPONENT INTERFACE
// ==========================================
// The common interface for both wrappers and wrapped objects.
public interface Component {
    void execute();
}

// ==========================================
// 2. THE LEAF
// ==========================================
// The basic element. It has no children. It does the real work.
public class Leaf implements Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        // Do some work
        System.out.println("Leaf " + name + " is doing the actual work.");
    }
}

// ==========================================
// 3. THE COMPOSITE (CONTAINER)
// ==========================================
// This element holds sub-elements (Leaves or other Composites)[cite: 1].
public class Composite implements Component {
    private String name;
    
    // The field's type is declared as the component interface so it 
    // can contain both concrete components and decorators/composites.
    private List<Component> children = new ArrayList<>();

    public Composite(String name) {
        this.name = name;
    }

    // Container-specific methods to manage children[cite: 1]
    public void add(Component c) {
        children.add(c);
    }

    public void remove(Component c) {
        children.remove(c);
    }

    public List<Component> getChildren() {
        return children;
    }

    // The crucial delegation step!
    @Override
    public void execute() {
        System.out.println("Composite " + name + " is delegating to its children:");
        
        // Delegate all work to child components[cite: 1]
        for (Component child : children) {
            child.execute(); 
        }
    }
}

// ==========================================
// 4. THE CLIENT
// ==========================================
public class Client {
    public static void main(String[] args) {
        // Create leaves (the end items)
        Component leaf1 = new Leaf("Item A");
        Component leaf2 = new Leaf("Item B");
        Component leaf3 = new Leaf("Item C");

        // Create a composite (a sub-menu or container)
        Composite subContainer = new Composite("Sub-Menu");
        subContainer.add(leaf1);
        subContainer.add(leaf2);

        // Create a root composite (the main menu or master container)
        Composite rootContainer = new Composite("Main Menu");
        rootContainer.add(subContainer); // Adding a composite to a composite!
        rootContainer.add(leaf3);        // Adding a leaf directly to the root!

        // The client simply calls execute on the root. 
        // The tree structure handles the rest automatically.
        System.out.println("--- Client initiating request ---");
        rootContainer.execute();
    }
}
```
