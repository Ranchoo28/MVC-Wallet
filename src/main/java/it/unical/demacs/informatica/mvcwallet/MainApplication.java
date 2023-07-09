package it.unical.demacs.informatica.mvcwallet;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    Stage st = new Stage();
    SceneHandler sceneHandler = SceneHandler.getInstance();

    @Override
    public void start(Stage stage) {
        //stage.setResizable(false);
        sceneHandler.init(this.st);
        sceneHandler.createLoginScene();

        // Aggiunto solo per i test
        //sceneHandler.createSideBar();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void restartApplication() {
        //sceneHandler.init(st);

        /*
        // Avvia un nuovo processo per avviare un'altra istanza dell'applicazione
        String javaPath = System.getProperty("java.home") + "/bin/java";
        String classpath = System.getProperty("java.class.path");
        String className = MainApplication.class.getName();
        //final String command = "java -jar " + currentJar.getPath();
        ProcessBuilder processBuilder = new ProcessBuilder(javaPath, "-cp", classpath, className);
        try {

           processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Termina il processo corrente
        System.exit(0);

         */
    }
}

