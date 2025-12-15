package com.noteam.next.services;

import com.noteam.next.entities.Admin;
import com.noteam.next.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AdminServices {
    @Autowired
    private AdminRepository adminRepository;
    private  static final Logger logger = Logger.getLogger(DriverService.class.getName());

    // get
    public Optional<Admin> getAdminById(int admin_id){
        logger.info("Getting the Admin by id: " + admin_id);
        return adminRepository.findById(admin_id);
    }
    public Optional<Admin> getAdminByEmail(String email){
        logger.info("Getting the Admin by email: " + email);
        return adminRepository.findByEmail(email);
    }


}
