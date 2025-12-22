package com.noteam.next.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnoreProperties("cities")
    private Country country;
    @OneToMany(mappedBy = "city", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Order> orders;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
