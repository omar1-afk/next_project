module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.noteam.nextclient to javafx.fxml;
    exports org.noteam.nextclient.scene to javafx.graphics;
    opens org.noteam.nextclient.controller to javafx.fxml;
    exports org.noteam.nextclient;
}