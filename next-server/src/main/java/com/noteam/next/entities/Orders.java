package com.noteam.next.entities;

import jakarta.persistence.*;

@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;
    @ManyToOne
    @JoinColumn(name="shipment_id")
    private Shipment shipment;
}
