package com.example.progettouid.application;

import com.example.progettouid.application.handler.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneHandler.getInstance().init(stage);
        //SceneHandler.getInstance().createLoginScene();

        // Aggiunto solo per i test
        SceneHandler.getInstance().createSideBar();
    }

    public static void main(String[] args) {
        launch();
    }
}