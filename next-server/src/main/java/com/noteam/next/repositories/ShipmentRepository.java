package com.noteam.next.repositories;
import com.noteam.next.entities.Admin;
import com.noteam.next.entities.Driver;
import com.noteam.next.entities.Shipment;
import com.noteam.next.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment ,Integer > {
    List<Shipment> findAllByDriver(Driver driver);
    List<Shipment> findAllByAdmin(Admin admin);
    List<Shipment> findAllByVehicle(Vehicle vehicle);
    List<Shipment> findAllByIsComplete(boolean isComplete);

}
