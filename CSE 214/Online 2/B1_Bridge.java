interface MChannel{
   void sendMessage(String message);
}

class Email implements MChannel{
    @Override
    public void sendMessage(String message) {
        System.out.println("Email: "+message);
    }
}

class WhatsApp implements MChannel{
    @Override
    public void sendMessage(String message) {
        System.out.println("WhatsApp: "+message);
    }
}

class PushM implements MChannel{
    @Override
    public void sendMessage(String message) {
        System.out.println("Push Notification: "+message);
    }
}

abstract class AppNotification{
    protected MChannel channel;

    public AppNotification(MChannel channel){
        this.channel=channel;
    }

    abstract public void notifyUser();
}

class paymentFailed extends AppNotification{

    public paymentFailed(MChannel channel) {
        super(channel);
    }

    @Override
    public void notifyUser() {
        channel.sendMessage("Your payment failed");
    }
    
}

class dispatch extends AppNotification{
    public dispatch(MChannel channel){
        super(channel);
    }

    @Override
    public void notifyUser() {
        channel.sendMessage("Your bazar is on the way");
    }
}

class NotificationFactory{
    public static AppNotification create(String notification, String channel){
        MChannel messagChannel;
        if(channel.equalsIgnoreCase("EMAIL")){
            messagChannel=new Email();
        }

        else if(channel.equalsIgnoreCase("WhatsApp")){
            messagChannel= new WhatsApp();
        }

        else messagChannel= new PushM();

        if(notification.equals("Payment")){
            return new paymentFailed(messagChannel);
        }
        else return new dispatch(messagChannel);
    }

}

public class B1_Bridge {
    public static void main(String[] args) {
        AppNotification update= NotificationFactory.create("email", "payment");
        update.notifyUser();
    }
    
}

