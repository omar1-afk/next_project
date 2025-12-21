package org.noteam.nextclient.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.noteam.nextclient.dto.ShipmentDetails;
import org.noteam.nextclient.dto.Order;
import org.noteam.nextclient.dto.State;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ShipmentDetailsController {

    private ShipmentDetails shipmentDetails = new ShipmentDetails(
            0, new ArrayList<>());
    private ArrayList<Order> backupOrders;
    private ObservableList<OrderDetails> orders = FXCollections.observableArrayList();

    //
    private FilteredList<OrderDetails> fList;

    public void setShipmentDetails(ShipmentDetails shipmentDetails) {

        if (idColumn.getCellValueFactory() == null) {
            idColumn.setCellValueFactory(data -> data.getValue().id());
            weightColumn.setCellValueFactory(data -> data.getValue().weight().asObject());
            statusColumn.setCellValueFactory(data -> data.getValue().status());
            priceColumn.setCellValueFactory(data -> data.getValue().price().asObject());
            dateColumn.setCellValueFactory(data -> data.getValue().date());
        }
        if (this.backupOrders == null) {
            this.backupOrders = new ArrayList<>();
            this.backupOrders.addAll(shipmentDetails.orders());
        }

        this.shipmentDetails = shipmentDetails;
        Integer shipmentId = shipmentDetails.id();
        shipmentIdLabel.setText(shipmentId.toString());

        orders.addAll(prepareOrders());
        fList = new FilteredList<>(orders, p -> true);

        completedCheckbox.selectedProperty().addListener(
                (observable, oldValue, newValue) -> applyFilter(datePicker));

        datePicker.valueProperty().addListener(
                (observable, oldValue, newValue) -> applyFilter(datePicker));

        ordersTable.setItems(fList);
    }

    @FXML
    private TableColumn<OrderDetails, String> idColumn;
    @FXML
    private TableColumn<OrderDetails, Integer> weightColumn;
    @FXML
    private TableColumn<OrderDetails, State> statusColumn;
    @FXML
    private TableColumn<OrderDetails, Float> priceColumn;
    @FXML
    private TableColumn<OrderDetails, LocalDate> dateColumn;

    @FXML
    private Label vehicleIdLabel;

    @FXML
    private Label driverIdLabel;

    @FXML
    private CheckBox completedCheckbox;

    private void applyFilter(DatePicker datePicker) {
        Predicate<OrderDetails> predicate = item -> {
            boolean completedMatch = completedCheckbox.isSelected();
            LocalDate date = datePicker.getValue();
            boolean dateMatch = date != null ? item.date().getValue().isEqual(date) : true;

            return completedMatch ? item.status().getValue() == State.DELEVERED : true && dateMatch;
        };
        fList.setPredicate(predicate);
    };

    @FXML
    private DatePicker datePicker;

    public record OrderDetails(
            SimpleStringProperty id,
            SimpleIntegerProperty weight,
            SimpleObjectProperty<State> status,
            SimpleFloatProperty price,
            SimpleObjectProperty<LocalDate> date) {
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
                    new SimpleObjectProperty<State>(order.state()),
                    new SimpleFloatProperty(order.price()),
                    new SimpleObjectProperty<LocalDate>(order.shippingDate()));
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
