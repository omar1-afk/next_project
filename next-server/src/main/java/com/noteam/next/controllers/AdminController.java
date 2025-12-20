package com.noteam.next.controllers;

import com.noteam.next.entities.Admin;
import com.noteam.next.services.AdminServices;
import com.noteam.next.services.AuthService;
import com.noteam.next.services.UserService;

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
  private UserService userService;

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

  @PostMapping("/login")
  public ResponseEntity<Admin> loginAdmin(@RequestParam String email, @RequestParam String password) {
    // find the Admin data that contains the following email
    Optional<Admin> adminOptional = adminService.getAdminByEmail(email);

    if (adminOptional.isEmpty()) { // Admin was not found
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    if (!password.equals(adminOptional.get().getPassword())) { // check if the passwords don't match
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping
  public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
    try {
      logger.info("Creating New Admin");
      Admin newAdmin = userService.createAdmin(admin);
      return ResponseEntity.status(HttpStatus.OK).body(newAdmin);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
