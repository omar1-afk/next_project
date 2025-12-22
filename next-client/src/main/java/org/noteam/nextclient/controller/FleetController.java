package org.noteam.nextclient.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.noteam.nextclient.models.Vehicle;
import org.noteam.nextclient.utils.SqlUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FleetController implements Initializable {

  @FXML
  private BorderPane fleetPane;

  @FXML
  private TableView<Vehicle> fleetTable;
  @FXML
  private TableColumn<Vehicle, String> plateCol, stateCol, typeCol;
  @FXML
  private TableColumn<Vehicle, Integer> weightCol;
  @FXML
  private TableColumn<Vehicle, String> createdAtCol, updatedVehicleCol;
  @FXML
  private Label fleetCountLabel;

  @FXML
  private TextField searchField;
  @FXML
  private AnchorPane allPane, availablePane, unavailablePane;

  @FXML
  private VBox addVehicleForm;
  @FXML
  private TextField typeField, weightField, plateField;

  private ObservableList<Vehicle> masterData = FXCollections.observableArrayList();
  private FilteredList<Vehicle> filteredData;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Column Mapping -------------------
    plateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
    weightCol.setCellValueFactory(new PropertyValueFactory<>("wight")); // Matches model "wight"
    typeCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));

    stateCol.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
          setText(null);
        } else {
          Vehicle v = getTableRow().getItem();
          if (v.isUsed()) setText("IN USE");
          else if (v.isAvailable()) setText("AVAILABLE");
          else setText("MAINTENANCE");
        }
      }
    });

    // Double-Click -----------------
    fleetTable.setRowFactory(tv -> {
      TableRow<Vehicle> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          showUpdateVehicleWindow(row.getItem());
        }
      });
      return row;
    });

    // Filtering -----------------
    allPane.setOnMouseClicked(e -> filterByState("ALL", allPane));
    availablePane.setOnMouseClicked(e -> filterByState("AVAILABLE", availablePane));
    unavailablePane.setOnMouseClicked(e -> filterByState("UNAVAILABLE", unavailablePane));

    refreshTable();
  }

  // Fetch reset the table
  public void refreshTable() {
    List<Vehicle> vehicles = SqlUtil.getAllVehicles(); //
    masterData.setAll(vehicles);

    filteredData = new FilteredList<>(masterData, p -> true);
    fleetTable.setItems(filteredData);
    fleetCountLabel.setText(vehicles.size() + " cars");

    setupSearch();
  }

  private void setupSearch() {
    searchField.textProperty().addListener((obs, oldVal, newVal) -> {
      filteredData.setPredicate(v -> {
        if (newVal == null || newVal.isEmpty()) return true;
        return v.getLicensePlate().toLowerCase().contains(newVal.toLowerCase());
      });
      fleetCountLabel.setText(filteredData.size() + " Results");
    });
  }

  private void filterByState(String state, AnchorPane activePane) {
    allPane.setStyle("-fx-background-color: transparent;");
    availablePane.setStyle("-fx-background-color: transparent;");
    unavailablePane.setStyle("-fx-background-color: transparent;");
    activePane.setStyle("-fx-background-color: #F1F1F1; -fx-background-radius: 8;");

    filteredData.setPredicate(v -> {
      if (state.equals("ALL")) return true;
      if (state.equals("AVAILABLE")) return v.isAvailable() && !v.isUsed();
      if (state.equals("UNAVAILABLE")) return !v.isAvailable() || v.isUsed();
      return true;
    });
  }

  private void showUpdateVehicleWindow(Vehicle vehicle) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/update-car.fxml"));
      Parent root = loader.load();

      UpdateCarController controller = loader.getController();
      controller.setVehicleData(vehicle); //

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.setTitle("Update Vehicle: " + vehicle.getLicensePlate());

      // Auto-refresh when window closes ----------------------
      stage.setOnHidden(e -> refreshTable());

      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void onAddCarClick() {
    addVehicleForm.setVisible(!addVehicleForm.isVisible());
  }

  // POST request ------------
  @FXML
  private void onAddVehicle() {
    try {
      String plate = plateField.getText();
      int weight = Integer.parseInt(weightField.getText());
      String type = typeField.getText().toUpperCase();

      if (SqlUtil.createVehicle(plate, weight, type)) { //
        addVehicleForm.setVisible(false);
        refreshTable();
        clearAddFields();
      }
    } catch (NumberFormatException e) {
      System.err.println("Invalid weight input.");
    }
  }

  private void clearAddFields() {
    plateField.clear();
    weightField.clear();
    typeField.clear();
  }
  public BorderPane getFleetPane() {
    return fleetPane;
  }
}