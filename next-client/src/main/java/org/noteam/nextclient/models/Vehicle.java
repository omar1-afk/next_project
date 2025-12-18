package org.noteam.nextclient.models;

import java.util.Date;

public class Vehicle {
    private int vehicleId;
    public enum vehicleType{
        VAN("van"),
        TRUCK("Truck");
        private String type;
        vehicleType(String type){
            this.type=type;
        }
        @Override
        public String toString(){
            return type;
        }
    }

    private String licensePlate;
    private boolean isAvailable;
    private boolean isUsed;
    private int wight;
    private vehicleType vehicleType;

    public vehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(vehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Vehicle(int vehicleId, int wight, String licensePlate , boolean isAvailable, boolean isUsed  ){

        this.vehicleId = vehicleId;
        this.wight = wight;
        this.licensePlate = licensePlate;
        this.isAvailable = isAvailable;
        this.isUsed = isUsed;



    }
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }


    public int getWight() {
        return wight;
    }

    public void setWight(int wight) {
        this.wight = wight;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
