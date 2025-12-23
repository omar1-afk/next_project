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
        private int shipmentId;
        private List<Integer> orderIds;
        private int adminId;
        private int vehicleId;
        private int driverId;
        private double totalWeight;
        private LocalDate shippingDate;
        private int cityId;

        // Getters and setters

        public int getShipmentId() {
            return shipmentId;
        }

        public List<Integer> getOrderIds() {
            return orderIds;
        }

        public void setOrderIds(List<Integer> orderIds) {
            this.orderIds = orderIds;
        }

        public int getAdminId() {
            return adminId;
        }

        public void setAdminId(int adminId) {
            this.adminId = adminId;
        }

        public int getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
        }

        public int getDriverId() {
            return driverId;
        }

        public void setDriverId(int driverId) {
            this.driverId = driverId;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(double totalWeight) {
            this.totalWeight = totalWeight;
        }

        public LocalDate getShippingDate() {
            return shippingDate;
        }

        public void setShippingDate(LocalDate shippingDate) {
            this.shippingDate = shippingDate;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }
    }

    // get
    @GetMapping

    public ResponseEntity<List<Shipment>> getAllShipments() {
        logger.info("Getting all shipments");
        try {
            List<Shipment> shipments = shipmentService.getAllShipments();
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        } catch (Exception e) {
            logger.severe("Error getting shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int shipmentId) {
        logger.info("Getting a shipment  by id" + shipmentId);
        try {
            Optional<Shipment> shipment = shipmentService.getShipmentById(shipmentId);
            return shipment.map(
                    value -> ResponseEntity.status(HttpStatus.OK).body(value))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            logger.severe("Error getting a shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByDriverId(@PathVariable int driverId) {
        logger.info("Getting all shipments  by driver: " + driverId);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByDriverId(driverId);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        } catch (Exception e) {
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByCityId(@PathVariable int cityId) {
        logger.info("Getting all shipments  by city: " + cityId);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByCityId(cityId);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        } catch (Exception e) {
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByAdminId(@PathVariable int adminId) {
        logger.info("Getting all shipments  by admin: " + adminId);
        try {

            List<Shipment> shipments = shipmentService.getAllShipmentsByAdminId(adminId);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        } catch (Exception e) {
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByVehicleId(@PathVariable int vehicleId) {
        logger.info("Getting all shipments  by vehicleId" + vehicleId);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByVehicleId(vehicleId);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        } catch (Exception e) {
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/complete/{isComplete}")
    public ResponseEntity<List<Shipment>> getAllShipmentsByIsComplete(@PathVariable boolean isComplete) {
        logger.info("Getting all shipments  by IsComplete:" + isComplete);
        try {
            List<Shipment> shipments = shipmentService.getAllShipmentsByIsComplete(isComplete);
            if (shipments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(shipments);
        } catch (Exception e) {
            logger.severe("Error getting all shipments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{shipmentId}/orders")
    public ResponseEntity<List<Order>> getAllOrdersInShipment(@PathVariable int shipmentId) {
        logger.info("Getting all orders  by shipmentId" + shipmentId);
        try {
            Optional<Shipment> shipment = shipmentService.getShipmentById(shipmentId);
            if (shipment.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            List<Order> orderList = shipmentService.getAllOrdersInShipment(shipment.get());
            return ResponseEntity.status(HttpStatus.OK).body(orderList);

        } catch (Exception e) {
            logger.severe("Error getting orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // post
    @PostMapping
    public ResponseEntity<?> createShipment(@RequestBody ShipmentRequest shipmentRequest) {
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.severe("Error creating shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating shipment: " + e.getCause().getMessage());
        }
    }

    // update
    @PutMapping("/update/{shipmentId}")
    public ResponseEntity<?> updateShipment(@PathVariable int shipmentId,
            @RequestBody ShipmentRequest shipmentRequest) {
        logger.info("update a shipment number" + shipmentId);

        try {
            Shipment updatedShipment = shipmentService.updateShipmentById(
                    shipmentRequest.getOrderIds(),
                    // shipmentRequest.getAdminId(),
                    shipmentId,
                    shipmentRequest.getVehicleId(),
                    shipmentRequest.getDriverId(),
                    shipmentRequest.getTotalWeight(),
                    shipmentRequest.getShippingDate(),
                    shipmentRequest.getCityId());
            return ResponseEntity.status(HttpStatus.OK).body(updatedShipment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.severe("Error updating shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating shipment: " + e.getCause().getMessage());
        }
    }

    @PatchMapping("/{shipmentId}/complete")
    public ResponseEntity<?> markShipmentAsComplete(@PathVariable int shipmentId) {
        logger.info("manually mark a shipment" + shipmentId + "as complete ");
        /*
         * admin authentication
         */
        try {
            Shipment updatedShipment = shipmentService.markShipmentAsComplete(shipmentId);
            if (updatedShipment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Shipment not found with id: " + shipmentId);
            }
            return ResponseEntity.ok(updatedShipment);
        } catch (Exception e) {
            logger.severe("Error marking shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{shipmentId}/driver/{driverId}")
    public ResponseEntity<?> assignDriverToShipment(@PathVariable int shipmentId, @PathVariable int driverId) {
        logger.info("Assign driver:" + driverId + " to shipment " + shipmentId);
        /*
         * admin authentication
         */
        try {
            Shipment updatedShipment = shipmentService.assignDriverToShipment(shipmentId, driverId);
            if (updatedShipment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Shipment not found with id: " + shipmentId);
            }
            return ResponseEntity.ok(updatedShipment);
        } catch (Exception e) {
            logger.severe("Error assign driver to  shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // delete
    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<?> deleteShipmentById(@PathVariable int shipmentId) {
        logger.info("delete a shipment number" + shipmentId);
        /*
         * admin authentication
         */
        try {
            if (!shipmentService.deleteShipmentById(shipmentId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("shipment not found");
            }
            return ResponseEntity.ok("Shipment deleted successfully");
        } catch (Exception e) {
            logger.severe("Error deleting shipment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
