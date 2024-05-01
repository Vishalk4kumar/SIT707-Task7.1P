package sit707_week7;

public class TemperatureReading {
    
    // Customer object
    private Customer customer;
    
    // reading time string format hh:mm:ss
    private String readingTime;
    
    // Body temperature
    private double bodyTemperature;

    // Getter and setter for bodyTemperature
    public double getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(double bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }
}