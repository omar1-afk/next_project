package com.noteam.next.services;

import com.noteam.next.entities.Vehicle;
import com.noteam.next.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public Optional<Vehicle> getVehicleById(int id) {
        return repository.findById(id);
    }

    public Vehicle addVehicle(Vehicle vehicle) {

        if (vehicle.getWeightLimit() == null || vehicle.getWeightLimit() <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }

        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().isBlank()) {
            throw new IllegalArgumentException("License plate is required");
        }

        return repository.save(vehicle);
    }

    public boolean deleteVehicle(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
