package com.noteam.next.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipment_id;
    @Column(name="total_weight")
    private Integer total_weight;
    @Column(name="shipping_date")
    private Date shipping_date;
    @Column(name="is_complete")
    private Boolean isComplete;
    @Column(name="created_at")
    private LocalDateTime created_at ;
    @Column(name="updated_at")
    private LocalDateTime updated_at ;
    @ManyToOne
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne
    @JoinColumn(name="driver_id")
    private Driver driver;
    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orderList;


    public List<Order> getOrdersList() {
        return orderList;
    }

    public void setOrdersList(List<Order> orderList) {
        this.orderList = orderList;
    }




    public Integer getShipment_id() {
        return shipment_id;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Admin getAdmin() {return admin;}

    public void setAdmin(Admin admin) {this.admin = admin;}

    public Integer getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(Integer total_weight) {
        this.total_weight = total_weight;
    }

    public Date getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(Date shipping_date) {
        this.shipping_date = shipping_date;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }


}
