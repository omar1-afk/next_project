module org.noteam.nextclient {
  requires javafx.controls;
  requires javafx.fxml;
  requires io.github.cdimascio.dotenv.java;
  requires java.net.http;
  requires com.fasterxml.jackson.databind;
  requires spring.webflux;
  requires spring.web;
  requires reactor.core;
  requires org.reactivestreams;

  opens org.noteam.nextclient to javafx.fxml;

  exports org.noteam.nextclient.scene to javafx.graphics;

  opens org.noteam.nextclient.entity to com.fasterxml.jackson.databind;

  exports org.noteam.nextclient;

  opens org.noteam.nextclient.controller to javafx.fxml;

  exports org.noteam.nextclient.controller;

  opens org.noteam.nextclient.scene to javafx.fxml;

  opens org.noteam.nextclient.assets to javafx.fxml;

}
