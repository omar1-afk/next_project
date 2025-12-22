package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.noteam.nextclient.models.Vehicle;
import org.noteam.nextclient.utils.SqlUtil;

public class UpdateCarController {

    @FXML
    private TextField carTypeField;

    @FXML
    private TextField weightLimitField;

    @FXML
    private TextField carPlateField;

    private int currentVehicleId;
    private Vehicle vehicle;

    public void setVehicleData(Vehicle vehicle) {
        if (vehicle != null) {
            this.vehicle = vehicle;
            this.currentVehicleId = vehicle.getVehicleId();
            carTypeField.setText(vehicle.getVehicleType().toString());
            weightLimitField.setText(String.valueOf(vehicle.getWight()));
            carPlateField.setText(vehicle.getLicensePlate());
        }
    }

    @FXML
    private void handleUpdateCar() {
        try {
            String type = carTypeField.getText().trim().toUpperCase();

            String weightRaw = weightLimitField.getText().replaceAll("[^0-9]", "");
            int weight = Integer.parseInt(weightRaw);

            String plate = carPlateField.getText().trim();

            boolean success;
            if (vehicle == null) {
                success = SqlUtil.createVehicle(plate, weight, type);
            } else {
                success = SqlUtil.updateVehicle(vehicle.getVehicleId(), plate, weight, type);
            }

            if (success) {
                closeWindow();
            } else {
                System.err.println("Failed to save vehicle changes to the server.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: Weight must be a valid number.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) carTypeField.getScene().getWindow();
        stage.close();
    }
}
