package org.noteam.nextclient;

import io.github.cdimascio.dotenv.Dotenv;
import org.noteam.nextclient.scene.LoginScene;

import javafx.application.Application;
import org.noteam.nextclient.scene.MainScene;

public class Launcher {
    public static void main(String[] args) {
        Application.launch(MainScene.class, args);
    }
}
