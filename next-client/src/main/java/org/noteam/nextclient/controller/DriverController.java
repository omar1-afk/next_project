package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DriverController {
    @FXML
    private BorderPane driverPane;
    @FXML
    private AnchorPane AllPane;
    @FXML
    private AnchorPane NamePane;
    @FXML
    private AnchorPane AgePane;

    public BorderPane getDriverPane(){
        return driverPane;
    }
    protected void makeStateActive(AnchorPane statePane){
        if(!statePane.getStyle().equals("-fx-background-color: #F1F1F1; -fx-background-radius: 8px;")){
            deactivateAllStates();
            statePane.setStyle("-fx-background-color: #F1F1F1; -fx-background-radius: 8px;");
        }
    }
    protected void deactivateAllStates(){
        AllPane.setStyle("-fx-background-radius: 8px;");
        NamePane.setStyle("-fx-background-radius: 8px;");
        AgePane.setStyle("-fx-background-radius: 8px;");
    }

    @FXML
    public void getAllDrivers(){
        makeStateActive(AllPane);
    }
    @FXML
    public void getDriverByNane(){
        makeStateActive(NamePane);
    }
    @FXML
    public void getDriverByAge(){
        makeStateActive(AgePane);
    }

}
