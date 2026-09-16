import java.util.*;

enum DisasterCategory {EARTHQUAKE, FLOOD, FIRE}
enum Severity {LOW, MODERATE, HIGH, CRITICAL}

class Alert{
    private String title;
    private DisasterCategory category;
    private String affectedLocation;
    private Severity severityLevel;
    private String safetyInstruction;

    private Alert(){ }

    public DisasterCategory getCategory(){
        return category;
    }

    @Override
    public String toString() {
    return "Title: " + title
            + "\nCategory: " + category
            + "\nAffected location: " + affectedLocation
            + "\nSeverity: " + severityLevel
            + "\nSafety instruction: " + safetyInstruction;
}

    static class Builder{
        private Alert alert=new Alert();

        Builder title(String title){
            alert.title=title;
            return this;
        }

        Builder category(DisasterCategory category){
            alert.category=category;
            return this;
        }

        Builder location(String location){
            alert.affectedLocation=location;
            return this;
        }

        Builder severity(Severity severityLevel){
            alert.severityLevel=severityLevel;
            return this;
        }

        Builder instructions(String instructions){
            alert.safetyInstruction=instructions;
            return this;
        }

        Alert build(){
         if (alert.title == null|| alert.category == null
            || alert.affectedLocation == null || alert.severityLevel == null
            || alert.safetyInstruction == null) {

            throw new IllegalStateException("All alert fields are required");
    }  
            return alert;
        }
    }

}






interface AlertObserver{
    void update(Alert alert);
}

class Citizen implements AlertObserver{
    private final int id;
    private final String name;
    private final List<Alert> receivedAlerts= new ArrayList<>();

    public Citizen(int id, String name){
        this.id=id;
        this.name=name;
    }

    @Override
    public void update(Alert alert) {
        receivedAlerts.add(alert);

        System.out.println(name+ " received: " + alert.getCategory());
    }

    public void displayNotifications(){
        System.out.println("\n Notifications of "+ name +":");
        if(receivedAlerts.isEmpty()){
            System.out.println("No notifications");
            return;
        }

        for(Alert alert: receivedAlerts){
            System.out.println("---------------");
            System.out.println(alert);
        }
    }
}



interface ALertSubject{
    void subscribe(AlertObserver observer, DisasterCategory... categories);
    void unsubscribe(AlertObserver observer,DisasterCategory... categories);
    void notifyObservers(Alert alert);
}

class BDAlert implements ALertSubject{

    private Map<DisasterCategory, Set<AlertObserver>> subscriptions= new HashMap<>();

    public BDAlert(){
        for (DisasterCategory category: DisasterCategory.values()){
            subscriptions.put(category, new HashSet<>());
        }
    }

    @Override
    public void subscribe(AlertObserver observer, DisasterCategory... categories) {
        for(DisasterCategory category: categories){
            subscriptions.get(category).add(observer);
        }
    }

    @Override
    public void unsubscribe(AlertObserver observer, DisasterCategory... categories) {
        for(DisasterCategory category: categories){
            subscriptions.get(category).remove(observer);
        }
    }

    @Override
    public void notifyObservers(Alert alert) {
        DisasterCategory category=alert.getCategory();

        for(AlertObserver observer: subscriptions.get(category)){
            observer.update(alert);
        }
    }
    
}




public class ObserverPattern {
    public static void main(String[] args) {
        BDAlert alertSystem= new BDAlert();

        Citizen harry= new Citizen(1, "Harry");
        Citizen ron= new Citizen(2, "Ron");
        Citizen hermione= new Citizen(3, "Hermione");
        Citizen nevil= new Citizen(4, "Nevil");

        alertSystem.subscribe(nevil,DisasterCategory.EARTHQUAKE,DisasterCategory.FLOOD);
        alertSystem.subscribe(ron, DisasterCategory.FIRE);
        alertSystem.subscribe(harry, DisasterCategory.EARTHQUAKE);
        alertSystem.subscribe(hermione,DisasterCategory.EARTHQUAKE,DisasterCategory.FIRE,DisasterCategory.FLOOD);

        Alert earthquakeAlert = new Alert.Builder()
                .title("Earthquake Warning")
                .category(DisasterCategory.EARTHQUAKE)
                .location("Dhaka")
                .severity(Severity.HIGH)
                .instructions("Move to an open area")
                .build();

        Alert floodAlert = new Alert.Builder()
                .title("Flood Warning")
                .category(DisasterCategory.FLOOD)
                .location("Sylhet")
                .severity(Severity.CRITICAL)
                .instructions("Move to higher ground")
                .build();

        Alert fireAlert = new Alert.Builder()
                .title("Fire Warning")
                .category(DisasterCategory.FIRE)
                .location("Chattogram")
                .severity(Severity.HIGH)
                .instructions("Evacuate the building")
                .build();

        alertSystem.notifyObservers(earthquakeAlert);
        alertSystem.notifyObservers(fireAlert);
        alertSystem.notifyObservers(floodAlert);

        alertSystem.unsubscribe(nevil, DisasterCategory.FLOOD);

        Alert secondFloodAlert = new Alert.Builder()
                .title("Second Flood Warning")
                .category(DisasterCategory.FLOOD)
                .location("Sunamganj")
                .severity(Severity.CRITICAL)
                .instructions("Leave low-lying areas")
                .build();

        System.out.println("After unsubscribing");
        alertSystem.notifyObservers(secondFloodAlert);

       harry.displayNotifications();
       hermione.displayNotifications();
       ron.displayNotifications();
       nevil.displayNotifications(); 
    }
    
}
