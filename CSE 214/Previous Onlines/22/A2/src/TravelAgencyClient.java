// 1. The Product
class HolidayPackage {
    private String flight;
    private String hotel;
    private String activity;

    public void setFlight(String flight) { this.flight = flight; }
    public void setHotel(String hotel) { this.hotel = hotel; }
    public void setActivity(String activity) { this.activity = activity; }

    @Override
    public String toString() {
        return "Package [Flight=" + flight + ", Hotel=" + hotel + ", Activity=" + activity + "]";
    }
}

// 2. The Builder Interface
interface PackageBuilder {
    void buildFlight();
    void buildHotel();
    void buildActivity();
    HolidayPackage getPackage();
}

// 3. Concrete Builder A
class RelaxationPackageBuilder implements PackageBuilder {
    private HolidayPackage holidayPackage = new HolidayPackage();

    public void buildFlight() { holidayPackage.setFlight("Business Class Flight"); }
    public void buildHotel() { holidayPackage.setHotel("5-Star Resort"); }
    public void buildActivity() { holidayPackage.setActivity("Spa Treatment"); }
    public HolidayPackage getPackage() { return holidayPackage; }
}

// 4. Concrete Builder B
class AdventurePackageBuilder implements PackageBuilder {
    private HolidayPackage holidayPackage = new HolidayPackage();

    public void buildFlight() { holidayPackage.setFlight("Economy Flight"); }
    public void buildHotel() { holidayPackage.setHotel("Mountain Cabin"); }
    public void buildActivity() { holidayPackage.setActivity("Hiking Tour"); }
    public HolidayPackage getPackage() { return holidayPackage; }
}

// 5. The Director
class Director {
    public void constructPackage(PackageBuilder builder) {
        builder.buildFlight();
        builder.buildHotel();
        builder.buildActivity();
    }
}

// 6. Client Code
public class TravelAgencyClient {
    public static void main(String[] args) {
        Director director = new Director();

        // Build a Relaxation Package
        PackageBuilder relaxationBuilder = new RelaxationPackageBuilder();
        director.constructPackage(relaxationBuilder);
        HolidayPackage relaxation = relaxationBuilder.getPackage();
        System.out.println(relaxation);

        // Build an Adventure Package using the exact same Director process
        PackageBuilder adventureBuilder = new AdventurePackageBuilder();
        director.constructPackage(adventureBuilder);
        HolidayPackage adventure = adventureBuilder.getPackage();
        System.out.println(adventure);
    }
}