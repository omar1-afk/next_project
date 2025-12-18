package org.noteam.nextclient.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewNavigator {
    private static Stage mainStage;
   // public static Stage getMainStage() {
     //   return mainStage;
    //}
    public static void setMainStage(Stage mainStage) {
        ViewNavigator.mainStage = mainStage;
    }
    public static void switchViews( Scene scene ) {
        if(mainStage != null) {
            mainStage.setScene(scene);
            mainStage.show();
        }
    }
}
