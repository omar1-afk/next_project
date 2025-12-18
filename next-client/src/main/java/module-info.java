module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jsobject;
    requires java.desktop;
    requires android.json;
    requires com.fasterxml.jackson.core;
    requires com.google.gson;
    requires java.sql;
    requires javafx.base;
    requires spring.data.commons;


    opens org.noteam.nextclient to javafx.fxml;
    exports org.noteam.nextclient;
    opens org.noteam.nextclient.controller to javafx.fxml;
    exports org.noteam.nextclient.controller;
    opens org.noteam.nextclient.scene to javafx.fxml;
    exports org.noteam.nextclient.scene;
}