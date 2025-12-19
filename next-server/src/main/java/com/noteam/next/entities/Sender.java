package com.noteam.next.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "senders") // to specifies the name of the database table
public class Sender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // for Auto-increment
    private Integer receiverId;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="socialSecurityNumber", nullable = false, unique = true) // Enforces the UNIQUE constraint
    private String socialSecurityNumber;

    @Column(name ="phone", nullable = false)
    private String phone;

    @Column(name ="commercial_register_number", nullable = true)
    private String commercial_register_number;

    @Column(name ="email", nullable = false)
    private String email;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updatedAt", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Integer getReceiverId() {
        return receiverId;
    }

//    public void setReceiverId(Integer receiverId) { (we don't need to set the sender id)
//        this.receiverId = receiverId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommercial_register_number() {
        return commercial_register_number;
    }

    public void setCommercial_register_number(String commercial_register_number) {
        this.commercial_register_number = commercial_register_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}