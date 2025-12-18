package org.noteam.nextclient.models;

public class City {
    private int  city_id;
    private String name;
    private Country country;

    public City(int city_id, String name, Country country) {
        this.city_id = city_id;
        this.name = name;
        this.country = country;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
