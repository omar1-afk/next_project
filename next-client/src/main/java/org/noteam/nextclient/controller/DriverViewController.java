package org.noteam.nextclient.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DriverViewController implements Initializable {

    /* ================= ROOT ================= */
    @FXML private BorderPane driverPane;

    /* ================= TOP ================= */
    @FXML private Button syncOrderBtn;

    /* ================= CENTER ================= */
    @FXML private Label orderNumLabel;
    @FXML private TextField searchFeild;
    @FXML private TableView<ShipmentRow> orderTable;

    @FXML private TableColumn<ShipmentRow, String> orderIdCol;
    @FXML private TableColumn<ShipmentRow, String> weightCol;
    @FXML private TableColumn<ShipmentRow, String> cityCol;
    @FXML private TableColumn<ShipmentRow, String> priceCol;
    @FXML private TableColumn<ShipmentRow, String> shipmentCol;
    @FXML private TableColumn<ShipmentRow, String> statusCol;
    @FXML private TableColumn<ShipmentRow, String> createdAtCol;

    private ObservableList<ShipmentRow> observableList;
    private final ObservableList<String> statusOptions = FXCollections.observableArrayList(
            "Pending", "Picked", "Delivered", "Cancelled"
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* -------- Table Binding -------- */
        orderIdCol.setCellValueFactory(cell -> cell.getValue().orderIdProperty());
        weightCol.setCellValueFactory(cell -> cell.getValue().weightProperty());
        cityCol.setCellValueFactory(cell -> cell.getValue().cityProperty());
        priceCol.setCellValueFactory(cell -> cell.getValue().priceProperty());
        shipmentCol.setCellValueFactory(cell -> cell.getValue().shipmentProperty());
        createdAtCol.setCellValueFactory(cell -> cell.getValue().createdAtProperty());

        /* -------- Status Column as ComboBox with Colors -------- */
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        statusCol.setCellFactory(ComboBoxTableCell.forTableColumn(statusOptions));
        statusCol.setCellFactory(column -> {
            return new TableCell<ShipmentRow, String>() {
                private final ComboBox<String> comboBox = new ComboBox<>(statusOptions);

                {
                    comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                        if (getTableRow() != null && getTableRow().getItem() != null) {
                            getTableRow().getItem().setStatus(newVal);
                            updateItemColor(newVal);
                        }
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        comboBox.setValue(item);
                        setGraphic(comboBox);
                        updateItemColor(item);
                    }
                }

                private void updateItemColor(String status) {
                    switch (status) {
                        case "Pending" -> comboBox.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                        case "Picked" -> comboBox.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
                        case "Delivered" -> comboBox.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
                        case "Cancelled" -> comboBox.setStyle("-fx-background-color: tomato; -fx-text-fill: white;");
                        default -> comboBox.setStyle("");
                    }
                }
            };
        });

        /* -------- Data -------- */
        observableList = FXCollections.observableArrayList(
                new ShipmentRow("ORD-1001", "1.5 kg", "Cairo", "120 EGP", "SH-9001", "Pending"),
                new ShipmentRow("ORD-1002", "2.0 kg", "Alex", "180 EGP", "SH-9002", "Picked"),
                new ShipmentRow("ORD-1003", "3.2 kg", "Giza", "220 EGP", "SH-9003", "Delivered")
        );

        orderTable.setItems(observableList);
        updateShipmentCount(observableList.size());

        /* -------- Search/Filter Functionality -------- */
        searchFeild.textProperty().addListener((obs, oldVal, newVal) -> {
            ObservableList<ShipmentRow> filteredList = observableList.filtered(shipment ->
                    shipment.getOrderId().toLowerCase().contains(newVal.toLowerCase()) ||
                            shipment.getShipment().toLowerCase().contains(newVal.toLowerCase()) ||
                            shipment.getCity().toLowerCase().contains(newVal.toLowerCase())
            );

            orderTable.setItems(filteredList);
            updateShipmentCount(filteredList.size());
        });

        /* -------- Button -------- */
        syncOrderBtn.setOnAction(e -> syncShipments());
    }

    private void syncShipments() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sync");
        alert.setHeaderText(null);
        alert.setContentText("Shipments synced successfully!");
        alert.show();
    }

    private void updateShipmentCount(int count) {
        orderNumLabel.setText(count + " shipment" + (count != 1 ? "s" : ""));
    }

    /* =========================================================
       INNER ROW CLASS
       ========================================================= */
    public static class ShipmentRow {

        private final SimpleStringProperty orderId;
        private final SimpleStringProperty weight;
        private final SimpleStringProperty city;
        private final SimpleStringProperty price;
        private final SimpleStringProperty shipment;
        private final SimpleStringProperty status;
        private final SimpleStringProperty createdAt;

        public ShipmentRow(String orderId,
                           String weight,
                           String city,
                           String price,
                           String shipment,
                           String status) {

            this.orderId = new SimpleStringProperty(orderId);
            this.weight = new SimpleStringProperty(weight);
            this.city = new SimpleStringProperty(city);
            this.price = new SimpleStringProperty(price);
            this.shipment = new SimpleStringProperty(shipment);
            this.status = new SimpleStringProperty(status);

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd MMM yyyy • HH:mm");
            this.createdAt = new SimpleStringProperty(LocalDateTime.now().format(formatter));
        }

        public String getOrderId() { return orderId.get(); }
        public String getWeight() { return weight.get(); }
        public String getCity() { return city.get(); }
        public String getPrice() { return price.get(); }
        public String getShipment() { return shipment.get(); }
        public String getStatus() { return status.get(); }
        public String getCreatedAt() { return createdAt.get(); }

        public void setStatus(String status) { this.status.set(status); }

        public SimpleStringProperty orderIdProperty() { return orderId; }
        public SimpleStringProperty weightProperty() { return weight; }
        public SimpleStringProperty cityProperty() { return city; }
        public SimpleStringProperty priceProperty() { return price; }
        public SimpleStringProperty shipmentProperty() { return shipment; }
        public SimpleStringProperty statusProperty() { return status; }
        public SimpleStringProperty createdAtProperty() { return createdAt; }
    }

    public BorderPane getDriverPane() {
        return driverPane;
    }
}
