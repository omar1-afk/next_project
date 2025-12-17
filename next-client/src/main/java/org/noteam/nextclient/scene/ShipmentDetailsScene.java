package org.noteam.nextclient.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShipmentDetailsScene extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxml = new FXMLLoader(
        ShipmentDetailsScene.class.getResource("shipmentDetails-scene.fxml"));
    Scene scene = new Scene(fxml.load(), 1280, 720);
    stage.setTitle("Shipment Details");
    stage.setScene(scene);
    stage.setFullScreen(false);
    stage.show();
  }

}
