package com.noteam.next.controllers;

import com.noteam.next.entities.Vehicle;
import com.noteam.next.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vehicle> getVehicles() { return service.getAllVehicles(); }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle saved = service.addVehicle(vehicle);
        return ResponseEntity.status(201).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable int id) {
        if (!service.deleteVehicle(id)) return ResponseEntity.status(404).body("Not found");
        return ResponseEntity.ok("Deleted");
    }
}
