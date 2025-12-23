package com.noteam.next.services;

import com.noteam.next.entities.*;
import com.noteam.next.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.Collections.emptyList;

@Service
public class ShipmentService {

    private static final Logger logger = Logger.getLogger(ShipmentService.class.getName());
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private OrderService ordersService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CityRepository cityRepository;
    // get

    public List<Shipment> getAllShipments() {
        logger.info("Getting all shipments");
        try {
            List<Shipment> shipments = shipmentRepository.findAll();
            if (shipments.isEmpty()) {
                logger.info("Shipments not found");
                return emptyList();
            }
            return shipments;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return emptyList();
        }

    }

    public Optional<Shipment> getShipmentById(int shipmentId) {
        logger.info("Getting a shipment  by id" + shipmentId);
        try {
            Optional<Shipment> shipment = shipmentRepository.findById(shipmentId);
            if (shipment.isEmpty()) {
                logger.info("Shipment not found");
                return Optional.empty();
            }
            return shipment;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public List<Shipment> getAllShipmentsByDriverId(int driverId) {
        logger.info("Getting all shipments  by driver :" + driverId);
        try {
            Optional<Driver> driver = driverRepository.findById(driverId);
            return driver.map(value -> shipmentRepository.findAllByDriver(value)).orElse(emptyList());
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Shipment> getAllShipmentsByAdminId(int adminId) {
        logger.info("Getting all shipments  by admin :" + adminId);
        try {
            Optional<Admin> admin = adminRepository.findById(adminId);
            return admin.map(value -> shipmentRepository.findAllByAdmin(value)).orElse(emptyList());
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }

    }

    public List<Shipment> getAllShipmentsByVehicleId(int vehicleId) {
        logger.info("Getting all shipments  by vehicleId" + vehicleId);
        try {
            Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
            return vehicle.map(value -> shipmentRepository.findAllByVehicle(value)).orElse(emptyList());
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Shipment> getAllShipmentsByCityId(int cityId) {
        logger.info("Getting all shipments  by cityId" + cityId);
        try {
            Optional<City> city = cityRepository.findById(cityId);
            return city.map(value -> shipmentRepository.findAllByCity(value)).orElse(emptyList());
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Shipment> getAllShipmentsByIsComplete(boolean IsComplete) {
        logger.info("Getting all shipments  by Iscomplete" + IsComplete);
        try {
            List<Shipment> shipments = shipmentRepository.findAllByIsComplete(IsComplete);
            if (shipments == null) {
                logger.info("Shipments not found");
                return emptyList();
            }
            return shipments;
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Order> getAllOrdersInShipment(Shipment shipment) {
        logger.info("Getting all orders  by shipmentId" + shipment.getId());
        try {
            List<Order> orders = shipment.getOrderList();
            if (orders == null) {
                logger.info("Orders not found");
                return emptyList();
            }
            return orders;
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    // post
    @Transactional
    public Shipment createShipment(List<Integer> orders, int adminId, int vehicleId, int driverId,
            double totalWeight, LocalDate shippingDate, int cityId) {
        logger.info("admin:" + adminId + "creates a shipment for vehicle" + vehicleId + "driver" + driverId
                + "will be shipped at" + shippingDate + "totalWeight" + totalWeight);
        try {
            Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId); // name check (checked)
            /*
             * Vehicle vehicle =new Vehicle();
             * vehicle.setCreatedAt(LocalDateTime.now());
             * vehicle.setAvailable(true);
             * vehicle.setType(Vehicle.VehicleType.TRUCK);
             * vehicle.setUsed(false);
             * vehicle.setWeightLimit(50);
             * vehicle.setLicensePlate("");
             * Driver driver = new Driver();
             * driver.setAge(10);
             * driver.setEmail(".com");
             * driver.setPassword("ps");
             * driver.setImage("im");
             * driver.setIsbusy(false);
             * driver.setCreated_at(LocalDateTime.now());
             * driver.setName("driver");
             * driver.setSocial_security_number("1234");
             * driver.setUpdated_at(LocalDateTime.now());
             */
            Optional<Driver> driver = driverRepository.findById(driverId); // name check
            Optional<Admin> admin = adminRepository.findById(adminId);
            Optional<City> city = cityRepository.findById(cityId);

            if (vehicle.isEmpty()) {
                throw new IllegalArgumentException("Vehicle not found");
            }
            if (driver.isEmpty()) {
                throw new IllegalArgumentException("Driver not found");
            }
            if (admin.isEmpty()) {
                throw new IllegalArgumentException("Admin not found");
            }
            if (city.isEmpty()) {
                throw new IllegalArgumentException(" City  not found");
            }

            totalWeight = Math.ceil(totalWeight);
            // int weight=vehicle.getWeightLimit();
            int weight = vehicle.get().getWeightLimit();// name check (checked)
            if (totalWeight > weight) {
                throw new IllegalArgumentException("Total weight exceeds vehicle capacity");
            }
            Shipment shipment;
            shipment = new Shipment();
            shipment.setVehicle(vehicle.get());
            // shipment.setVehicle(vehicle);
            shipment.setAdmin(admin.get());
            // shipment.setDriver(driver);
            shipment.setDriver(driver.get());
            shipment.setCity(city.get());
            shipment.setIsComplete(totalWeight == weight);
            shipment.setTotalWeight((int) totalWeight);
            shipment.setShippingDate(shippingDate);
            shipment.setCreatedAt(LocalDateTime.now());
            Shipment createdShipment = shipmentRepository.save(shipment);
            if (orders != null && !orders.isEmpty()) {
                ordersService.attachShipmentByIds(orders, createdShipment.getId()); // name
            }
            return createdShipment;
        } catch (Exception e) {
            logger.severe("Error creating shipment: " + e.getMessage());
            throw new RuntimeException("Failed to create shipment: " + e.getMessage(), e);
        }

    }

    // update
    @Transactional
    public Shipment updateShipmentById(List<Integer> orders, int shipmentId, int vehicleId, int driverId,
            double totalWeight, LocalDate shippingDate, int cityId) {
        logger.info("update a shipment number" + shipmentId);
        try {

            Optional<Shipment> shipment = shipmentRepository.findById(shipmentId);
            if (shipment.isEmpty()) {
                throw new IllegalArgumentException("Shipment not found with id: " + shipmentId);

            }
            Shipment existingShipment = shipment.get();
            Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId); // name check (checked)
            Optional<Driver> driver = driverRepository.findById(driverId); // name check
            Optional<City> city = cityRepository.findById(cityId);

            // Optional<Admin> admin = adminRepository.findById(adminId); // it depends on
            // whether the admin here is the one who added the shipment or
            // the one who last confirmed a change cuz obviously it can not be both
            if (vehicle.isEmpty()) {
                throw new IllegalArgumentException("Vehicle not found");
            }

            if (driver.isEmpty()) {
                throw new IllegalArgumentException(" Driver not found");
            }
            if (city.isEmpty()) {
                throw new IllegalArgumentException(" City  not found");
            }
            totalWeight = Math.ceil(totalWeight);
            int weight = vehicle.get().getWeightLimit();// name check (checked)
            if (totalWeight > weight) {
                throw new IllegalArgumentException("Total weight exceeds vehicle capacity");
            }

            existingShipment.setVehicle(vehicle.get());
            // updatedShipment.setAdmin(admin.get());
            existingShipment.setDriver(driver.get());
            existingShipment.setCity(city.get());
            existingShipment.setIsComplete(totalWeight == weight);
            existingShipment.setTotalWeight((int) totalWeight);
            existingShipment.setShippingDate(shippingDate);
            existingShipment.setUpdatedAt(LocalDateTime.now());
            Shipment savedShipment = shipmentRepository.save(existingShipment);
            if (orders != null && !orders.isEmpty()) {
                ordersService.attachShipmentByIds(orders, savedShipment.getId());
            }
            return savedShipment;
        } catch (Exception e) {
            logger.severe("Error updating shipment: " + e.getMessage());
            throw new RuntimeException("Failed to update shipment: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Shipment markShipmentAsComplete(int shipmentId) {
        logger.info("manually mark a shipment" + shipmentId + "as complete");
        Optional<Shipment> shipment = shipmentRepository.findById(shipmentId);
        if (shipment.isEmpty()) {
            return null;
        } else {
            Shipment updateShipment = shipment.get();
            updateShipment.setIsComplete(true);
            updateShipment.setUpdatedAt(LocalDateTime.now());

            // again the admin issue

            return shipmentRepository.save(updateShipment);
        }

    }

    @Transactional
    public Shipment assignDriverToShipment(int shipmentId, int driverId) {
        logger.info("Assign employee" + driverId + " to shipment " + shipmentId);
        Optional<Shipment> shipment = shipmentRepository.findById(shipmentId);
        Optional<Driver> driver = driverRepository.findById(driverId); // name check
        /*
         * // check if employee is busy if GUI doesn't handle it
         *
         * ....
         */

        if (driver.isEmpty() || shipment.isEmpty()) {
            return null;
        } else {
            Shipment updateShipment = shipment.get();
            updateShipment.setDriver(driver.get());
            updateShipment.setUpdatedAt(LocalDateTime.now());
            // again the admin issue
            return shipmentRepository.save(updateShipment);
        }

    }

    // delete
    @Transactional
    public boolean deleteShipmentById(int shipmentId) {
        logger.info("create a shipment number" + shipmentId);
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(shipmentId);
        if (shipmentOptional.isEmpty()) {
            return false;
        } else {
            try {
                Shipment shipment = shipmentOptional.get();
                if (shipment.getOrderList() != null && !shipment.getOrderList().isEmpty()) {
                    List<Integer> orderIds = new ArrayList<>();
                    for (Order order : shipment.getOrderList()) {
                        orderIds.add(order.getId());
                    }
                    ordersService.deattachShipmentByIds(orderIds);
                }
                shipment.setDriver(null);
                shipment.setVehicle(null);
                shipment.setAdmin(null);
                shipment.setOrderList(null);
                shipmentRepository.save(shipment);
                shipmentRepository.delete(shipment);
                return true;
            } catch (Exception e) {
                logger.severe("Error deleting shipment: " + e.getMessage());
                throw new RuntimeException("Failed to delete shipment: " + e.getMessage());
            }
        }

    }

}
