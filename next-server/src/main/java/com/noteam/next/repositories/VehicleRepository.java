package com.noteam.next.repositories;

import com.noteam.next.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByIsAvailable(boolean isAvailable);
}
