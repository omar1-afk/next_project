package com.noteam.next.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer city_id;
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }
}
