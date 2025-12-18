package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpdateCarController {
    @FXML private TextField carTypeField;
    @FXML private TextField weightLimitField;
    @FXML private TextField carPlateField;

    @FXML
    private void handleUpdateCar() {
        System.out.println("--- Update Core/Car Data ---");
        System.out.println("Type: " + carTypeField.getText());
        System.out.println("Weight Limit: " + weightLimitField.getText());
        System.out.println("License Plate: " + carPlateField.getText());
        System.out.println("----------------------------");

        // Success message for the console
        if (!carPlateField.getText().isEmpty()) {
            System.out.println("Update Successful for Plate: " + carPlateField.getText());
        }
    }
}