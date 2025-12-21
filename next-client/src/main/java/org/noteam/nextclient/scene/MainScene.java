package org.noteam.nextclient.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class MainScene extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene=new Scene(root,1440,720);
        stage.setTitle("Next Application");
        String css = getClass().getResource("style.css").toExternalForm();
        // In Main.java
        scene.getStylesheets().add(css);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/noteam/nextclient/assets/logo.png")));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.F11),
                () -> {
                    if(stage.isFullScreen()){
                        stage.setFullScreen(false);
                    }
                    else {
                        stage.setFullScreen(true);
                    }
                }
        );
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
}