package org.noteam.nextclient.dto;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Vehicle {

    private final IntegerProperty vehicleId = new SimpleIntegerProperty();
    private final ObjectProperty<VehicleType> type = new SimpleObjectProperty<>();
    private final IntegerProperty weightLimit = new SimpleIntegerProperty();
    private final StringProperty licensePlate = new SimpleStringProperty();
    private final BooleanProperty available = new SimpleBooleanProperty(true);


    private final ObjectProperty<LocalDateTime> createdAt =
            new SimpleObjectProperty<>();

    public enum VehicleType { VAN, TRUCK }

    public Vehicle() {}

    public Vehicle(int id, VehicleType type, int weightLimit, String licensePlate) {
        this.vehicleId.set(id);
        this.type.set(type);
        this.weightLimit.set(weightLimit);
        this.licensePlate.set(licensePlate);
    }

    public IntegerProperty vehicleIdProperty() { return vehicleId; }
    public ObjectProperty<VehicleType> typeProperty() { return type; }
    public IntegerProperty weightLimitProperty() { return weightLimit; }
    public StringProperty licensePlateProperty() { return licensePlate; }
    public BooleanProperty availableProperty() { return available; }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    public int getVehicleId() { return vehicleId.get(); }
    public void setVehicleId(int vehicleId) {
        this.vehicleId.set(vehicleId);
    }

    public VehicleType getType() { return type.get(); }
    public void setType(VehicleType type) {
        this.type.set(type);
    }

    public int getWeightLimit() { return weightLimit.get(); }
    public void setWeightLimit(int weightLimit) {
        this.weightLimit.set(weightLimit);
    }

    public String getLicensePlate() { return licensePlate.get(); }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate.set(licensePlate);
    }

    public boolean isAvailable() { return available.get(); }
    public void setAvailable(boolean available) {
        this.available.set(available);
    }
    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

}