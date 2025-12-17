package org.noteam.nextclient.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class MainScene extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
    Scene scene = new Scene(root, 1440, 720);
    stage.setTitle("Next Application");
    String css = getClass().getResource("style.css").toExternalForm();
    // In Main.java
    Font.loadFont(getClass().getResourceAsStream("/org/noteam/nextclient/fonts/Roboto-Regular.ttf"), 12);
    scene.getStylesheets().add(css);
    Image icon = new Image(
        Objects.requireNonNull(getClass().getResourceAsStream("/org/noteam/nextclient/assets/logo.png")));
    stage.getIcons().add(icon);
    stage.setResizable(true);
    stage.setFullScreen(false);
    stage.setScene(scene);
    stage.show();
  }
}
