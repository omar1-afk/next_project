package com.noteam.next.services;

import com.noteam.next.entities.Driver;
import com.noteam.next.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class DriverService {
    private static final Logger logger = Logger.getLogger(DriverService.class.getName());

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private AuthService authService;

    // get
    public Optional<Driver> getDriverById(int driver_id) {
        logger.info("Getting the Driver by id: " + driver_id);
        return driverRepository.findById(driver_id);
    }

    public Optional<Driver> getDriverByEmail(String email) {
        logger.info("Getting the Driver by email: " + email);
        return driverRepository.findByEmail(email);
    }

    public List<Driver> getAllDrivers(String sortBy, String sortDir) {
        logger.info("Driver service: getting all Drivers sorted by " + sortBy + ",(" + sortDir + ")");
        return driverRepository.findAll();
    }

    public Page<Driver> getDdriversByPage(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        logger.info("Driver service: getting Drivers by page: " + page + " with size " + size + " sorted by " + sortBy
                + ",(" + sortDir + ")");
        return driverRepository.findAll(pageable);
    }

    public List<Driver> findAll() {
        logger.info("Getting all drivers");
        return driverRepository.findAll();
    }

    // post
    public Driver createdriver(String name, int age, String image, String social_security_number, String email,
            String password, Boolean isbusy) {
        logger.info("creating a new driver");
        Driver driver = new Driver();
        driver.setName(name);
        driver.setAge(age);
        driver.setImage(image);
        driver.setSocialSecurityNumber(social_security_number);
        driver.setEmail(email);
        driver.setPassword(authService.hashPassword(password));
        driver.setIsBusy(false);
        driver.setCreatedAt(LocalDateTime.now());
        driver.setUpdatedAt(LocalDateTime.now());
        return driverRepository.save(driver);
    }

    public int updatedriver(int id, Driver driver) {
        Optional<Driver> driverOptional = getDriverById(id);
        if (driverOptional.isEmpty()) {
            logger.info("Driver service: The driver with id: " + id + " is not found!");
            return 0;
        } else {
            Driver newDriver = driverOptional.get();
            logger.info("updating driver by id : " + id);
            newDriver.setName(driver.getName());
            newDriver.setAge(driver.getAge());
            newDriver.setImage(driver.getImage());
            newDriver.setSocialSecurityNumber(driver.getSocialSecurityNumber());
            newDriver.setEmail(driver.getEmail());
            newDriver.setPassword(authService.hashPassword(driver.getPassword()));
            newDriver.setIsBusy(driver.getIsBusy());
            newDriver.setUpdatedAt(LocalDateTime.now());
            driverRepository.save(newDriver);
            return 1;
        }
    }

    // delete
    public boolean deleteDriverById(int driver_id) {
        Optional<Driver> driverOptional = getDriverById(driver_id);
        if (driverOptional.isEmpty()) {
            return false;
        } else {
            driverRepository.deleteById(driver_id);
            return true;
        }
    }

}
