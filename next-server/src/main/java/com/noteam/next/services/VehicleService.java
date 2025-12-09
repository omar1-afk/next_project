package com.noteam.next.services;


import com.noteam.next.entities.VehicleEntity;
import com.noteam.next.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public List<VehicleEntity> getAllVehicles() {
        return repository.findAll();
    }

    public Optional<VehicleEntity> getVehicleById(int id) {
        return repository.findById(id);
    }

    public VehicleEntity addVehicle(VehicleEntity vehicle) {
        return repository.save(vehicle);
    }

    public VehicleEntity updateVehiclePartial(VehicleEntity existing, VehicleEntity updates) {

        if (updates.getType() != null) {
            existing.setType(updates.getType());
        }

        if (updates.getLicensePlate() != null) {
            existing.setLicensePlate(updates.getLicensePlate());
        }

        if (updates.getWeightLimit() != null) {
            existing.setWeightLimit(updates.getWeightLimit());
        }

        existing.setAvailable(updates.isAvailable());
        existing.setUsed(updates.isUsed());

        return repository.save(existing);
    }

    public boolean deleteVehicle(int id) {
        if (!repository.existsById(id)) return false;

        repository.deleteById(id);
        return true;
    }

    public List<VehicleEntity> getAvailableVehicles() {
        return repository.findByAvailable(true);
    }
}

