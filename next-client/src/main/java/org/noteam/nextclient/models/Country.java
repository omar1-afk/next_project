package org.noteam.nextclient.models;

import java.util.ArrayList;
import java.util.List;

public class Country {

    private Integer country_id;
    private String name;
    private List<City> cities ;

    public Country(Integer country_id, String name,List<City>cities) {
        this.country_id = country_id;
       this.cities = cities;
        this.name = name;
    }
    public Country(Integer country_id, String name) {
      this.country_id = country_id;
      this.name = name;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
