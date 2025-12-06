package com.noteam.next.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String region;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private boolean flameable;
    @Column(nullable = false)
    private boolean breakable;
    @Column(nullable = false)
    private int price;
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(nullable = false)
    private int weight;
    @Column(name = "shipping_date") //can be null!!!!!!!!!!!!!!!!1
    private LocalDate shippingDate;
    @ManyToOne
    @JoinColumn(name = "shipment_id",nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnoreProperties("orders")
    Shipment shipment;
    @ManyToOne
    @JoinColumn(name="receiver_id",nullable = true)
    @JsonIgnoreProperties("orders")
    Receiver receiver;
    @ManyToOne
    @JoinColumn(name="sender_id",nullable = true)
    @JsonIgnoreProperties("orders")
    Sender sender;
    @Column(name = "boxes_count")
    int boxesCount;
    @Column(name = "created_at",updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
    public Order(){}
    public Order(String country, String city, String region, String address, boolean flameable, boolean breakable, int price, State state, int weight, LocalDate shippingDate, Shipment shipment, Receiver receiver, Sender sender, int boxesCount) {
        this.country = country;
        this.city = city;
        this.region = region;
        this.address = address;
        this.flameable = flameable;
        this.breakable = breakable;
        this.price = price;
        this.state = state;
        this.weight = weight;
        this.shippingDate = shippingDate;
        this.shipment = shipment;
        this.receiver = receiver;
        this.sender = sender;
        this.boxesCount = boxesCount;
    }



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFlameable() {
        return flameable;
    }
    public void setFlameable(boolean flameable) {
        this.flameable = flameable;
    }

    public boolean isBreakable() {
        return breakable;
    }
    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Receiver getReceiver() {
        return receiver;
    }
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Sender getSender() {
        return sender;
    }
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public int getBoxesCount() { return boxesCount; }
    public void setBoxesCount(int boxesCount) { this.boxesCount = boxesCount; }

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

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

}
