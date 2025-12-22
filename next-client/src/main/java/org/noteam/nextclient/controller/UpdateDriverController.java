package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.noteam.nextclient.models.Driver;
import org.noteam.nextclient.utils.SqlUtil;

import java.util.Objects;

public class UpdateDriverController {

  @FXML private TextField driverNameField, ssnField, driverEmailField, ageField, phoneField, passwordTextField;
  @FXML private PasswordField passwordField;
  @FXML private ImageView togglePasswordImage;

  private int currentDriverId;
  private boolean isPasswordVisible = false;

  // 1. Method to receive data from the Main Table
  public void setDriverData(Driver driver) {
    this.currentDriverId = driver.getDriverId();
    driverNameField.setText(driver.getName());
    ssnField.setText(driver.getSocialSecurityNumber());
    driverEmailField.setText(driver.getEmail());
    ageField.setText(String.valueOf(driver.getAge()));
    passwordField.setText(driver.getPassword());
    passwordTextField.setText(driver.getPassword());
    // Note: Your model needs a phone field if you want to populate phoneField
  }

  // 2. Logic to send data to the Server
  @FXML
  private void handleUpdateDriver() {
    String name = driverNameField.getText();
    int age = Integer.parseInt(ageField.getText());
    String email = driverEmailField.getText();
    String ssn = ssnField.getText();
    String password = isPasswordVisible ? passwordTextField.getText() : passwordField.getText();

    // We call SqlUtil to perform the HTTP PUT request
    boolean success = SqlUtil.updateDriver(currentDriverId, name, age, email, password, ssn);

    if (success) {
      closeWindow();
    } else {
      System.err.println("Update failed check server logs.");
    }
  }

  @FXML
  private void togglePassword() {
    isPasswordVisible = !isPasswordVisible;
    if (isPasswordVisible) {
      passwordTextField.setText(passwordField.getText());
      passwordTextField.setVisible(true);
      passwordField.setVisible(false);
      togglePasswordImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/noteam/nextclient/assets/eye.png"))));
    } else {
      passwordField.setText(passwordTextField.getText());
      passwordField.setVisible(true);
      passwordTextField.setVisible(false);
      togglePasswordImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/noteam/nextclient/assets/hidden.png"))));
    }
  }

  private void closeWindow() {
    Stage stage = (Stage) driverNameField.getScene().getWindow();
    stage.close();
  }
}