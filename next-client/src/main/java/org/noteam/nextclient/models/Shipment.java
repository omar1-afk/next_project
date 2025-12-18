package org.noteam.nextclient.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Shipment {
    private List<Integer> ordersIds;
    private  int shipmentId;
    private Admin admin;
    private Vehicle vehicle;
    private int vehicleId = vehicle.getVehicleId();
    //private String vehicleType= vehicle.;
     private Driver driver;
    private String driverName= driver.getName();
    private double totalWeight;
    private LocalDate shippingDate;
    private City city;
    private String cityName = city.getName();

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private boolean isComplete;

    public Shipment(int shipmentId, Admin admin, Vehicle vehicle, int vehicleId, String vehicleType, Driver driver, String driverName, double totalWeight, LocalDate shippingDate, City city, String cityName, boolean isComplete) {
        this.shipmentId = shipmentId;
        this.admin = admin;
        this.vehicle = vehicle;
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.driver = driver;
        this.driverName = driverName;
        this.totalWeight = totalWeight;
        this.shippingDate = shippingDate;
        this.city = city;
        this.cityName = cityName;
        this.isComplete = isComplete;
    }

    public List<Integer> getOrdersIds() { return ordersIds; }
   public void setOrdersIds(List<Integer> ordersIds) { this.ordersIds = ordersIds; }
    public Admin getAdmin() {return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
    public double getTotalWeight() { return totalWeight; }
    public void setTotalWeight(double totalWeight) { this.totalWeight = totalWeight; }
    public LocalDate getShippingDate() { return shippingDate; }
    public void setShippingDate(LocalDate shippingDate) { this.shippingDate = shippingDate; }
    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
    public boolean isComplete() { return isComplete; }
    public void setComplete(boolean complete) { this.isComplete = complete; }
    public int getShipmentId() {return shipmentId;}
    public void setShipmentId(int shipmentId) {this.shipmentId = shipmentId;}
}
