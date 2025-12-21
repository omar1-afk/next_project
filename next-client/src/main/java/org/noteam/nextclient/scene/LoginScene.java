package org.noteam.nextclient.scene;

import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;
import org.noteam.nextclient.Config;
import org.noteam.nextclient.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScene extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxml = new FXMLLoader(
                LoginScene.class.getResource("login-scene.fxml"));
        Scene scene = new Scene(fxml.load(), 1280, 720);
        // scene.getStylesheets().add(LoginScene.class.getResource("style.css").toString());
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }
}
