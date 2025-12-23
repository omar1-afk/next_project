package com.noteam.next.controllers;

import com.noteam.next.entities.Vehicle;
import com.noteam.next.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    private static final Logger logger = Logger.getLogger(VehicleController.class.getName());

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<?> getVehicles(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Boolean used) {
        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicles();

            if (type != null) {
                try {
                    Vehicle.VehicleType vt = Vehicle.VehicleType.valueOf(type.toUpperCase());
                    vehicles = vehicles.stream()
                            .filter(v -> v.getType() == vt)
                            .toList();
                } catch (IllegalArgumentException ex) {
                    return ResponseEntity.badRequest().body("Invalid type: VAN or TRUCK only");
                }
            }

            if (available != null) {
                vehicles = vehicles.stream()
                        .filter(v -> v.getIsAvailable() == available)
                        .toList();
            }

            if (used != null) {
                vehicles = vehicles.stream()
                        .filter(v -> v.getIsUsed() == used)
                        .toList();
            }

            return ResponseEntity.ok(vehicles);

        } catch (Exception ex) {
            logger.severe("Error: " + ex.getMessage());
            return ResponseEntity.internalServerError().body("Unexpected error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable int id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);

        return vehicle.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vehicle not found"));
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody Vehicle vehicle) {
        if (vehicle.getType() == null)
            return ResponseEntity.badRequest().body("Vehicle type is required");

        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().isBlank())
            return ResponseEntity.badRequest().body("License plate is required");

        Vehicle saved = vehicleService.addVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(
            @PathVariable int id,
            @RequestBody Vehicle updates) {
        Optional<Vehicle> existingOpt = vehicleService.getVehicleById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vehicle not found");
        }

        Vehicle updated = vehicleService.updateVehiclePartial(existingOpt.get(), updates);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable int id) {
        if (!vehicleService.deleteVehicle(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vehicle not found");
        }

        return ResponseEntity.ok("Vehicle deleted successfully");
    }
}
