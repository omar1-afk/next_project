module org.noteam.nextclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires java.desktop;
    requires com.fasterxml.jackson.core;
    requires com.google.gson;
    requires java.sql;
    requires javafx.base;
    requires spring.data.commons;
    requires io.github.cdimascio.dotenv.java;
    requires com.fasterxml.jackson.databind;
    requires org.noteam.nextclient;

    opens org.noteam.nextclient to javafx.fxml, com.google.gson;

    exports org.noteam.nextclient;

    opens org.noteam.nextclient.controller to javafx.fxml;

    exports org.noteam.nextclient.controller;

    opens org.noteam.nextclient.dto to com.google.gson;

    opens org.noteam.nextclient.scene to javafx.fxml;

    exports org.noteam.nextclient.scene;
}
