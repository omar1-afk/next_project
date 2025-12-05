package com.noteam.next.entities;

import jakarta.persistence.*;

@Entity

@Table(name="vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int vehicle_id;

    @Column(name="wight")
    private int wight;


    public int getWight() {
        return wight;
    }

    public void setWight(int wight) {
        this.wight = wight;
    }


    public int getVehicle_id() {
        return vehicle_id;
    }


}
