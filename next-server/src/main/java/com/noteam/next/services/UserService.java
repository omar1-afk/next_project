package com.noteam.next.services;

import com.noteam.next.repositories.AdminRepository;
import com.noteam.next.repositories.DriverRepository;
import com.noteam.next.entities.Admin;
import com.noteam.next.entities.Driver;
import com.noteam.next.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final AdminRepository adminRepo;
    @Autowired
    private final DriverRepository driverRepo;

    public UserService(AdminRepository adminRepo, DriverRepository driverRepo) {
        this.adminRepo = adminRepo;
        this.driverRepo = driverRepo;
    }

    public ArrayList<User> findAll() {
        ArrayList<User> result = new ArrayList<>();
        ArrayList<Admin> admins = (ArrayList<Admin>) adminRepo.findAll();
        ArrayList<Driver> drivers = (ArrayList<Driver>) driverRepo.findAll();
        result.addAll(admins);
        result.addAll(drivers);
        return result;
    }

    public Optional<User> findById(Integer id) {
        Optional<Admin> admin = adminRepo.findById(id);
        if (admin.isPresent()) {
            return Optional.of(admin.get());
        }
        Optional<Driver> driver = driverRepo.findById(id);
        if (driver.isPresent()) {
            return Optional.of(driver.get());
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        Optional<Admin> admin = adminRepo.findByEmail(email);
        if (admin.isPresent()) {
            return Optional.of(admin.get());
        }
        Optional<Driver> driver = driverRepo.findByEmail(email);
        if (driver.isPresent()) {
            return Optional.of(driver.get());
        }
        return Optional.empty();
    }

    public Admin createAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    public Driver createDriver(Driver driver) {
        Driver newDriver = new Driver();
        newDriver.setName(driver.getName());
        newDriver.setAge(driver.getAge());
        newDriver.setImage(driver.getImage());
        newDriver.setSocialSecurityNumber(driver.getSocialSecurityNumber());
        newDriver.setEmail(driver.getEmail());
        newDriver.setPassword(driver.getPassword());
        newDriver.setIsbusy(false);
        newDriver.setCreatedAt(LocalDateTime.now());
        newDriver.setUpdatedAt(LocalDateTime.now());
        return driverRepo.save(newDriver);
    }
}
