package org.noteam.nextclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Path to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene/update-driver.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 830, 500);
        stage.setTitle("Update driver");

        // Path to the Logo in assets
        // Since Launcher is in 'nextclient' and assets is in 'nextclient/assets', we use:
        stage.getIcons().add(new Image(getClass().getResourceAsStream("assets/logo.png")));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}