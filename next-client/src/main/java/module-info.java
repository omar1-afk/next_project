module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jsobject;
    requires java.desktop;
    requires android.json;
    requires com.fasterxml.jackson.core;
    requires com.google.gson;


    opens org.noteam.nextclient to javafx.fxml;
    exports org.noteam.nextclient;
}