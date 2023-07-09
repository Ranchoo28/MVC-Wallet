package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class AlertHandler {

    private AlertHandler(){}
    private static final AlertHandler instance = new AlertHandler();
    public static AlertHandler getInstance(){return instance;}

    private final SceneHandler sceneHandler = SceneHandler.getInstance();

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
        else if(result.get() == ButtonType.OK) sceneHandler.createSideBar();
    }

    public void createRegistrationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle("Informazione");
        alert.setContentText("Registrazione effettuata! Puoi effettuare il login.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) sceneHandler.createLoginScene();
    }

    public void createForgotPassAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Cambio password");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) sceneHandler.createChangePasswordFromForgot();
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
        else if(result.get() == ButtonType.OK){
            SqlService.getIstance().serviceLogout(LoginController.username);
            LoggedHandler.getInstance().stayLoggedWriting("null");
            sceneHandler.createLoginScene();
        }
        else if(result.get() == ButtonType.CANCEL) alert.close();
    }

    public void createChangedAlert(String details){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle("Cambio "+ details);
        alert.setContentText(details+ " cambiato con successo!");
        alert.show();
    }

    public void passChangedFromForgot() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Cambio password");
        alert.setContentText("Password cambiata con successo! Ora puoi effettuare il login.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) sceneHandler.createLoginScene();
        else if(result.get() == ButtonType.OK) sceneHandler.createLoginScene();
    }

    public void restartAppAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Riavvia applicazione");
        alert.setContentText("Riavvia l'applicazione per il cambio delle impostazioni");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
        else if(result.get() == ButtonType.OK) System.exit(0);
    }
}
