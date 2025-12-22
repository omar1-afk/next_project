package org.noteam.nextclient.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.noteam.nextclient.models.Driver;
import org.noteam.nextclient.utils.SqlUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class DriverController implements Initializable {

  @FXML
  private BorderPane driverPane;
  @FXML
  private Label DriverNumLabel;
  @FXML
  private TextField searchFeild;
  @FXML
  private AnchorPane AllPane, NamePane, AgePane;

  @FXML
  private TableView<DriverRow> DriverTable;
  @FXML
  private TableColumn<DriverRow, Integer> DriverIDCol;
  @FXML
  private TableColumn<DriverRow, String> DriverNameCol, EmailCol, AgeCol, SSNCol;
  @FXML
  private TableColumn<DriverRow, Boolean> BusyCol;

  private ObservableList<DriverRow> observableList;
  private List<Driver> allFetchedDrivers;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Double-Click ---------
    DriverTable.setRowFactory(tv -> {
      TableRow<DriverRow> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          showUpdateWindow(row.getItem());
        }
      });
      return row;
    });

    refreshTable();

    // TESTING DATA instead of server -------------
//
//    allFetchedDrivers = new ArrayList<>();
//    allFetchedDrivers.add(new Driver(1, "Omar", "", 25, "123456", "omar@test.com", "123", false));
//    allFetchedDrivers.add(new Driver(2, "Test User", "", 30, "999-99-9999", "test@mail.com", "pass", true));
//    setDriverTableView(allFetchedDrivers);

  }

  public void refreshTable() {
    allFetchedDrivers = SqlUtil.getAllDrivers();
    setDriverTableView(allFetchedDrivers);
  }

  public void setDriverTableView(List<Driver> driverList) {
    DriverIDCol.setCellValueFactory(new PropertyValueFactory<>("driverID"));
    DriverNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    AgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
    SSNCol.setCellValueFactory(new PropertyValueFactory<>("SSN"));
    BusyCol.setCellValueFactory(new PropertyValueFactory<>("busy"));

    observableList = FXCollections.observableArrayList();

    for (Driver driver : driverList) {
      observableList.add(new DriverRow(
        driver.getDriverId(),
        driver.getName(),
        driver.getEmail(),
        driver.getAge(),
        driver.getSocialSecurityNumber(),
        driver.isBusy()
      ));
    }

    DriverTable.setItems(observableList);
    DriverNumLabel.setText(driverList.size() + " Drivers");
  }

  //  Update Window Logic ------------

  private void showUpdateWindow(DriverRow selectedRow) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/update-driver.fxml"));
      Parent root = loader.load();

      UpdateDriverController controller = loader.getController();

      Driver driverToUpdate = findDriverInList(selectedRow.getDriverID());

      if (driverToUpdate != null) {
        controller.setDriverData(driverToUpdate);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Update Driver: " + driverToUpdate.getName());

        // Refresh table when window is closed
        stage.setOnHidden(e -> refreshTable());

        stage.show();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Create Window Logic ------------
  @FXML
  private void handleOpenCreateDriver() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/create-driver.fxml"));
      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.setTitle("Create New Driver");

      // Refresh table when window is closed (so the new driver appears)
      stage.setOnHidden(e -> refreshTable());

      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Driver findDriverInList(int id) {
    if (allFetchedDrivers == null) return null;
    return allFetchedDrivers.stream()
      .filter(d -> d.getDriverId() == id)
      .findFirst()
      .orElse(null);
  }

  // Tab Sorting -----------------

  @FXML
  public void getDriverByName() {
    makeStateActive(NamePane);
    if (observableList != null) {
      observableList.sort(Comparator.comparing(row -> row.getName().toLowerCase()));
    }
  }

  @FXML
  public void getDriverByAge() {
    makeStateActive(AgePane);
    if (observableList != null) {
      // Use standard String comparison since Age is "X years"
      observableList.sort(Comparator.comparing(DriverRow::getAge));
    }
  }

  @FXML
  public void getAllDrivers() {
    makeStateActive(AllPane);
    if (observableList != null) {
      observableList.sort(Comparator.comparingInt(DriverRow::getDriverID));
    }
  }

  private void makeStateActive(AnchorPane statePane) {
    deactivateAllStates();
    statePane.setStyle("-fx-background-color: #F1F1F1; -fx-background-radius: 8px;");
  }

  private void deactivateAllStates() {
    AllPane.setStyle("-fx-background-radius: 8px; -fx-background-color: transparent;");
    NamePane.setStyle("-fx-background-radius: 8px; -fx-background-color: transparent;");
    AgePane.setStyle("-fx-background-radius: 8px; -fx-background-color: transparent;");
  }

  public BorderPane getDriverPane() { return driverPane; }

  public static class DriverRow {
    private final SimpleIntegerProperty driverID;
    private final SimpleStringProperty name, email, age, SSN;
    private final SimpleBooleanProperty busy;

    public DriverRow(Integer id, String name, String email, Integer age, String ssn, Boolean busy) {
      this.driverID = new SimpleIntegerProperty(id);
      this.name = new SimpleStringProperty(name);
      this.email = new SimpleStringProperty(email);
      this.age = new SimpleStringProperty(age + " years");
      this.SSN = new SimpleStringProperty(ssn);
      this.busy = new SimpleBooleanProperty(busy);
    }

    public int getDriverID() { return driverID.get(); }
    public String getName() { return name.get(); }
    public String getEmail() { return email.get(); }
    public String getAge() { return age.get(); }
    public String getSSN() { return SSN.get(); }
    public boolean isBusy() { return busy.get(); }
  }
}