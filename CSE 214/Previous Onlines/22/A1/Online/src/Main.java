interface Transport {
    void deliver();
}

class Truck implements Transport {
    public void deliver() { System.out.println("Delivering by land in a truck."); }
}

class Ship implements Transport {
    public void deliver() { System.out.println("Delivering by sea in a ship."); }
}

// Creator
 class Logistics {
    public Transport planDelivery(String s) {   
        s=s.toUpperCase();                // client-facing code, unchanged
        if(s.equals("ROAD"))
            return new Truck();

        else if(s.equals("SHIP")) return new Ship();

        return null;
    }
}


public class Main {
    public static void main(String[] args) {
        String mode = "Road";  // client input
        Logistics logistics=new Logistics();

       Transport t= logistics.planDelivery(mode);
       t.deliver();
    }
}