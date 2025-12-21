package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UpdateDriverController {

    @FXML private TextField driverNameField;
    @FXML private TextField ssnField; // New
    @FXML private TextField driverEmailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordTextField;
    @FXML private TextField ageField; // New
    @FXML private TextField phoneField; // New
    @FXML private ImageView togglePasswordImage;

    private boolean isPasswordVisible = false;

    @FXML
    private void handleUpdateDriver() {
        System.out.println("--- Update Driver Data Received ---");
        System.out.println("Name: " + driverNameField.getText());
        System.out.println("SSN: " + ssnField.getText());
        System.out.println("Email: " + driverEmailField.getText());
        String pass = passwordField.isVisible() ? passwordField.getText() : passwordTextField.getText();
        System.out.println("Password: " + pass);

        System.out.println("Age: " + ageField.getText());
        System.out.println("Phone: " + phoneField.getText());
        System.out.println("------------------------------------");
    }
    @FXML
    private void togglePassword(MouseEvent event) {
        if (!isPasswordVisible) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            togglePasswordImage.setImage(new Image(getClass().getResourceAsStream("/org/noteam/nextclient/assets/eye.png")));
            isPasswordVisible = true;
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);
            togglePasswordImage.setImage(new Image(getClass().getResourceAsStream("/org/noteam/nextclient/assets/hidden.png")));
            isPasswordVisible = false;
        }
    }
}