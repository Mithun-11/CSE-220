// 1. The Common Interface
interface Notification {
    void notifyUser();
}

// 2. Concrete Classes (The different channels)
class SMSNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an SMS notification...");
    }
}

class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an Email notification...");
    }
}

class PushNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending a Push notification...");
    }
}

// 3. The Factory (Decides which class to instantiate based on the String)
class NotificationFactory {
    public Notification createNotification(String channel) {
        if (channel == null || channel.isEmpty()) {
            return null;
        }
        
        if (channel.equalsIgnoreCase("SMS")) {
            return new SMSNotification();
        } else if (channel.equalsIgnoreCase("EMAIL")) {
            return new EmailNotification();
        } else if (channel.equalsIgnoreCase("PUSH")) {
            return new PushNotification();
        }
        
        return null; // Return null if the string doesn't match
    }
}

// 4. The Client (This is where notifyUser() is called!)
public class NotificationApp {
    public static void main(String[] args) {
        NotificationFactory factory = new NotificationFactory();

        // The client asks the factory for an SMS object using a String
        Notification notification1 = factory.createNotification("SMS");
        
        // The client calls the method on the returned interface!
        if (notification1 != null) {
            notification1.notifyUser(); 
        }

        // The client asks for an Email object
        Notification notification2 = factory.createNotification("EMAIL");
        if (notification2 != null) {
            notification2.notifyUser(); 
        }
    }
}