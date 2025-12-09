package com.noteam.next.entities;

import jakarta.persistence.*;

@Entity

@Table(name="vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int vehicle_id;

    @Column(name="weight")
    private int weight;


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public int getVehicle_id() {
        return vehicle_id;
    }


}
