package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.noteam.nextclient.utils.SqlUtil;
import java.util.Objects;

public class CreateDriverController {

  @FXML
  private TextField driverNameField, ssnField, driverEmailField, ageField, passwordTextField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private ImageView togglePasswordImage;

  private boolean isPasswordVisible = false;

  @FXML
  private void handleCreateDriver() {
    try {
      String name = driverNameField.getText();
      int age = Integer.parseInt(ageField.getText());
      String email = driverEmailField.getText();
      String ssn = ssnField.getText();
      String password = isPasswordVisible ? passwordTextField.getText() : passwordField.getText();

      //POST method in SqlUtil
      boolean success = SqlUtil.createNewDriver(name, age, email, password, ssn);

      if (success) {
        closeWindow();
      }
    } catch (NumberFormatException e) {
      System.err.println("Please enter a valid number for age.");
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