package com.noteam.next.entities;
import jakarta.persistence.*;

@Entity
@Table(name="drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driver_id;

    public Integer getDriver_id() {
        return driver_id;
    }

}
