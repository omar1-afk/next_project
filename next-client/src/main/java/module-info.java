module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires android.json;
    requires com.fasterxml.jackson.core;
    requires com.google.gson;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires io.github.cdimascio.dotenv.java;
    requires spring.web;
    requires spring.webflux;
    requires com.fasterxml.jackson.databind;

    opens org.noteam.nextclient to javafx.fxml;

    exports org.noteam.nextclient;

    opens org.noteam.nextclient.controller to javafx.fxml;

    exports org.noteam.nextclient.controller;

    opens org.noteam.nextclient.scene to javafx.fxml;

    exports org.noteam.nextclient.scene;
}
