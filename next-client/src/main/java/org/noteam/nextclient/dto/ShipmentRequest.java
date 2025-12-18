package org.noteam.nextclient.dto;

import java.util.Date;
import java.util.List;

public class ShipmentRequest {
    private List<Integer> ordersIds;
    private int shipmentId;
    private int adminId;
    private String adminName;
    private int vehicleId;
    private int vehicleWeight;
    private boolean vehicleIsAvailable;
    private boolean vehicleIsUsed;
    private boolean driverIsAvailable;
    private boolean driverIsUsed;

    private int driverId;
    private String driverName;
    private int totalWeight;
    private String shippingDate;
    private int cityId;
    private String cityName;
    private String countryName;
    private boolean isComplete;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(int vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public boolean isDriverIsUsed() {
        return driverIsUsed;
    }

    public void setDriverIsUsed(boolean driverIsUsed) {
        this.driverIsUsed = driverIsUsed;
    }

    public boolean isDriverIsAvailable() {
        return driverIsAvailable;
    }

    public void setDriverIsAvailable(boolean driverIsAvailable) {
        this.driverIsAvailable = driverIsAvailable;
    }

    public boolean isVehicleIsUsed() {
        return vehicleIsUsed;
    }

    public void setVehicleIsUsed(boolean vehicleIsUsed) {
        this.vehicleIsUsed = vehicleIsUsed;
    }

    public boolean isVehicleIsAvailable() {
        return vehicleIsAvailable;
    }

    public void setVehicleIsAvailable(boolean vehicleIsAvailable) {
        this.vehicleIsAvailable = vehicleIsAvailable;
    }

    public List<Integer> getOrdersIds() {
        return ordersIds;
    }

    public void setOrdersIds(List<Integer> ordersIds) {
        this.ordersIds = ordersIds;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public ShipmentRequest(int shipmentId, int totalWeight, String shippingDate , String cityName, String countryName ){
        this.shipmentId = shipmentId;
        this.cityName = cityName;
        this.totalWeight = totalWeight;
        this.shippingDate = shippingDate;
        this.countryName = countryName;
    }


    public ShipmentRequest(List<Integer> orderIds, String countryName, String cityName, String shippingDate, int totalWeight, String driverName, int driverId, boolean driverIsUsed, boolean driverIsAvailable, boolean vehicleIsUsed, boolean vehicleIsAvailable, int vehicleWeight, int vehicleId, String adminName, int adminId) {
        this.ordersIds = orderIds;
        this.countryName = countryName;
        this.cityName = cityName;
        this.shippingDate = shippingDate;
        this.totalWeight = totalWeight;
        this.driverName = driverName;
        this.driverId = driverId;
        this.driverIsUsed = driverIsUsed;
        this.driverIsAvailable = driverIsAvailable;
        this.vehicleIsUsed = vehicleIsUsed;
        this.vehicleIsAvailable = vehicleIsAvailable;
        this.vehicleWeight = vehicleWeight;
        this.vehicleId = vehicleId;
        this.adminName = adminName;
        this.adminId = adminId;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    // Getters and setters
    public List<Integer> getOrderIds() { return ordersIds; }
    public void setOrderIds(List<Integer> orderIds) { this.ordersIds = orderIds; }
    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }
    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }
    public int getTotalWeight() { return totalWeight; }
    public void setTotalWeight(int totalWeight) { this.totalWeight = totalWeight; }
    public String getShippingDate() { return shippingDate; }
    public void setShippingDate(String shippingDate) { this.shippingDate = shippingDate; }


}
