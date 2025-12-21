package org.noteam.nextclient.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class DriverController {
    @FXML
    private BorderPane driverPane;
    @FXML
    private AnchorPane AllPane;
    @FXML
    private AnchorPane NamePane;
    @FXML
    private AnchorPane AgePane;

    @FXML
    private TableView<DriverController.DriverRow> driverTable;
    @FXML
    private TableColumn<DriverController.DriverRow, Integer> DriverIDCol;
    @FXML
    private TableColumn<DriverController.DriverRow, Double> DriverNameCol;
    @FXML
    private TableColumn<DriverController.DriverRow, String> EmailCol;
    @FXML
    private TableColumn<DriverController.DriverRow, String> AgeCol;
    @FXML
    private TableColumn<DriverController.DriverRow, Double> SCNCol;
    @FXML
    private TableColumn<DriverController.DriverRow, String> BusyCol;

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        List<Driver> driverList = Arrays.asList();
//        setDriverTableView(driverList);
//    }

//    public void setDriverTableView(List<Driver> driverList){
//        DriverIDCol.setCellValueFactory(new PropertyValueFactory<>("driverID"));
//        DriverNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        AgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
//        SCNCol.setCellValueFactory(new PropertyValueFactory<>("SCN"));
//        BusyCol.setCellValueFactory(new PropertyValueFactory<>("isbusy"));
//        observableList = FXCollections.observableArrayList();
//        driverList.forEach(driver->{observableList.add(new DriverRow(driver.id(), driver.name() , driver.email() , driver.age() , driver.SCN() , driver.isbusy() ));})
//        driverTable.setItems(observableList);
//        System.out.println(driverTable.getItems());


    public class DriverRow {
        SimpleIntegerProperty driverID;
        SimpleStringProperty name;
        SimpleStringProperty email;
        SimpleIntegerProperty age;
        SimpleStringProperty SCN;
        SimpleBooleanProperty isbusy;

        public DriverRow(Integer driverID, String name, String email, Integer age, String SCN, Boolean isbusy) {
            this.driverID = new SimpleIntegerProperty(driverID);
            this.name = new SimpleStringProperty(name);
            this.email = new SimpleStringProperty(email);
            this.age = new SimpleIntegerProperty(age);
            this.SCN = new SimpleStringProperty(SCN);
            this.isbusy = new SimpleBooleanProperty(isbusy);
        }

        public int getDriverID() {
            return driverID.get();
        }

        public SimpleIntegerProperty driverIDProperty() {
            return driverID;
        }

        public void setDriverID(int driverID) {
            this.driverID.set(driverID);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getEmail() {
            return email.get();
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public int getAge() {
            return age.get();
        }

        public SimpleIntegerProperty ageProperty() {
            return age;
        }

        public void setAge(int age) {
            this.age.set(age);
        }

        public String getSCN() {
            return SCN.get();
        }

        public SimpleStringProperty SCNProperty() {
            return SCN;
        }

        public void setSCN(String SCN) {
            this.SCN.set(SCN);
        }

        public boolean isBusy() {
            return isbusy.get();
        }

        public SimpleBooleanProperty busyProperty() {
            return isbusy;
        }

        public void setBusy(boolean isbusy) {
            this.isbusy.set(isbusy);
        }
    }

    public BorderPane getDriverPane() {
        return driverPane;
    }

    protected void makeStateActive(AnchorPane statePane) {
        if (!statePane.getStyle().equals("-fx-background-color: #F1F1F1; -fx-background-radius: 8px;")) {
            deactivateAllStates();
            statePane.setStyle("-fx-background-color: #F1F1F1; -fx-background-radius: 8px;");
        }
    }

    protected void deactivateAllStates() {
        AllPane.setStyle("-fx-background-radius: 8px;");
        NamePane.setStyle("-fx-background-radius: 8px;");
        AgePane.setStyle("-fx-background-radius: 8px;");
    }

    @FXML
    public void getAllDrivers() {
        makeStateActive(AllPane);
    }

    @FXML
    public void getDriverByNane() {
        makeStateActive(NamePane);
    }

    @FXML
    public void getDriverByAge() {
        makeStateActive(AgePane);
    }
}


