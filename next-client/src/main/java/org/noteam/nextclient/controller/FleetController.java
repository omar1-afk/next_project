package org.noteam.nextclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.noteam.nextclient.dto.Vehicle;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FleetController {

    @FXML
    private BorderPane fleetPane;
    public BorderPane getFleetPane(){
        return fleetPane;
    }


    @FXML private TableView<Vehicle> fleetTable;
    @FXML private TableColumn<Vehicle, String> plateCol;
    @FXML private TableColumn<Vehicle, String> stateCol;
    @FXML private TableColumn<Vehicle, Integer> weightCol;
    @FXML private TableColumn<Vehicle, Vehicle.VehicleType> typeCol;
    @FXML private TableColumn<Vehicle, String> createdAtCol;
    @FXML private TableColumn<Vehicle, Void> updatedVehicleCol;


    @FXML private Label fleetCountLabel;
    @FXML private TextField searchField;


    @FXML private VBox addVehicleForm;
    @FXML private TextField typeField;
    @FXML private TextField weightField;
    @FXML private TextField plateField;


    @FXML private AnchorPane allPane;
    @FXML private AnchorPane availablePane;
    @FXML private AnchorPane unavailablePane;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
    private FilteredList<Vehicle> filteredVehicles;

    @FXML
    public void initialize() {

        plateCol.setCellValueFactory(c -> c.getValue().licensePlateProperty());

        stateCol.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(
                        cell.getValue().isAvailable() ? "Available" : "Unavailable"
                )
        );

        weightCol.setCellValueFactory(c -> c.getValue().weightLimitProperty().asObject());
        typeCol.setCellValueFactory(c -> c.getValue().typeProperty());
        createdAtCol.setCellValueFactory(c -> {
            LocalDateTime dt = c.getValue().getCreatedAt();
            return new ReadOnlyStringWrapper(
                    dt != null ? dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "-"
            );
        });


        updatedVehicleCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("View");

            {
                btn.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold;");
                btn.setOnAction(e -> {
                    Vehicle v = getTableView().getItems().get(getIndex());
                    populateViewForm(v);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        filteredVehicles = new FilteredList<>(vehicleList, v -> true);
        fleetTable.setItems(filteredVehicles);


        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String search = newVal.toLowerCase().trim();
            filteredVehicles.setPredicate(v ->
                    search.isEmpty()
                            || v.getLicensePlate().toLowerCase().contains(search)
                            || String.valueOf(v.getVehicleId()).contains(search)
            );
        });

        allPane.setOnMouseClicked(e -> filteredVehicles.setPredicate(v -> true));
        availablePane.setOnMouseClicked(e -> filteredVehicles.setPredicate(Vehicle::isAvailable));
        unavailablePane.setOnMouseClicked(e -> filteredVehicles.setPredicate(v -> !v.isAvailable()));


        loadVehicles();
        addVehicleForm.setVisible(false);
    }


    private void loadVehicles() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/vehicles"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Vehicle[] vehicles = mapper.readValue(response.body(), Vehicle[].class);
                vehicleList.setAll(vehicles);
                fleetCountLabel.setText(vehicleList.size() + " cars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddCarClick() {
        clearForm();
        addVehicleForm.setVisible(true);
    }


    @FXML
    private void onAddVehicle() {
        try {
            String typeText = typeField.getText().trim().toUpperCase();
            String plateText = plateField.getText().trim();
            int weight = Integer.parseInt(weightField.getText().trim());

            if (typeText.isEmpty() || plateText.isEmpty() || weight <= 0) return;

            Vehicle.VehicleType type = Vehicle.VehicleType.valueOf(typeText);
            Vehicle vehicle = new Vehicle(0, type, weight, plateText);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/vehicles"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(vehicle)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                loadVehicles();
                addVehicleForm.setVisible(false);
                clearForm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void populateViewForm(Vehicle v) {
        addVehicleForm.setVisible(true);
        typeField.setText(v.getType().name());
        weightField.setText(String.valueOf(v.getWeightLimit()));
        plateField.setText(v.getLicensePlate());
    }


    private void clearForm() {
        typeField.clear();
        weightField.clear();
        plateField.clear();
    }
}


// ----------------------------------------------------------------------------------
//package org.noteam.nextclient.controller;
//
//import javafx.fxml.FXML;
//import javafx.scene.layout.BorderPane;
//
//public class FleetController {
//    @FXML
//    private BorderPane fleetPane;
//    public BorderPane getFleetPane(){
//        return fleetPane;
//    }
//}
