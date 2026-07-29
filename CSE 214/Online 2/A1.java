interface Notification{
    String send(String msg);
}

class Email implements Notification{
    @Override
    public String send(String msg) {
        return "Email: "+ msg;
    }
}

class SMS implements Notification{
    @Override
    public String send(String msg) {
        return "SMS: "+ msg;
    }
}

class Push implements Notification{
    @Override
    public String send(String msg) {
        return "Push: "+msg;
    }
}

abstract class Decorator implements Notification{
    protected Notification wrap;

    public Decorator(Notification wrap){
        this.wrap=wrap;
    }

    @Override
    public String send(String msg) {
        return wrap.send(msg);
    }
}

class Encryption extends Decorator{
    public Encryption(Notification wrap){
        super(wrap);
    }

    @Override
    public String send(String msg) {
        // TODO Auto-generated method stub
        return "Encrypted " + super.send(msg);
    }
}

class Priority extends Decorator{
    public Priority(Notification wrap){
        super(wrap);
    }

    @Override
    public String send(String msg) {
        // TODO Auto-generated method stub
        return "High Priority "+ super.send(msg);
    }
}

class Logging extends Decorator{
    public Logging(Notification wrap){
        super(wrap);
    }

    @Override
    public String send(String msg) {
        // TODO Auto-generated method stub
        return "Logged "+ super.send(msg);
    }
}

public class A1 {

    public static void main(String[] args) {
        
    }
}