package com.noteam.next.repositories;

import com.noteam.next.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {
    List<VehicleEntity> findByAvailable(boolean available);
}
