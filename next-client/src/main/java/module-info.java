module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.noteam.nextclient to javafx.fxml;
    exports org.noteam.nextclient;
    opens org.noteam.nextclient.controller to javafx.fxml;
    exports org.noteam.nextclient.controller;
    opens org.noteam.nextclient.scene to javafx.fxml;
    exports org.noteam.nextclient.scene;
}