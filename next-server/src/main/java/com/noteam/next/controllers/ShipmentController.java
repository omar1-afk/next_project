package com.noteam.next.controllers;

import com.noteam.next.entities.Orders;
import com.noteam.next.entities.Shipment;
import com.noteam.next.services.ShipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    private static final Logger logger = Logger.getLogger(ShipmentController.class.getName());
    //get
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        logger.info("Getting all shipments" );
        List<Shipment> shipments= shipmentService.getAllShipments();
        return ResponseEntity.status(HttpStatus.OK).body(shipments);
    }

    @GetMapping("/{shipment_id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int shipment_id) {
        logger.info("Getting a shipment  by id"+shipment_id );
        Optional<Shipment> shipment = shipmentService.getShipmentById(shipment_id);
        return shipment.map(
                value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @GetMapping("/driver/{driver_id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByDriverId(@PathVariable int driver_id) {
        logger.info("Getting all shipments  by driver: " + driver_id);
        List<Shipment> shipments = shipmentService.getAllShipmentsByDriverId(driver_id);
        return ResponseEntity.status(HttpStatus.OK).body(shipments);
    }
    @GetMapping("/admin/{admin_id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByAdminId(@PathVariable int admin_id) {
        logger.info("Getting all shipments  by admin: " + admin_id);
        List<Shipment> shipments = shipmentService.getAllShipmentsByAdminId(admin_id);
        return ResponseEntity.status(HttpStatus.OK).body(shipments);
    }

    @GetMapping("/vehicle/{vehicle_id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByVehicleId(@PathVariable int vehicle_id) {
        logger.info("Getting all shipments  by vehicle_id" + vehicle_id);
        List<Shipment> shipments = shipmentService.getAllShipmentsByVehicle_id(vehicle_id);
        return ResponseEntity.status(HttpStatus.OK).body(shipments);
    }

    @GetMapping("/complete/{is_complete}")
    public  ResponseEntity<List<Shipment>> getAllShipmentsByIs_complete(@PathVariable boolean isComplete) {
        logger.info("Getting all shipments  by IsComplete:" + isComplete);
        List<Shipment> shipments = shipmentService.getAllShipmentsByIsComplete(isComplete);
        return ResponseEntity.status(HttpStatus.OK).body(shipments);
    }

    @GetMapping("/{shipment_id}/orders")
    public ResponseEntity<List<Shipment>> getAllOrdersInShipment(@PathVariable int shipment_id) {
        logger.info("Getting all orders  by shipment_id" + shipment_id);
       Optional<Shipment > shipment= shipmentService.getShipmentById(shipment_id);
       if(shipment.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       else {
           List<Orders> ordersList = shipmentService.getAllOrdersInShipment(shipment.get());
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

       }

    }

    //post
    @PostMapping("/create")
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment,@RequestParam List<Integer> orders_id ) {
        logger.info("create a shipment will be shipped at"+ shipment.getShipping_date());
         /*
        admin authentication
         */
        shipmentService.createShipment(
                orders_id,
                shipment.getAdmin().getAdmin_id(),
                shipment.getVehicle().getVehicle_id(),     //change later to the actual name in vehicle rep
                shipment.getDriver().getDriver_id(),  //change later to the actual name in employee rep
                shipment.getTotal_wight(),
                shipment.getShipping_date()
        );
        return ResponseEntity.status(HttpStatus.OK).build();

    }
    //update
    @PutMapping("/update")
    public ResponseEntity<Shipment> updateShipment( @RequestBody Shipment shipment ,@RequestParam List<Integer> orders_id) {
        logger.info("update a shipment number" + shipment.getShipment_id());
         /*
        admin authentication
         */
        Optional<Shipment>  updatedShipment =Optional.ofNullable(shipmentService.updateShipmentById(
                orders_id,
                shipment.getAdmin().getAdmin_id(), //change later to the actual name in admin entity
                shipment.getShipment_id(),
                shipment.getVehicle().getVehicle_id(),     //change later to the actual name in vehicle entity
                shipment.getDriver().getDriver_id(),  //change later to the actual name in driver entity
                shipment.getTotal_wight(),
                shipment.getShipping_date()));
        return updatedShipment.map(
                value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PatchMapping("/{shipment_id}/complete")
    public ResponseEntity<Shipment>  markShipmentAsComplete (@PathVariable int shipment_id) {
        logger.info("manually mark a shipment"+ shipment_id+ "as complete ");
         /*
        admin authentication
         */
        Optional<Shipment> updatedShipment = Optional.ofNullable(shipmentService.markShipmentAsComplete(shipment_id));
        return updatedShipment.map(
                shipment -> ResponseEntity.status(HttpStatus.OK).body(shipment))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
    @PatchMapping("/{shipment_id}/driver/{driver_id}")
    public ResponseEntity<Shipment>  assignDriverToShipment (@PathVariable int shipment_id, @PathVariable int driver_id) {
        logger.info("Assign driver:" + driver_id +" to shipment "+ shipment_id );
         /*
        admin authentication
         */
        Optional<Shipment>  updatedShipment = Optional.ofNullable(shipmentService.assignDriverToShipment(shipment_id, driver_id));
        return updatedShipment.map(
                shipment -> ResponseEntity.status(HttpStatus.OK).body(shipment))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());

    }

    //delete
    @DeleteMapping("/{shipment_id} ")
    public ResponseEntity<Shipment> deleteShipmentById(@PathVariable int shipment_id) {
        logger.info("delete a shipment number" + shipment_id);
         /*
        admin authentication
         */
        if (!shipmentService.deleteShipmentById(shipment_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
