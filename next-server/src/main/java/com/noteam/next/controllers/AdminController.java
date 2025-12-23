package com.noteam.next.controllers;

import com.noteam.next.dto.AdminResponse;
import com.noteam.next.entities.Admin;
import com.noteam.next.services.AdminServices;
import com.noteam.next.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

    @Autowired
    private AdminServices adminService;
    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<Admin> getAdminById(@RequestParam int admin_id) {
        logger.info("Getting admin by id: " + admin_id);

        Optional<Admin> adminOptional = adminService.getAdminById(admin_id);
        if (adminOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(adminOptional.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@RequestParam String email) {
        logger.info("Getting admin by email: " + email);
        Optional<Admin> adminOptional = adminService.getAdminByEmail(email);

        if (adminOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(adminOptional.get());
    }

    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(@RequestBody Admin admin) {
        try {
            logger.info("Creating New Admin");
            Admin newAdmin = authService.createAdmin(admin);
            AdminResponse res = new AdminResponse(
                    newAdmin.getName(),
                    newAdmin.getEmail(),
                    newAdmin.getSocialSecurityNumber(),
                    newAdmin.getImage(),
                    newAdmin.getAge());
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
