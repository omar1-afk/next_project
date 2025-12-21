package org.noteam.nextclient.utils;

import javafx.scene.control.Alert;

public class Utilities {
    public static final int APP_WIDTH = 1614;
    public static final int APP_HEIGHT = 900;
    public static void ShowAlertDialog(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
       // alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
