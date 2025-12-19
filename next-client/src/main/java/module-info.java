module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.logging;

    opens org.noteam.nextclient to javafx.fxml;
    opens org.noteam.nextclient.controller to javafx.fxml;
    opens org.noteam.nextclient.scene to javafx.fxml;
    opens org.noteam.nextclient.dto to com.fasterxml.jackson.databind;

    exports org.noteam.nextclient;
    exports org.noteam.nextclient.controller;
    exports org.noteam.nextclient.scene;
}

