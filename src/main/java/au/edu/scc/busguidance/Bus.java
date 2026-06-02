package au.edu.scc.busguidance;

import java.util.Objects;

public class Bus {
    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType;

    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {
        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = fuelType;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Bus bus)) {
            return false;
        }
        return capacity == bus.capacity
                && Double.compare(fuelLevel, bus.fuelLevel) == 0
                && Objects.equals(busID, bus.busID)
                && Objects.equals(fuelType, bus.fuelType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(busID, capacity, fuelLevel, fuelType);
    }
}
