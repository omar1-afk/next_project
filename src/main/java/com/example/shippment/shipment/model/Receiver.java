package com.example.shippment.shipment.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "receivers") // to specifies the name of the database table
public class Receiver {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // for Auto-increment
    private Integer receiverId;

    private String name;

    @Column(unique = true) // Enforces the UNIQUE constraint
    private String socialSecurityNumber;

    private String phone;
    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    //Constructor
    public Receiver() {}

    public Integer getReceiverId() { return receiverId; }
    public void setReceiverId(Integer receiverId) { this.receiverId = receiverId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSocialSecurityNumber() { return socialSecurityNumber; }
    public void setSocialSecurityNumber(String socialSecurityNumber) { this.socialSecurityNumber = socialSecurityNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}