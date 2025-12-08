package com.noteam.next.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "country_name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "country",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<City> getCities() {
        return cities;
    }
}
