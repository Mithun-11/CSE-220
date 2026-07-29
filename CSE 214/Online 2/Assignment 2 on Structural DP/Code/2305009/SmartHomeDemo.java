import java.util.*;

interface SmartDevice{
    void activate();
    void deactivate();
    double getPowerUsage();
    String getStatus();
}

interface Container extends SmartDevice{
    List<SmartDevice> getDevices();
}
class SmartLight implements SmartDevice{

    private boolean status=false;
    @Override
    public void activate() {status=true; }

    @Override
    public void deactivate() {status=false; }

    @Override
    public double getPowerUsage() {
        return status ? 10.0 : 0.0;
    }

    @Override
    public String getStatus() {
        return "Light: "  + (status? "ON": "OFF")  ;
    }
    
}

class SmartThermostat implements SmartDevice{
    private boolean status=false;
    @Override
    public void activate() {status=true; }

    @Override
    public void deactivate() {status=false; }

    @Override
    public double getPowerUsage() {
        return status ? 150.0 : 0.0;
    }

    @Override
    public String getStatus() {
        return "Thermostat: "  + (status? "ON": "OFF")  ;
    }
}

class SmartSpeaker implements SmartDevice{
    private boolean status=false;
    @Override
    public void activate() {status=true; }

    @Override
    public void deactivate() {status=false; }

    @Override
    public double getPowerUsage() {
        return status ? 5.0 : 0.0;
    }

    @Override
    public String getStatus() {
        return "Speaker: "  + (status? "ON": "OFF")  ;
    }
}

class Room implements Container{
    private String name;
    private List<SmartDevice> devices= new ArrayList<>();

    public Room(String name){
        this.name=name;
    }

    public void addDevice(SmartDevice device){
        devices.add(device);
    }

    public void removeDevice(SmartDevice device){
        devices.remove(device);
    }

    public List<SmartDevice> getDevices(){
        return new ArrayList<>(devices);
    }

    @Override
    public void activate() {
        
        for (SmartDevice device : devices) {
            device.activate();
        }
    }

    @Override
    public void deactivate() {
        for(SmartDevice device: devices){
            device.deactivate();
        }
        
    }

    @Override
    public double getPowerUsage() {
        double total=0.0;
        for (SmartDevice device : devices) {
            total+=device.getPowerUsage();
        }
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb= new StringBuilder("[" + name+ "]");
        for (SmartDevice device : devices) {
            sb.append("\n ").append(device.getStatus());
        }
        return sb.toString();
    }
    
}

class Home implements Container{
    private String name;
    private List<SmartDevice> rooms= new ArrayList<>();

    public Home(String name){
        this.name=name;
    }

    public void addRoom(SmartDevice room){
        rooms.add(room);
    }

    public void removeRoom(SmartDevice room){
        rooms.remove(room);
    }

    @Override
    public void activate() {
        for (SmartDevice room : rooms) {
            room.activate();
        }
        
    }

    @Override
    public void deactivate() {
        for (SmartDevice room : rooms) {
            room.deactivate();
        }
        
    }

    @Override
    public double getPowerUsage() {
        double total=0.0;
        for (SmartDevice room : rooms) {
         total+=room.getPowerUsage();   
        }
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("=== " + name + " ===");
        for (SmartDevice room : rooms) {
            sb.append("\n").append(room.getStatus());
        }
        return sb.toString();
    }

    @Override
    public List<SmartDevice> getDevices() {
        return new ArrayList<>(rooms);
    }
    
}

abstract class Decorator implements SmartDevice{
    protected SmartDevice wrapee;

    public Decorator(SmartDevice wrapee){
        this.wrapee=wrapee;
    }

    public SmartDevice getWrapee(){
        return wrapee;
    }

    @Override
    public void activate() {wrapee.activate();}

    @Override
    public void deactivate() {wrapee.deactivate(); }

    @Override
    public double getPowerUsage() {return wrapee.getPowerUsage(); }

    @Override
    public String getStatus() {return wrapee.getStatus();}
    
}

