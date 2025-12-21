package org.noteam.nextclient.views;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.noteam.nextclient.utils.Utilities;
import org.noteam.nextclient.utils.ViewNavigator;

public class ShipmentView {
    private String email;
    private Label hellowLabel , greatingsLabel;


    public ShipmentView(String email) {
        this.email = email;
        hellowLabel = new Label();
        greatingsLabel = new Label("Good Morning!");

    }
    public void show(){
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        ViewNavigator.switchViews(scene);

    }
    private Scene createScene(){
        VBox shipmentVBox = new VBox();
        shipmentVBox.getStyleClass().addAll("main-background");
        VBox sidepaneVBox = new VBox();
        VBox mainVBox = new VBox();
        HBox buttonsHBox = new HBox();
        HBox srearchHBox = new HBox();
        VBox tableVBox = new VBox();

        return new Scene(shipmentVBox , Utilities.APP_WIDTH , Utilities.APP_HEIGHT);
    }
}
