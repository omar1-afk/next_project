package org.noteam.nextclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.noteam.nextclient.utils.ViewNavigator;
import org.noteam.nextclient.views.ShipmentView;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
      stage.setTitle("Next");
        ViewNavigator.setMainStage(stage);
        new ShipmentView("email.com").show();
        
    }
}
