package org.noteam.nextclient.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.noteam.nextclient.models.Driver;
import org.noteam.nextclient.utils.SqlUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DriverController implements Initializable {

  // UI Layout Components ------
  @FXML
  private BorderPane driverPane;
  @FXML
  private Label DriverNumLabel;
  @FXML
  private TextField searchFeild;
  @FXML
  private AnchorPane AllPane;
  @FXML
  private AnchorPane NamePane;
  @FXML
  private AnchorPane AgePane;

  // Table Components ------
  @FXML
  private TableView<DriverRow> DriverTable;
  @FXML
  private TableColumn<DriverRow, Integer> DriverIDCol;
  @FXML
  private TableColumn<DriverRow, String> DriverNameCol;
  @FXML
  private TableColumn<DriverRow, String> EmailCol;
  @FXML
  private TableColumn<DriverRow, String> AgeCol;
  @FXML
  private TableColumn<DriverRow, String> SSNCol;
  @FXML
  private TableColumn<DriverRow, Boolean> BusyCol;

  private ObservableList<DriverRow> observableList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    List<Driver> driverList = SqlUtil.getAllDrivers();
    setDriverTableView(driverList);

//    testing -------------------------
//    List<Driver> driverList = new ArrayList<>();
//    driverList.add(new Driver(1, "Omar", "", 25, "123456", "omar@test.com", "123", false));
//    setDriverTableView(driverList);
  }


  public void setDriverTableView(List<Driver> driverList) {
    // Mapping Table Columns to DriverRow getters
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

  // --- Tab Switching Methods (Matches FXML #onMouseClicked) ---

  @FXML
  public void getAllDrivers() {
    makeStateActive(AllPane);
//    --------------------------
  }

  @FXML
  public void getDriverByNane() {
    makeStateActive(NamePane);
//    --------------------------
  }

  @FXML
  public void getDriverByAge() {
    makeStateActive(AgePane);
//    --------------------------
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

  public BorderPane getDriverPane() {
    return driverPane;
  }


  public static class DriverRow {
    private final SimpleIntegerProperty driverID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty age;
    private final SimpleStringProperty SSN;
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