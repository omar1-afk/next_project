package org.noteam.nextclient.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class OrderTable {
    int orderId;
    int orderWeight;
    private BooleanProperty selected=new SimpleBooleanProperty(false);
    int orderPrice;
    public BooleanProperty selectedProperty() {
        return selected;
    }
    public void setSelected(boolean value) {
        selected.set(value);
    }
    public Boolean isSelected() {
        return selected.get();
    }
    public OrderTable(int orderId, int orderPrice, int orderWeight) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderWeight = orderWeight;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(int orderWeight) {
        this.orderWeight = orderWeight;
    }
}
