package com.noteam.next.controllers;
import com.noteam.next.entities.Order;
import com.noteam.next.entities.Shipment;
import com.noteam.next.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    private static final Logger logger = Logger.getLogger(ShipmentController.class.getName());
    public static class ShipmentRequest {
        private int shipment_id;
        private List<Integer> orderIds;
        private int admin_id;
        private int vehicle_id;
        private int driver_id;
        private double total_weight;
        private LocalDate shipping_date;
        private int city_id;


        // Getters and setters

        public int getShipmentId() {
            return shipment_id;
        }

        public List<Integer> getOrderIds() { return orderIds; }
        public void setOrderIds(List<Integer> orderIds) { this.orderIds = orderIds; }
        public int getAdminId() { return admin_id; }
        public void setAdminId(int adminId) { this.admin_id = adminId; }
        public int getVehicleId() { return vehicle_id; }
        public void setVehicleId(int vehicleId) { this.vehicle_id = vehicleId; }
        public int getDriverId() { return driver_id; }
        public void setDriverId(int driverId) { this.driver_id = driverId; }
        public double getTotalWeight() { return total_weight; }
        public void setTotalWeight(double totalWeight) { this.total_weight = totalWeight; }
        public LocalDate getShippingDate() { return shipping_date; }
        public void setShippingDate(LocalDate shippingDate) { this.shipping_date = shippingDate; }
        public int getCityId() { return city_id; }
        public void setCityId(int cityId) { this.city_id = cityId; }
    }
    //get
    @GetMapping
    
    public ResponseEntity<List<Shipment>> getAllShipments() {
        logger.info("Getting all shipments" );
        try {
            List<Shipment> shipments = shipmentService.getAllShipments();
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        }
        catch (Exception e){
            logger.severe("Error getting shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{shipment_id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int shipment_id) {
        logger.info("Getting a shipment  by id"+shipment_id );
        try {
            Optional<Shipment> shipment = shipmentService.getShipmentById(shipment_id);
            return shipment.map(
                            value -> ResponseEntity.status(HttpStatus.OK).body(value))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (Exception e){
            logger.severe("Error getting a shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/driver/{driver_id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByDriverId(@PathVariable int driver_id) {
        logger.info("Getting all shipments  by driver: " + driver_id);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByDriverId(driver_id);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        }
        catch (Exception e){
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/city/{city_id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByCityId(@PathVariable int city_id) {
        logger.info("Getting all shipments  by city: " + city_id);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByCityId(city_id);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        }
        catch (Exception e){
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/{admin_id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByAdminId(@PathVariable int admin_id) {
        logger.info("Getting all shipments  by admin: " + admin_id);
        try {


            List<Shipment> shipments = shipmentService.getAllShipmentsByAdminId(admin_id);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        }
        catch (Exception e){
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/vehicle/{vehicle_id}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByVehicleId(@PathVariable int vehicle_id) {
        logger.info("Getting all shipments  by vehicle_id" + vehicle_id);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByVehicleId(vehicle_id);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        }
        catch (Exception e){
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/complete/{isComplete}")
    public  ResponseEntity<List<Shipment>> getAllShipmentsByIs_complete(@PathVariable boolean isComplete) {
        logger.info("Getting all shipments  by IsComplete:" + isComplete);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByIsComplete(isComplete);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        }
        catch (Exception e){
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{shipment_id}/orders")
    public ResponseEntity<List<Order>> getAllOrdersInShipment(@PathVariable int shipment_id) {
        logger.info("Getting all orders  by shipment_id" + shipment_id);
        try {
            Optional<Shipment > shipment= shipmentService.getShipmentById(shipment_id);
            if(shipment.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
                List<Order> orderList = shipmentService.getAllOrdersInShipment(shipment.get());
                return ResponseEntity.status(HttpStatus.OK).body(orderList);

        }
        catch (Exception e){
            logger.severe("Error getting orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    //post
    @PostMapping
    public ResponseEntity<?> createShipment(@RequestBody ShipmentRequest shipmentRequest ) {
        logger.info("create a shipment ");

        try {
           Shipment createdShipment = shipmentService.createShipment(
                    shipmentRequest.getOrderIds(),
                    shipmentRequest.getAdminId(),
                    shipmentRequest.getVehicleId(),
                    shipmentRequest.getDriverId(),
                    shipmentRequest.getTotalWeight(),
                    shipmentRequest.getShippingDate(),
                   shipmentRequest.getCityId()

            );
            return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
          }
        catch (IllegalArgumentException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
          }
        catch (Exception e) {
          logger.severe("Error creating shipment: " + e.getMessage());
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating shipment: " + e.getCause().getMessage());
    }
}
    //update
    @PutMapping("/update/{shipment_id}")
    public ResponseEntity<?> updateShipment(@PathVariable int shipment_id,@RequestBody ShipmentRequest shipmentRequest ) {
        logger.info("update a shipment number" + shipment_id);

        try {
            Shipment updatedShipment = shipmentService.updateShipmentById(
                    shipmentRequest.getOrderIds(),
                    //shipmentRequest.getAdminId(),
                    shipment_id,
                    shipmentRequest.getVehicleId(),
                    shipmentRequest.getDriverId(),
                    shipmentRequest.getTotalWeight(),
                    shipmentRequest.getShippingDate(),
                    shipmentRequest.getCityId());
            return ResponseEntity.status(HttpStatus.OK).body(updatedShipment);
          }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           }
        catch (Exception e) {
            logger.severe("Error updating shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating shipment: " + e.getCause().getMessage());
        }
    }


    @PatchMapping("/{shipment_id}/complete")
    public ResponseEntity<?>  markShipmentAsComplete (@PathVariable int shipment_id) {
        logger.info("manually mark a shipment"+ shipment_id+ "as complete ");
         /*
        admin authentication
         */
        try{
        Shipment updatedShipment = shipmentService.markShipmentAsComplete(shipment_id);
            if (updatedShipment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Shipment not found with id: " + shipment_id);
            }
            return ResponseEntity.ok(updatedShipment);
    }
         catch (Exception e) {
        logger.severe("Error marking shipment: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}
    @PatchMapping("/{shipment_id}/driver/{driver_id}")
    public ResponseEntity<?>  assignDriverToShipment (@PathVariable int shipment_id, @PathVariable int driver_id) {
        logger.info("Assign driver:" + driver_id +" to shipment "+ shipment_id );
         /*
        admin authentication
         */
        try{
        Shipment  updatedShipment = shipmentService.assignDriverToShipment(shipment_id, driver_id);
            if (updatedShipment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Shipment not found with id: " + shipment_id);
            }
            return ResponseEntity.ok(updatedShipment);
        }
        catch (Exception e){
        logger.severe("Error assign driver to  shipment: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }
    //delete
    @DeleteMapping("/{shipment_id}")
    public ResponseEntity<?> deleteShipmentById(@PathVariable int shipment_id) {
        logger.info("delete a shipment number" + shipment_id);
         /*
        admin authentication
         */
        try{
        if (!shipmentService.deleteShipmentById(shipment_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("shipment not found");
        }
        return ResponseEntity.ok("Shipment deleted successfully");
    }
        catch (Exception e){
        logger.severe("Error deleting shipment: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }


}
