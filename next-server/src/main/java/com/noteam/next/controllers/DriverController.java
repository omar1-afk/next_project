package com.noteam.next.controllers;

import com.noteam.next.dto.DriverResponse;
import com.noteam.next.entities.Driver;
import com.noteam.next.services.AuthService;
import com.noteam.next.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {
    private static final Logger logger = Logger.getLogger(DriverController.class.getName());
    @Autowired
    private DriverService driverService;
    @Autowired
    private AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@RequestParam int driver_id) {
        logger.info("Getting driver by id: " + driver_id);
        Optional<Driver> driverOptional = driverService.getDriverById(driver_id);
        if (driverOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(driverOptional.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Driver> getDriverByEmail(@RequestParam String email) {
        logger.info("Getting driver by email: " + email);
        Optional<Driver> driverOptional = driverService.getDriverByEmail(email);

        if (driverOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(driverOptional.get());
    }

    @GetMapping("/all")
    ResponseEntity<List<Driver>> getAllDrivers(
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        List<Driver> driverList = driverService.getAllDrivers(sortBy, sortDir);
        if (driverList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Driver controller: getting all Drivers sorted by " + sortBy + ",(" + sortDir + ")");
            return ResponseEntity.ok(driverList);
        }
    }

    @GetMapping
    ResponseEntity<Page<Driver>> getDriversByPage(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        Page<Driver> driverPage = driverService.getDdriversByPage(page, size, sortBy, sortDir);
        if (driverPage.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Driver controller: getting Drivers by page: " + page + " with size " + size + " sorted by "
                    + sortBy + ",(" + sortDir + ")");
            return ResponseEntity.ok(driverPage);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Driver> loginDriver(@RequestParam String email, @RequestParam String password) {
        // find the Driver data that contains the following email
        Optional<Driver> driverOptional = driverService.getDriverByEmail(email);

        if (driverOptional.isEmpty()) {
            // Driver was not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // check if the passwords don't match
        if (!password.equals(driverOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@RequestBody Driver driver) {
        try {
            logger.info("Creating New Driver");
            Driver newDriver = authService.createDriver(driver);
            DriverResponse body = new DriverResponse(
                    newDriver.getName(),
                    newDriver.getEmail(),
                    newDriver.getSocial_security_number(),
                    newDriver.getIsBusy(),
                    newDriver.getImage(),
                    newDriver.getAge());
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateDriverById(@PathVariable("id") int driver_id,
            @RequestBody Driver driver) {
        logger.info("Driver controller: Updating Driver by id: " + driver_id);
        int result = driverService.updatedriver(
                driver_id,
                driver.getName(),
                driver.getAge(),
                driver.getEmail(),
                driver.getPassword(),
                driver.getSocial_security_number(),
                driver.getImage(),
                driver.getIsBusy());
        if (result == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok("The Driver with id: " + driver_id + " is updated successfully!");
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteDriverById(@PathVariable("id") int driver_id) {
        logger.info("Driver controller: Deleting Driver by id: " + driver_id);
        if (driverService.deleteDriverById(driver_id)) {
            return ResponseEntity.ok("The Driver with id: " + driver_id + " is deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
