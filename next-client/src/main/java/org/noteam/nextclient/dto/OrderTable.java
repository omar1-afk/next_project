package org.noteam.nextclient.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class OrderTable {
    private IntegerProperty orderId=new SimpleIntegerProperty();
    private IntegerProperty orderPrice=new SimpleIntegerProperty();
    private IntegerProperty orderWeight=new SimpleIntegerProperty();
    private BooleanProperty selected=new SimpleBooleanProperty(false);

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
        this.orderId.set(orderId);
        this.orderPrice.set(orderPrice);
        this.orderWeight.set(orderWeight);
    }

    public IntegerProperty getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public IntegerProperty getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice.set(orderPrice);
    }

    public IntegerProperty getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(int orderWeight) {
        this.orderWeight.set(orderWeight);
    }

    public BooleanProperty getSelected() {
        return selected;
    }

    public void setSelected(BooleanProperty selected) {
        this.selected = selected;
    }
}
