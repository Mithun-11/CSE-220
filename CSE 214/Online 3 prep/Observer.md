```Java
import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(double temperature);
}

interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

class WeatherStation implements Subject {
    private final List<Observer> observers =
            new ArrayList<>();

    private double temperature;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature);
        }
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        notifyObservers();
    }
}

class PhoneDisplay implements Observer {
    public void update(double temperature) {
        System.out.println(
                "Phone temperature: " + temperature
        );
    }
}

class WarningSystem implements Observer {
    public void update(double temperature) {
        if (temperature > 40) {
            System.out.println("Warning: Extreme heat!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();

        Observer phone = new PhoneDisplay();
        Observer warning = new WarningSystem();

        station.addObserver(phone);
        station.addObserver(warning);

        station.setTemperature(35);
        station.setTemperature(42);

        station.removeObserver(phone);

        station.setTemperature(45);
    }
}

```

## Pull method
```Java
interface Observer {
    void update(WeatherStation station);
}

class WeatherStation {
    private double temperature;
    private double humidity;

    double getTemperature() {
        return temperature;
    }

    double getHumidity() {
        return humidity;
    }
}

class TemperatureDisplay implements Observer {
    public void update(WeatherStation station) {
        System.out.println(station.getTemperature());
    }
}

class HumidityDisplay implements Observer {
    public void update(WeatherStation station) {
        System.out.println(station.getHumidity());
    }
}
```
