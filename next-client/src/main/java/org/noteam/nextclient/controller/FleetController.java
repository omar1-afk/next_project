package org.noteam.nextclient.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.noteam.nextclient.dto.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;

public class FleetController {

    @FXML private TableView<Vehicle> fleetTable;
    @FXML private TableColumn<Vehicle, String> plateCol;
    @FXML private TableColumn<Vehicle, String> stateCol;
    @FXML private TableColumn<Vehicle, Integer> weightCol;
    @FXML private TableColumn<Vehicle, Vehicle.VehicleType> typeCol;
    @FXML private TableColumn<Vehicle, String> createdAtCol;
    @FXML private TableColumn<Vehicle, Button> updatedVehicleCol;

    @FXML private Label fleetCountLabel;
    @FXML private TextField searchField;
    @FXML private VBox addVehicleForm;
    @FXML private TextField typeField, weightField, plateField;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup table columns
        plateCol.setCellValueFactory(cell -> cell.getValue().licensePlateProperty());
        stateCol.setCellValueFactory(cell -> {
            String state = cell.getValue().isAvailable() ? "Available" : "Unavailable";
            return new ReadOnlyStringWrapper(state);
        });
        weightCol.setCellValueFactory(cell -> cell.getValue().weightLimitProperty().asObject());
        typeCol.setCellValueFactory(cell -> cell.getValue().typeProperty());
        createdAtCol.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
        );
        updatedVehicleCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Update");

            {
                btn.setOnAction(event -> {
                    Vehicle v = getTableView().getItems().get(getIndex());
                    populateUpdateForm(v);
                });
                btn.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold;");
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        fleetTable.setItems(vehicleList);

        // Load initial vehicles
        loadVehicles();

        // Hide add form initially
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
                fleetCountLabel.setText(vehicles.length + " cars");
            } else {
                System.out.println("Failed to load vehicles: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddCarClick() {
        addVehicleForm.setVisible(true);
        clearForm();
    }

    private void clearForm() {
        typeField.clear();
        weightField.clear();
        plateField.clear();
    }

    @FXML
    private void onAddVehicle() {
        try {
            String typeText = typeField.getText().trim().toUpperCase();
            String plateText = plateField.getText().trim();
            int weight = Integer.parseInt(weightField.getText().trim());

            if (typeText.isEmpty() || plateText.isEmpty()) {
                System.out.println("Type and Plate must not be empty!");
                return;
            }

            Vehicle.VehicleType type = Vehicle.VehicleType.valueOf(typeText);
            Vehicle vehicle = new Vehicle(0, type, weight, plateText);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/vehicles"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(vehicle)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                Vehicle savedVehicle = mapper.readValue(response.body(), Vehicle.class);
                vehicleList.add(savedVehicle);
                fleetCountLabel.setText(vehicleList.size() + " cars");
            } else {
                System.out.println("Failed to save vehicle: " + response.body());
            }

            addVehicleForm.setVisible(false);
            clearForm();
        } catch (NumberFormatException e) {
            System.out.println("Weight must be a valid number!");
        } catch (IllegalArgumentException e) {
            System.out.println("Type must be VAN or TRUCK!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateUpdateForm(Vehicle vehicle) {
        addVehicleForm.setVisible(true);
        typeField.setText(vehicle.getType().name());
        weightField.setText(String.valueOf(vehicle.getWeightLimit()));
        plateField.setText(vehicle.getLicensePlate());
        // Optional: adjust the button to "Update" mode if needed
    }
}


