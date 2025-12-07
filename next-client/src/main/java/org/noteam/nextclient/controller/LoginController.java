package org.noteam.nextclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class LoginController {
  @FXML
  private StackPane passwordFieldsStack;
  @FXML
  private javafx.scene.control.TextField emailField;

  private String email = "";


  @FXML
  private PasswordField passwordField;

  private String password = "";


  @FXML
  protected void login(ActionEvent e) {
    password = passwordField.getText();
    email = emailField.getText();
    System.out.println(password + email);
    if (!(password.length() > 0 || email.length() > 0)) {
      System.out.println("ERROR");
      return;
    }
    System.out.println("HEELo");
  }
  @FXML
  private Group passwordToggleGroup;
  @FXML
  private ToggleButton toggleVisibilityButton;
  @FXML
  private TextField passwordVisibleField;
  @FXML
  private ImageView showPasswordImage;
  @FXML
  private ImageView hidePasswordImage;
  private boolean visiblePassword = false;
  @FXML
  protected void togglePasswordVisibility() {
    visiblePassword = !visiblePassword;
    showPasswordImage.setVisible(!visiblePassword);
    hidePasswordImage.setVisible(visiblePassword);
    passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());
    if (visiblePassword) {
      passwordVisibleField.setManaged(true);
      passwordVisibleField.setVisible(true);
      passwordVisibleField.setMouseTransparent(false);
      passwordField.setVisible(false);
      passwordField.setManaged(false);
      passwordField.setMouseTransparent(true);
      passwordVisibleField.requestFocus();
    } else {
      passwordVisibleField.setManaged(false);
      passwordVisibleField.setVisible(false);
      passwordVisibleField.setMouseTransparent(true);
      passwordField.setVisible(true);
      passwordField.setManaged(true);
      passwordField.setMouseTransparent(false);
      passwordField.requestFocus();
    }
    passwordToggleGroup.toFront();
  }
}
