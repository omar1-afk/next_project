module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;
    opens org.noteam.nextclient to javafx.fxml;
    opens org.noteam.nextclient.controller to javafx.fxml;
    opens org.noteam.nextclient.scene to javafx.fxml;
    exports org.noteam.nextclient;
    exports org.noteam.nextclient.controller;
    exports org.noteam.nextclient.scene;
}