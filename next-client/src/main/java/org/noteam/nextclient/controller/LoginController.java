package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.noteam.nextclient.models.Employee;
import org.noteam.nextclient.scene.DataEntryWindow;
import org.noteam.nextclient.utils.ApiUtil;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;

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
        passwordField.textProperty().bindBidirectional(passwordVisibleField.textProperty());
        password = passwordField.getText();
        email = emailField.getText();
        if (password.length() <= 8 || email.length() <= 8) {
            alert("Email and/or password are incorrect");
            return;
        }

        new Thread(() -> {
            JsonObject data = new JsonObject();

            data.add("email", email);
            data.add("password", password);

            HttpURLConnection con = ApiUtil.fetchApi("/api/v1/login", ApiUtil.RequestMethod.POST, data);
        });
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
