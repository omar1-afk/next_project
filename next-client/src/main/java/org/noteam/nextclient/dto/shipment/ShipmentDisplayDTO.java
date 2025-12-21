package org.noteam.nextclient.dto.shipment;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.noteam.nextclient.models.*;

public class ShipmentDisplayDTO {
        private IntegerProperty shipmentId=new SimpleIntegerProperty();

        private IntegerProperty totalWeight=new SimpleIntegerProperty();
        private StringProperty shippingDate=new SimpleStringProperty();
        private StringProperty cityName=new SimpleStringProperty();
        private String countryName;
        private boolean isComplete;
        private int cityId;
        private int driverId;
       private int vehicleId;
       private VehicleObj vehicle;
       private Country country=new Country(1,"Egypt");

    private City city =new City(1,"Alexandria",country);
    private DriverObj driver;

    public ShipmentDisplayDTO(int shipmentId, int totalWeight, String shippingDate,
                              String cityName, String countryName, boolean isComplete,
                              int cityId, int driverId, int vehicleId,City city, DriverObj driver) {
        this.shipmentId.set(shipmentId);
        this.totalWeight.set(totalWeight);
        this.shippingDate.set(shippingDate);
        this.cityName.set(cityName);
        this.countryName = countryName;
        this.isComplete = isComplete;
        this.cityId = cityId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.city = city;
        this.driver = driver;

    }

        // Getters and Setters
        public IntegerProperty shipmentIdProperty() { return shipmentId; }
        public void setShipmentId(int shipmentId) { this.shipmentId.set( shipmentId); }

        public IntegerProperty totalWeightProperty() { return totalWeight; }
        public void setTotalWeight(IntegerProperty totalWeight) { this.totalWeight=totalWeight; }

        public StringProperty shippingDateProperty() { return shippingDate; }
        public void setShippingDate(StringProperty shippingDate) { this.shippingDate=shippingDate;  }

        public StringProperty cityNameProperty() { return cityName; }
        public void setCityName(StringProperty cityName) { this.cityName=cityName;  }

        public String getCountryName() { return countryName; }
        public void setCountryName(String countryName) { this.countryName = countryName; }

        public boolean isComplete() { return isComplete; }
       public void setComplete(boolean complete) { isComplete = complete; }
       public int getCityId() { return cityId; }
       public int getDriverId() { return driverId; }
       public int getVehicleId() { return vehicleId; }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }


    public void setVehicle(VehicleObj vehicle) {
        this.vehicle = vehicle;
    }

    public DriverObj getDriver() {
        return driver;
    }

    public void setDriver(DriverObj driver) {
        this.driver = driver;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
}
