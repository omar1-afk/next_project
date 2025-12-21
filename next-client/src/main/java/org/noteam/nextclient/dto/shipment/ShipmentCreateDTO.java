package org.noteam.nextclient.dto.shipment;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShipmentCreateDTO {
    private final List<Integer> orderIds;
    private final int vehicleId;
    private final int driverId;
    private final int cityId;
    private final String shippingDate;
    private final int adminId;
    private final int totalWeight;

    private ShipmentCreateDTO(Builder builder) {
        this.orderIds = Collections.unmodifiableList(new ArrayList<>(builder.orderIds));
        this.vehicleId = builder.vehicleId;
        this.driverId = builder.driverId;
        this.cityId = builder.cityId;
        this.shippingDate = builder.shippingDate;
        this.adminId = builder.adminId;
        this.totalWeight=builder.totalWeight;
    }

    public static class Builder {
        private List<Integer> orderIds = new ArrayList<>();
        private int vehicleId;
        private int driverId;
        private int cityId;
        private String shippingDate;
        private int adminId;
        private int totalWeight;

        public Builder orderIds(List<Integer> orderIds) {
            if (orderIds != null) {
                this.orderIds = new ArrayList<>(orderIds);
            }
            return this;
        }

        public Builder vehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder driverId(int driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder cityId(int cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder shippingDate(String shippingDate) {
            this.shippingDate = shippingDate;
            return this;
        }

        public Builder adminId(int adminId) {
            this.adminId = adminId;
            return this;
        }
        public Builder totalWeight(int totalWeight) {
            this.totalWeight= totalWeight;
            return this;
        }


        public ShipmentCreateDTO build() {
            validate();
            return new ShipmentCreateDTO(this);
        }
        private void validate() {
            if (vehicleId <= 0) throw new IllegalArgumentException("Vehicle ID must be positive");
            if (driverId <= 0) throw new IllegalArgumentException("Driver ID must be positive");
            if (cityId <= 0) throw new IllegalArgumentException("City ID must be positive");
            if (shippingDate == null || shippingDate.trim().isEmpty()) {
                throw new IllegalArgumentException("Shipping date is required");
            }
            if (adminId <= 0) throw new IllegalArgumentException("Admin ID must be positive");
        }
    }

    // Getters
    public List<Integer> getOrderIds() { return orderIds; }
    public int getVehicleId() { return vehicleId; }
    public int getDriverId() { return driverId; }
    public int getCityId() { return cityId; }
    public String getShippingDate() { return shippingDate; }
    public int getAdminId() { return adminId; }
    public  int getTotalWeight() { return totalWeight; }
}