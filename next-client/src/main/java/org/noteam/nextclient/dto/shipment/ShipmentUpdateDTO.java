package org.noteam.nextclient.dto.shipment;

import org.noteam.nextclient.models.DriverObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ShipmentUpdateDTO {
    private final int shipmentId;
    private final List<Integer> orderIds;
    private final int vehicleId;
    private final int driverId;
    private final int cityId;
    private final String shippingDate;
    private final int totalWeight;
   private final DriverObj driver;
    private ShipmentUpdateDTO(Builder builder) {
        this.shipmentId = builder.shipmentId;
        this.orderIds = Collections.unmodifiableList(new ArrayList<>(builder.orderIds));
        this.vehicleId = builder.vehicleId;
        this.driverId = builder.driverId;
        this.cityId = builder.cityId;
        this.shippingDate = builder.shippingDate;
        this.totalWeight = builder.totalWeight;
        this.driver=builder.driver;

    }

    public static class Builder {
        private int shipmentId;
        private List<Integer> orderIds = new ArrayList<>();
        private int vehicleId;
        private int driverId;
        private int cityId;
        private String shippingDate;
        private int totalWeight;
        private DriverObj driver;

        public Builder shipmentId(int shipmentId) {
            this.shipmentId = shipmentId;
            return this;
        }

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

        public Builder driver(DriverObj driver) {
            this.driver = driver;
            return this;
        }

        public Builder totalWeight(int totalWeight) {
            this.totalWeight = totalWeight;
            return this;
        }

        public ShipmentUpdateDTO build() {
            validate();
            return new ShipmentUpdateDTO(this);
        }


        private void validate() {
            if (shipmentId <= 0) throw new IllegalArgumentException("Shipment ID must be positive");
            if (vehicleId <= 0) throw new IllegalArgumentException("Vehicle ID must be positive");
            if (driverId <= 0) throw new IllegalArgumentException("Driver ID must be positive");
            if (cityId <= 0) throw new IllegalArgumentException("City ID must be positive");
            if (shippingDate == null || shippingDate.trim().isEmpty()) {
                throw new IllegalArgumentException("Shipping date is required");
            }
            if (totalWeight <= 0) throw new IllegalArgumentException("Total weight must be positive");
        }
    }

    // Getters
    public int getShipmentId() { return shipmentId; }
    public List<Integer> getOrderIds() { return orderIds; }
    public int getVehicleId() { return vehicleId; }
    public int getDriverId() { return driverId; }
    public int getCityId() { return cityId; }
    public String getShippingDate() { return shippingDate; }
    public int getTotalWeight() { return totalWeight; }

    public DriverObj getDriver() {
        return driver;
    }
}