package org.noteam.nextclient.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.noteam.nextclient.dto.ShipmentDetails;
import org.noteam.nextclient.dto.Order;
import org.noteam.nextclient.dto.State;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ShipmentDetailsController {

  private ShipmentDetails shipmentDetails = new ShipmentDetails(
      0, new ArrayList<>());
  private ArrayList<Order> backupOrders;

  public void setShipmentDetails(ShipmentDetails shipmentDetails) {

    if (idColumn.getCellValueFactory() == null) {

      idColumn.setCellValueFactory(data -> data.getValue().id());
      weightColumn.setCellValueFactory(data -> data.getValue().weight().asObject());
      statusColumn.setCellValueFactory(data -> data.getValue().status());
      priceColumn.setCellValueFactory(data -> data.getValue().price().asObject());
    }
    if (this.backupOrders == null) {
      this.backupOrders = new ArrayList<>();
      this.backupOrders.addAll(shipmentDetails.orders());
    }

    this.shipmentDetails = shipmentDetails;
    refresh();
  }

  @FXML
  private TableColumn<OrderDetails, String> idColumn;
  @FXML
  private TableColumn<OrderDetails, Integer> weightColumn;
  @FXML
  private TableColumn<OrderDetails, String> statusColumn;
  @FXML
  private TableColumn<OrderDetails, Float> priceColumn;

  @FXML
  private CheckBox completedCheckbox;

  @FXML
  protected void filterCompleted() {
    boolean isSelected = completedCheckbox.isSelected();
    ArrayList<Order> orders = this.shipmentDetails.orders();
    ArrayList<Order> result = new ArrayList<>();
    if (isSelected) {
      for (Order order : orders) {
        switch (order.state()) {
          case DELEVERED:
            result.add(order);
          default:
            continue;
        }
      }
    } else {
      result = this.backupOrders;
    }
    this.shipmentDetails.orders().clear();
    this.shipmentDetails.orders().addAll(result);
    System.out.println(this.shipmentDetails.orders().size());
    this.refresh();
  }

  private void refresh() {
    Integer shipmentId = shipmentDetails.id();
    shipmentIdLabel.setText(shipmentId.toString());
    ObservableList<OrderDetails> orders = prepareOrders();
    ordersTable.setItems(orders);
  }

  public record OrderDetails(
      SimpleStringProperty id,
      SimpleIntegerProperty weight,
      SimpleStringProperty status,
      SimpleFloatProperty price) {
  }

  private String statusToString(State state) {
    switch (state) {
      case PICKED:
        return "Picked";
      case PACKAGING:
        return "In packaging process";
      case SHIPPING:
        return "Shipping";
      case DELEVERED:
        return "Delivered";
      case RETURNED:
        return "Returned";
      default:
        return "NONE";
    }
  }

  private ObservableList<OrderDetails> prepareOrders() {
    ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
    for (Order order : this.shipmentDetails.orders()) {
      OrderDetails orderDetails = new OrderDetails(
          new SimpleStringProperty(
              "Order Id: " + order.id()),
          new SimpleIntegerProperty(order.weight()),
          new SimpleStringProperty(
              statusToString(order.state())),
          new SimpleFloatProperty(order.price()));
      orderDetailsList.add(orderDetails);
    }
    ;
    return FXCollections.observableArrayList(orderDetailsList);
  };

  @FXML
  private Label shipmentIdLabel;
  @FXML
  private TableView ordersTable;

}
