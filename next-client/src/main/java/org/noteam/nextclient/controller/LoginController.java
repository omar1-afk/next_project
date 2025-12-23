package org.noteam.nextclient.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.noteam.nextclient.Config;
import org.noteam.nextclient.dto.LoginResponse;
import org.noteam.nextclient.models.Employee;
import org.noteam.nextclient.scene.DataEntryWindow;
import org.noteam.nextclient.utils.ApiUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class LoginController {
    private static Logger log = Logger.getLogger(LoginController.class.getName());
    @FXML
    private StackPane passwordFieldsStack;
    @FXML
    private javafx.scene.control.TextField emailField;

    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

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
        log.severe("______________STARTING____________");
        passwordField.textProperty().bindBidirectional(passwordVisibleField.textProperty());
        password = passwordField.getText();
        email = emailField.getText();
        if (password.length() <= 4 || email.length() <= 4) {
            alert("Email and/or password are incorrect");
            return;
        }

        Platform.runLater(() -> {
            JsonObject data = new JsonObject();
            log.info("Config.Token " + Config.TOKEN);

            data.addProperty("email", email);
            data.addProperty("password", password);
            log.severe(data.toString());
            try {
                HttpURLConnection con = ApiUtil.fetchApi("/api/v1/login",
                        ApiUtil.RequestMethod.POST, data);
                if (con == null)
                    return;
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String body = ApiUtil.readResponse(con);
                    Gson gson = new Gson();
                    LoginResponse res = gson.fromJson(body, LoginResponse.class);
                    ApiUtil.setToken(res.getToken());
                    switchToDashboard();
                }
                log.info("" + con.getResponseCode());

            } catch (IOException e) {
                log.severe(e.toString());
            }
            log.info("Config.Token " + Config.TOKEN);
        });
    }

    private void switchToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/noteam/nextclient/scene/main-view.fxml"));
            Parent root = loader.load();
            MainController shipmentController = loader.getController();
            Scene scene = new Scene(root, 1440, 720);
          scene.getAccelerators().put(
            new KeyCodeCombination(KeyCode.F11),
            () -> {
              if (mainStage.isFullScreen()) {
                mainStage.setFullScreen(false);
              } else {
                mainStage.setFullScreen(true);
              }
            });
            mainStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
