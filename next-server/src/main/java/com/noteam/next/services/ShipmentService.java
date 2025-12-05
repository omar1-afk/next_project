package com.noteam.next.services;
import com.noteam.next.entities.*;
import com.noteam.next.repositories.DriverRepository;
import com.noteam.next.repositories.ShipmentRepository;
import com.noteam.next.repositories.VehicleRepository;
import com.noteam.next.repositories.AdminRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
    private OrdersService ordersService;
    @Autowired
    private AdminRepository adminRepository;
    //get

    public List<Shipment> getAllShipments() {
         logger.info("Getting all shipments" );
         return shipmentRepository.findAll();

     }
    public Optional<Shipment> getShipmentById(int shipment_id) {
        logger.info("Getting a shipment  by id"+shipment_id );
        return shipmentRepository.findById(shipment_id);
    }
    public List<Shipment> getAllShipmentsByDriverId(int driver_id) {
        logger.info("Getting all shipments  by driver :" + driver_id);
        Optional<Driver> driver = driverRepository.findById(driver_id);
        return driver.map(value -> shipmentRepository.findAllByDriver(value)).orElse(null);

    }
    public List<Shipment> getAllShipmentsByAdminId(int admin_id) {
        logger.info("Getting all shipments  by admin :"+ admin_id);
        Optional<Admin> admin = adminRepository.findById(admin_id);
        return admin.map(value->shipmentRepository.findAllByAdmin(value)).orElse(null);

    }
    public List<Shipment> getAllShipmentsByVehicle_id(int vehicle_id) {
        logger.info("Getting all shipments  by vehicle_id" + vehicle_id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id);
        return vehicle.map(value->shipmentRepository.findAllByVehicle(value)).orElse(null);
    }
    public List<Shipment> getAllShipmentsByIsComplete( boolean isComplete ) {
        logger.info("Getting all shipments  by Is_complete" + isComplete);
        return shipmentRepository.findAllByIsComplete(isComplete);
    }
    public List<Orders> getAllOrdersInShipment(Shipment shipment) {
        logger.info("Getting all orders  by shipment_id" +shipment.getShipment_id());
        return shipment.getOrdersList();
    }
    //post
    public Shipment createShipment(List<Integer> orders,int admin_id,int vehicle_id, int driver_id, double total_wight, Date shipping_date) {
        logger.info("admin:" +admin_id+"creates a shipment for vehicle" +vehicle_id + "driver"+driver_id +"will be shipped at"+shipping_date +"total_wight"+total_wight);

        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id); // name check
        Optional<Driver> driver= driverRepository.findById(driver_id); // name check
        Optional<Admin> admin = adminRepository.findById(admin_id);

        if(vehicle.isEmpty()||driver.isEmpty()){return null;}
        else {
            total_wight = Math.ceil(total_wight);
            int wight = vehicle.get().getWight();// name check
            if (total_wight < wight ) {
                Shipment shipment;
                shipment = new Shipment();
                shipment.setVehicle(vehicle.get());
                shipment.setAdmin(admin.get());
                shipment.setDriver(driver.get());
                shipment.setIsComplete(false);
                shipment.setTotal_wight((int) total_wight);
                shipment.setShipping_date(shipping_date);
                shipment.setCreated_at(LocalDateTime.now());
                ordersService.AssignOrDeleteShipment(orders,shipment.getShipment_id());   // name check
                return shipmentRepository.save(shipment);
            }
            else if(total_wight == wight) {
                Shipment shipment;
                shipment = new Shipment();
                shipment.setVehicle(vehicle.get());
                shipment.setDriver(driver.get());
                shipment.setAdmin(admin.get());
                shipment.setIsComplete(true);
                shipment.setShipping_date(shipping_date);
                shipment.setTotal_wight((int) total_wight);
                shipment.setCreated_at(LocalDateTime.now());
                ordersService.AssignOrDeleteShipment(orders,shipment.getShipment_id());   // name check
                return shipmentRepository.save(shipment);
            }

            else  {
                return null;
            }
        }

    }
    //update
    public Shipment updateShipmentById(List<Integer> orders,int admin_id,int shipment_id,int vehicle_id, int driver_id, double total_wight, Date shipping_date) {
        logger.info("update a shipment number" + shipment_id);
        Optional<Shipment> shipment = shipmentRepository.findById(shipment_id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicle_id);     // name check
        Optional<Driver> driver = driverRepository.findById(driver_id); // name check

       // Optional<Admin> admin = adminRepository.findById(admin_id); // it depends on whether the admin here is the one who added the shipment or
                                                                     // the one who last confirmed a change cuz obviously it can not be both
        if (vehicle.isEmpty() || driver.isEmpty() || shipment.isEmpty()) {
            return null;
        } else {
            total_wight = Math.ceil(total_wight);
            int wight = vehicle.get().getWight();   // name check
            if (total_wight < wight) {
                Shipment updateShipment = shipment.get();
                updateShipment.setVehicle(vehicle.get());
                updateShipment.setDriver(driver.get());
                //updateShipment.setAdmin(admin.get());
                updateShipment.setTotal_wight((int) total_wight);
                updateShipment.setShipping_date(shipping_date);
                updateShipment.setIsComplete(false);
                updateShipment.setUpdated_at(LocalDateTime.now());
                ordersService.AssignOrDeleteShipment(orders,updateShipment.getShipment_id());   // name check
                return shipmentRepository.save(updateShipment);
            } else if (total_wight == wight) {
                Shipment updateShipment = shipment.get();
                updateShipment.setVehicle(vehicle.get());
                updateShipment.setDriver(driver.get());
                //updateShipment.setAdmin(admin.get());
                updateShipment.setTotal_wight((int) total_wight);
                updateShipment.setShipping_date(shipping_date);
                updateShipment.setIsComplete(true);
                updateShipment.setUpdated_at(LocalDateTime.now());
                ordersService.AssignOrDeleteShipment(orders,updateShipment.getShipment_id());   // name check
                return shipmentRepository.save(updateShipment);
            } else {
                return null;
            }
        }


    }
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
    public boolean deleteShipmentById(int shipment_id) {
        logger.info("create a shipment number" + shipment_id);
        Optional<Shipment> shipment = shipmentRepository.findById(shipment_id);
        if (shipment.isEmpty()) {
            return false;
        }
        else {
            shipment.get().setDriver(null);
            shipment.get().setVehicle(null);
            shipment.get().setAdmin(null);
            shipmentRepository.delete(shipment.get());
            return true;
        }

    }






}
