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
    //get

    public List<Shipment> getAllShipments() {
         logger.info("Getting all shipments");
         try {
           List<Shipment>shipments =shipmentRepository.findAll();
           if(shipments.isEmpty()) {
               logger.info("Shipments not found");
               return emptyList();
           }
           return shipments;
         }
         catch(Exception e){
             logger.log(Level.SEVERE,e.getMessage(),e);
             return emptyList();
         }


     }
    public Optional<Shipment> getShipmentById(int shipment_id) {
        logger.info("Getting a shipment  by id"+shipment_id );
        try {
            Optional<Shipment> shipment = shipmentRepository.findById(shipment_id);
            if(shipment.isEmpty()){
                logger.info("Shipment not found");
                return Optional.empty();
            }
            return shipment;
        }
        catch(Exception e){
            logger.log(Level.SEVERE,e.getMessage(),e);
            return Optional.empty();
        }
    }
    public List<Shipment> getAllShipmentsByDriverId(int driver_id) {
        logger.info("Getting all shipments  by driver :" + driver_id);
        try {
            Optional<Driver> driver = driverRepository.findById(driver_id);
            return driver.map(value -> shipmentRepository.findAllByDriver(value)).orElse(emptyList());
        }
        catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    public List<Shipment> getAllShipmentsByAdminId(int admin_id) {
        logger.info("Getting all shipments  by admin :"+ admin_id);
        try {
            Optional<Admin> admin = adminRepository.findById(admin_id);
            return admin.map(value->shipmentRepository.findAllByAdmin(value)).orElse(emptyList());
        }
       catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
       }

    }
    public List<Shipment> getAllShipmentsByVehicle_id(int vehicle_id) {
        logger.info("Getting all shipments  by vehicle_id" + vehicle_id);
        try {
            Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id);
            return vehicle.map(value->shipmentRepository.findAllByVehicle(value)).orElse(emptyList());
        }
        catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Shipment> getAllShipmentsByCityId(int city_id) {
        logger.info("Getting all shipments  by city_id" + city_id);
        try {
            Optional<City> city = cityRepository.findById(city_id);
            return city.map(value->shipmentRepository.findAllByCity(value)).orElse(emptyList());
        }
        catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Shipment> getAllShipmentsByIsComplete( boolean isComplete ) {
        logger.info("Getting all shipments  by Is_complete" + isComplete);
        try {
          List<Shipment> shipments=  shipmentRepository.findAllByIsComplete(isComplete);
          if(shipments==null){
              logger.info("Shipments not found");
              return emptyList();
          }
          return shipments;
        }
        catch(Exception e){
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    public List<Order> getAllOrdersInShipment(Shipment shipment) {
        logger.info("Getting all orders  by shipment_id" +shipment.getShipment_id() );
        try {
              List<Order> orders = shipment.getOrdersList();
              if (orders == null) {
                  logger.info("Orders not found");
                  return emptyList();
              }
              return orders;
          }
        catch(Exception e){
            logger.severe("Error: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    //post
    @Transactional
    public Shipment createShipment(List<Integer> orders, int admin_id, int vehicle_id, int driver_id, double total_weight, LocalDate shipping_date , int city_id) {
        logger.info("admin:" +admin_id+"creates a shipment for vehicle" +vehicle_id + "driver"+driver_id +"will be shipped at"+shipping_date +"total_wight"+total_weight);
try{
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id); // name check (checked)
    /*
        Vehicle vehicle =new Vehicle();
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setAvailable(true);
        vehicle.setType(Vehicle.VehicleType.TRUCK);
        vehicle.setUsed(false);
        vehicle.setWeightLimit(50);
        vehicle.setLicensePlate("");
        Driver driver = new Driver();
        driver.setAge(10);
        driver.setEmail(".com");
        driver.setPassword("ps");
        driver.setImage("im");
    driver.setIsbusy(false);
    driver.setCreated_at(LocalDateTime.now());
    driver.setName("driver");
    driver.setSocial_security_number("1234");
    driver.setUpdated_at(LocalDateTime.now());
*/
        Optional<Driver> driver= driverRepository.findById(driver_id); // name check
        Optional<Admin> admin = adminRepository.findById(admin_id);
        Optional<City> city = cityRepository.findById(city_id);

       if (vehicle.isEmpty() )
        {throw new IllegalArgumentException("Vehicle not found");}
        if(driver.isEmpty()){throw new IllegalArgumentException("Driver not found");}
        if(admin.isEmpty()){throw new IllegalArgumentException("Admin not found");}
        if( city.isEmpty()){ throw new IllegalArgumentException(" City  not found");}



            total_weight = Math.ceil(total_weight);
       // int weight=vehicle.getWeightLimit();
        int weight = vehicle.get().getWeightLimit();// name check (checked)
             if (total_weight > weight) {
              throw new IllegalArgumentException("Total weight exceeds vehicle capacity");
             }
                Shipment shipment;
                shipment = new Shipment();
                shipment.setVehicle(vehicle.get());
                // shipment.setVehicle(vehicle);
                shipment.setAdmin(admin.get());
                //shipment.setDriver(driver);
                shipment.setDriver(driver.get());
                shipment.setCity(city.get());
                shipment.setIsComplete(total_weight == weight );
                shipment.setTotal_weight((int) total_weight);
                shipment.setShipping_date(shipping_date);
                shipment.setCreated_at(LocalDateTime.now());
                Shipment createdShipment = shipmentRepository.save(shipment);
                if (orders != null && !orders.isEmpty()) {
                    ordersService.attachShipmentByIds(orders, createdShipment); // name
                }
             return createdShipment;
        } catch (Exception e) {
         logger.severe("Error creating shipment: " + e.getMessage());
          throw new RuntimeException("Failed to create shipment: " + e.getMessage(), e);
        }

    }
    //update
    @Transactional
    public Shipment updateShipmentById(List<Integer> orders, int shipment_id, int vehicle_id, int driver_id, double total_weight, LocalDate shipping_date, int city_id) {
        logger.info("update a shipment number" + shipment_id);
        try{

        Optional<Shipment> shipment = shipmentRepository.findById(shipment_id);
        if (shipment.isEmpty()) {
            throw new IllegalArgumentException("Shipment not found with id: " + shipment_id);

        }
        Shipment existingShipment = shipment.get();
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id);     // name check (checked)
        Optional<Driver> driver = driverRepository.findById(driver_id); // name check
        Optional<City> city= cityRepository.findById(city_id);

       // Optional<Admin> admin = adminRepository.findById(admin_id); // it depends on whether the admin here is the one who added the shipment or
                                                                     // the one who last confirmed a change cuz obviously it can not be both
        if (vehicle.isEmpty() ||driver.isEmpty()||city.isEmpty()){
            throw new IllegalArgumentException("Vehicle, Driver, City or shipment not found");
        }
        total_weight = Math.ceil(total_weight);
        int weight = vehicle.get().getWeightLimit();// name check (checked)
        if (total_weight > weight) {
            throw new IllegalArgumentException("Total weight exceeds vehicle capacity");
        }

        existingShipment.setVehicle(vehicle.get());
        //updatedShipment.setAdmin(admin.get());
        existingShipment.setDriver(driver.get());
        existingShipment.setCity(city.get());
        existingShipment.setIsComplete(total_weight == weight );
        existingShipment.setTotal_weight((int) total_weight);
        existingShipment.setShipping_date(shipping_date);
        existingShipment.setUpdated_at(LocalDateTime.now());
        Shipment savedShipment = shipmentRepository.save(existingShipment);
        if (orders != null && !orders.isEmpty()) {
            ordersService.attachShipmentByIds(orders, savedShipment);
        }
        return savedShipment;
       } catch (Exception e) {
        logger.severe("Error updating shipment: " + e.getMessage());
        throw new RuntimeException("Failed to update shipment: " + e.getMessage(), e);
    }
    }
    @Transactional
    public Shipment markShipmentAsComplete(int shipment_id ) {
        logger.info("manually mark a shipment"+ shipment_id+ "as complete" );
        Optional<Shipment> shipment = shipmentRepository.findById(shipment_id);
        if (shipment.isEmpty()) {
            return null;
        }
        else {
            Shipment updateShipment = shipment.get();
            updateShipment.setIsComplete(true);
            updateShipment.setUpdated_at(LocalDateTime.now());

            // again the admin issue

            return shipmentRepository.save(updateShipment);
        }

    }
    @Transactional
    public Shipment assignDriverToShipment(int shipment_id, int driver_id) {
        logger.info("Assign employee" + driver_id +" to shipment "+ shipment_id );
        Optional<Shipment> shipment = shipmentRepository.findById(shipment_id);
        Optional<Driver> driver = driverRepository.findById(driver_id);  // name check
        /*
        // check if employee is busy if GUI doesn't handle it

       ....
        */

        if ( driver.isEmpty() || shipment.isEmpty()) {
            return null;
        }
        else {
            Shipment updateShipment = shipment.get();
            updateShipment.setDriver(driver.get());
            updateShipment.setUpdated_at(LocalDateTime.now());
            // again the admin issue
            return shipmentRepository.save(updateShipment);
        }


    }
    //delete
    @Transactional
    public boolean deleteShipmentById(int shipment_id) {
        logger.info("create a shipment number" + shipment_id);
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(shipment_id);
        if (shipmentOptional.isEmpty()) {
            return false;
        }
        else {
            try{
               Shipment shipment = shipmentOptional.get();
                if (shipment.getOrdersList() != null && !shipment.getOrdersList().isEmpty()) {
              List<Integer> orderIds = new ArrayList<>() ;
                for(Order order :shipment.getOrdersList() ) {
                    orderIds.add(order.getId());
                }
               ordersService.deattachShipmentByIds(orderIds);
            }
            shipment.setDriver(null);
            shipment.setVehicle(null);
            shipment.setAdmin(null);
            shipment.setOrdersList(null);
            shipmentRepository.save(shipment);
            shipmentRepository.delete(shipment);
            return true;
        }
            catch(Exception e) {
                logger.severe("Error deleting shipment: " + e.getMessage());
                throw new RuntimeException("Failed to delete shipment: " + e.getMessage());
            }
        }

    }






}
