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
            new SimpleObjectProperty<>(LocalDateTime.now());

    public enum VehicleType {
        VAN, TRUCK
    }

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
    public VehicleType getType() { return type.get(); }
    public int getWeightLimit() { return weightLimit.get(); }
    public String getLicensePlate() { return licensePlate.get(); }
    public boolean isAvailable() { return available.get(); }
    public LocalDateTime getCreatedAt() { return createdAt.get(); }
}
