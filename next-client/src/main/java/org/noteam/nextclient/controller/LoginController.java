package org.noteam.nextclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.noteam.nextclient.Config;
import org.noteam.nextclient.dto.LoginResponse;
import org.noteam.nextclient.entity.Employee;
import org.noteam.nextclient.scene.DataEntryWindow;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

public class LoginController {
  @FXML
  private StackPane passwordFieldsStack;
  @FXML
  private javafx.scene.control.TextField emailField;

  private String email = "";

  @FXML
  private PasswordField passwordField;

  private String password = "";

  private void alert(String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Failed to login");
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  private Button loginButton;

  @FXML
  protected void login() {

    System.out.println("SSSS");
    class Data {
      private String name;
      private int age;
    }
    Stage primaryStage = (Stage) loginButton.getScene().getWindow();
    DataEntryWindow<Data> window = new DataEntryWindow<>("Create new Data", primaryStage);
    window.showAndWaitForResult();

    return;
    // passwordField.textProperty().bindBidirectional(passwordVisibleField.textProperty());
    // password = passwordField.getText();
    // email = emailField.getText();
    // if (password.length() <= 8 || email.length() <= 8) {
    // alert("Email and/or password are incorrect");
    // return;
    // }
    // new Thread(() -> {
    // System.out.println("SSSS");
    // Employee credentials = new Employee(email, password);
    // WebClient client = WebClient.builder().baseUrl(Config.SERVER_URL).build();
    // client.post()
    // .uri("/login")
    // .bodyValue(credentials)
    // .retrieve()
    // .bodyToMono(LoginResponse.class)
    // .subscribe(res -> {
    // javafx.application.Platform.runLater(() -> {
    // System.out.println("Login Success: " + res);
    // // TODO: Switch the scenes
    // alert("GOOOOOOOOD");
    // System.out.println(res.getToken());
    // });
    // },
    // error -> {
    // System.out.println(error);
    // }
    // );

    // });
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
