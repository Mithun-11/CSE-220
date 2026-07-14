class Bicycle{
   private String frame, gearSystem, tireType;

   public void setFrame(String frame)
   {
    this.frame=frame;
   }
   public void setGearSystem(String gearSystem)
   {
    this.gearSystem=gearSystem;
   }
   public void setTireType(String tireType)
   {
    this.tireType=tireType;
   }

   public String toString()
   {
    return "Frame: "+frame+" Gear System: "+gearSystem+
    " Tire Type: "+tireType;
   }

}

interface Builder
{
    void buildFrame();
    void buildGear();
   void buildTyre();

    Bicycle getBicycle();
}

class Commuter implements Builder{
    Bicycle bike=new Bicycle();
    public void buildFrame() {bike.setFrame("Aluminium Frame");}
    public void buildGear() {bike.setGearSystem("Single Speed Gear");}
    public void buildTyre() {bike.setTireType("Road Tires");}

    public Bicycle getBicycle(){return bike;}

}

class MountainBeast implements Builder{
    Bicycle bike = new Bicycle();

    @Override
    public void buildFrame() {
       bike.setFrame("Carbon FIber Frame");
        
    }

    @Override
    public void buildGear() {
        bike.setGearSystem("!2-Speed Gear");
        
    }

    @Override
    public void buildTyre() {
        bike.setTireType("Off-road Frip Tires");
        
    }

    @Override
    public Bicycle getBicycle() {
       
        return bike;
    }
    
}


class Director{
    public void construct(Builder build)
    {
        build.buildFrame();
        build.buildGear();
        build.buildTyre();
    }
}


public class App {
    public static void main(String[] args) throws Exception {
        Director director= new Director();
        Builder bikeBuilder = new Commuter();
        director.construct(bikeBuilder);
        Bicycle bike= bikeBuilder.getBicycle();

        System.out.println(bike);
    }
}
