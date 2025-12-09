package com.noteam.next.repositories;

import com.noteam.next.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment,Integer> {
}
