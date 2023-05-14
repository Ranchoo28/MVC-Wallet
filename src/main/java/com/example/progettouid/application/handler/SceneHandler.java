package com.example.progettouid.application.handler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Optional;

public class SceneHandler {

    private static final SceneHandler instance = new SceneHandler();
    private Stage stage;
    private Scene scene;

    public static SceneHandler getInstance() {
        return instance;
    }

    private SceneHandler() {}

    public void init(Stage stage) {
        // Crea lo stage iniziale
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle("Wallet Login");
            createLoginScene();
            this.stage.setScene(scene);
            this.stage.show();
        }
    }

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource(resourceName));
        return fxmlLoader.load();
    }

    public void createSideBar() {
        // Crea la scena per l'homepage
        try {
            stage.setTitle("MVC Wallet");
            scene.setRoot(loadRootFromFXML("/com/example/progettouid/sidebar-view.fxml"));
            stage.setMinWidth(650);
            stage.setMinHeight(500);
            stage.setWidth(650);
            stage.setHeight(500);
            stage.setResizable(true);
        } catch (IOException ignored) {
        }
    }

    public void createLoginScene() {
        // Crea la scena del login
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML("/com/example/progettouid/login-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML("/com/example/progettouid/login-view.fxml"));
            stage.setTitle("MVC Wallet login");
            stage.setMinWidth(600);
            stage.setMinHeight(350);
            stage.setWidth(600);
            stage.setHeight(350);
            stage.setResizable(false);
        } catch (IOException ignored) {
        }
    }

    public void createRegistrationScene() {
        // Crea la scena della registrazione
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML("/com/example/progettouid/register-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML("/com/example/progettouid/register-view.fxml"));
            stage.setTitle("MVC Wallet registrazione");
            stage.setMinWidth(600);
            stage.setMinHeight(450);
            stage.setWidth(600);
            stage.setHeight(450);
            stage.setResizable(false);
        } catch (IOException ignored) {
        }
    }

    public void createForgotPasswordScene() {
        // Crea la scena per la password dimenticata
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML("/com/example/progettouid/forgot-pass.fxml"));
            else
                scene.setRoot(loadRootFromFXML("/com/example/progettouid/forgot-pass.fxml"));
            stage.setTitle("MVC Wallet password dimenticata");
            stage.setMinWidth(400);
            stage.setMinHeight(250);
            stage.setWidth(400);
            stage.setHeight(250);
            stage.setResizable(false);
        } catch (IOException ignored) {
        }
    }

    public void createSettingsScene() {
        // Crea la scena per la password dimenticata
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML("/com/example/progettouid/settings-view.fxml"));
            else
                scene.setRoot(loadRootFromFXML("/com/example/progettouid/settings-view.fxml"));
            stage.setTitle("MVC Wallet impostazioni");
            stage.setMinWidth(600);
            stage.setMinHeight(450);
            stage.setWidth(600);
            stage.setHeight(450);
            stage.setResizable(false);
        } catch (IOException ignored) {
        }
    }


    // Creazione dei vari alert
    public void createErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        FontIcon icon = new FontIcon("mdi2a-alert");
        icon.setIconColor(Paint.valueOf("#ff3333")); // Rosso
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle("Errore");
        alert.setContentText(message);
        alert.show();
    }
    public void createLoginAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2c-check-circle-outline");
        icon.setIconColor(Paint.valueOf("#4d79ff")); // Blu
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Login eseguito!");
        alert.setContentText("Accesso avvenuto.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createSideBar();
    }

    public void createForgotPassAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Informazione");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createLoginScene();
    }

    public void createLogoutAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        FontIcon icon = new FontIcon("mdi2p-progress-question");
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Logut");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) createLoginScene();
        else if(result.get() == ButtonType.CANCEL) alert.close();
    }
}