abstract class ContainerDecorator extends Decorator implements Container{
    protected Container wrapee;
    public ContainerDecorator(Container wrapee){
        super(wrapee);
        this.wrapee=wrapee;
    }

    @Override
    public List<SmartDevice> getDevices() {
        return wrapee.getDevices();
    }
}

class AccessRestricted extends Decorator{
    private final int pin;
    private boolean locked;

    public AccessRestricted(SmartDevice wrapee, int pin){
        super(wrapee);
        this.pin=pin;
        this.locked=true;
    }

    public void lock()
    {
        locked=true;
    }

    public void unlock(int given){
        if(given==pin) locked=false;
    }

    @Override
    public void activate() {
        if(!locked) super.activate();
    }

    @Override
    public void deactivate() {
        if(!locked) super.deactivate();
    }

    @Override
    public String getStatus() {
        
        return super.getStatus() + (locked ? " LOCKED" : "");
    }
    
}

class TimerControlled extends Decorator{
    private final int duration;
    private boolean timerRunning;

    public TimerControlled(SmartDevice wrapee,int duration){
        super(wrapee);
        this.duration=duration;
        timerRunning=false;
    }

    public void simulateTimerExpiry(){
        if(timerRunning) deactivate();
    }

    @Override
    public void activate() {
        super.activate();
        timerRunning=true;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        timerRunning=false;
    }

    @Override
    public String getStatus() {
        return super.getStatus() + (timerRunning ? " [auto-off in " + duration + 
                    " seconds]": "");
    }

    
}

class PowerThrottled extends Decorator{
    SmartDevice wrapee;
    private final int powerCap;

    public PowerThrottled(SmartDevice wrapee, int powerCap){
        super(wrapee);
        this.powerCap=powerCap;
    }

    @Override
    public double getPowerUsage() {
        return Math.min(super.getPowerUsage(), powerCap);
    }

    @Override
    public String getStatus() {
        return super.getStatus() + (super.getPowerUsage() > powerCap ? 
                    " [throttled to " + powerCap + "W]": "");
    }
}

class EcoMode extends ContainerDecorator{
    private final double powerBudget;

    public EcoMode(Container wrapee, double powerBudget){
        super(wrapee);
        this.powerBudget=powerBudget;
    }

    @Override
    public void activate() {
        super.activate();
        List<SmartDevice> devices= getDevices();
        for (int i = devices.size()-1; i >= 0; i--) {
            if (super.getPowerUsage() > powerBudget) {
                devices.get(i).deactivate();
            } 
            else {
                break;
            }
        }
    }

    @Override
    public String getStatus() {
        return "[ECO: "+ powerBudget + "W budget]\n"+ super.getStatus();
    }
}

class GuestMode extends ContainerDecorator{
    private Set<Class<?>> allowed;

    public GuestMode(Container wrapee,Set<Class<?>> allowed ){
        super(wrapee);
        this.allowed=allowed;
    }

    private boolean isAllowed(SmartDevice device){
        SmartDevice realType=device;
        while(realType instanceof Decorator ){
            realType=((Decorator) realType).getWrapee();
        }

        return allowed.contains(realType.getClass());
    }

    @Override
    public void activate() {
        for(SmartDevice device: getDevices()){
            if(isAllowed(device)) device.activate();
        }
    }

    @Override
    public double getPowerUsage() {
        double total=0.0;
        for(SmartDevice device: getDevices()){
            if(isAllowed(device)) total+=device.getPowerUsage();
        }

        return total;
    }

    @Override
    public String getStatus() {
        String prevStatus= super.getStatus();
        String[] strs=prevStatus.split("\n");
        StringBuilder sb= new StringBuilder("[GUEST MODE]\n");
        sb.append(strs[0]);

        for(SmartDevice device: getDevices()){
            sb.append("\n ").append(device.getStatus());
            if(!isAllowed(device)) sb.append(" [guest-restricted]");
        }

        return sb.toString();
    }
    
}

public class SmartHomeDemo {
    
}
