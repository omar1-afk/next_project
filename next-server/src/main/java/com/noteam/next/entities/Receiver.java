package com.noteam.next.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "receivers") // to specifies the name of the database table
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // for Auto-increment
    @Column(name = "receiver_id")
    private Integer receiverId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "socialSecurityNumber", nullable = false, unique = true) // Enforces the UNIQUE constraint
    private String socialSecurityNumber;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructor
    public Receiver() {
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    // public void setReceiverId(Integer receiverId) { (we don't need to set the
    // receiver id)
    // this.receiverId = receiverId;
    // }

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

// Men3em test
// @Entity
// @Table(name = "receivers")
// public class Receiver {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer id;
// private String shipmentName;
// @OneToMany(mappedBy = "receiver",cascade =
// {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
// private List<Order> orders = new ArrayList<>();
//
// public Integer getId() {
// return id;
// }
//
// public void setId(Integer id) {
// this.id = id;
// }
//
// public String getShipmentName() {
// return shipmentName;
// }
//
// public void setShipmentName(String shipmentName) {
// this.shipmentName = shipmentName;
// }
//
// public List<Order> getOrders() {
// return orders;
// }
//
// public void setOrders(List<Order> orders) {
// this.orders = orders;
// }
// }
