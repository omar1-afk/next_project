package org.noteam.nextclient.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Date;

public class Order {
    private int orderId;
    private Shipment shipment;
    private Sender sender;
    private Reciever receiver;
    private Country country;
    private City city;
    private String region;
    private String address;
    private boolean flammable;
    private boolean breakable;
    private int price;
    private enum state {
        Pending,
        PACKAGING,
        SHIPPING,
        PIKED
    }

    private int weight;

    private int boxs;

    public Order(int orderId, int boxs, int weight, int price, boolean breakable, boolean flammable, String address, String region, City city, Country country, Reciever receiver, Sender sender, Shipment shipment) {
        this.orderId = orderId;
        this.boxs = boxs;
        this.weight = weight;
        this.price = price;
        this.breakable = breakable;
        this.flammable = flammable;
        this.address = address;
        this.region = region;
        this.city = city;
        this.country = country;
        this.receiver = receiver;
        this.sender = sender;
        this.shipment = shipment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipmentId(Shipment shipment) {
        this.shipment= shipment;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Reciever getReceiver() {
        return receiver;
    }

    public void setReceiver(Reciever receiver) {
        this.receiver= receiver;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFlammable() {
        return flammable;
    }

    public void setFlammable(boolean flammable) {
        this.flammable = flammable;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBoxs() {
        return boxs;
    }

    public void setBoxs(int boxs) {
        this.boxs = boxs;
    }
}
