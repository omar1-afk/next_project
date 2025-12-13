package com.noteam.next.services;
import com.noteam.next.entities.Driver;
import com.noteam.next.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class DriverService {
    private  static final Logger logger = Logger.getLogger(DriverService.class.getName());

    @Autowired
    private DriverRepository driverRepository;

    // get
    public Optional<Driver> getDriverById(int driver_id){
        logger.info("Getting the Driver by id: " + driver_id);
        return driverRepository.findById(driver_id);
    }

    public Optional<Driver> getDriverByEmail(String email){
        logger.info("Getting the Driver by email: " + email);
        return driverRepository.findByEmail(email);
    }

    public List<Driver> findAll() {
        logger.info("Getting all drivers");
        return driverRepository.findAll();
    }

    // post
    public int createdriver(String name, int age ,String image, String social_security_number, String email, String password, Boolean isbusy){
        logger.info("creating a new driver");
        Driver driver = new Driver();
        driver.setName(name);
        driver.setAge(age);
        driver.setImage(image);
        driver.setSocial_security_number(social_security_number);
        driver.setEmail(email);
        driver.setPassword(password);
        driver.setIsbusy(false);
        driver.setCreated_at(LocalDateTime.now());
        driver.setUpdated_at(LocalDateTime.now());
        driverRepository.save(driver);
        return 1;
    }

    public int updatedriver (int driver_id, String name, int age , String image, String social_security_number, String email, String password, Boolean isbusy){
        Optional<Driver> driverOptional= getDriverById(driver_id);
        if(driverOptional.isEmpty()) {
            logger.info("Driver service: The driver with id: " + driver_id + " is not found!");
            return -1;
        }
        else{
                logger.info("updating driver by id : " + driver_id);
                Driver driver = new Driver();
                driver.setName(name);
                driver.setAge(age);
                driver.setImage(image);
                driver.setSocial_security_number(social_security_number);
                driver.setEmail(email);
                driver.setPassword(password);
                driver.setIsbusy(false);
                driver.setUpdated_at(LocalDateTime.now());
                driverRepository.save(driver);
                return 1;
            }
        }
    // delete
    public void deleteById(int driver_id) {
        logger.info("Deleting the Driver by id: " + driver_id );
        driverRepository.deleteById(driver_id);
    }
}
