package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class AlertHandler {

    private SceneHandler sceneHandler;
    private LanguageHandler languageHandler;

    private AlertHandler(){}
    private static final AlertHandler instance = new AlertHandler();
    public static AlertHandler getInstance(){return instance;}


    // Creazione dei vari alert
    public void createErrorAlert(String message) {
        languageHandler = LanguageHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        FontIcon icon = new FontIcon("mdi2a-alert");
        icon.setIconColor(Paint.valueOf("#ff3333")); // Rosso
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle(languageHandler.getBundle().getString("errorTitle"));
        alert.setContentText(message);
        alert.show();
    }
    public void createLoginAlert() {
        sceneHandler = SceneHandler.getInstance();
        languageHandler = LanguageHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2c-check-circle-outline");
        icon.setIconColor(Paint.valueOf("#4d79ff")); // Blu
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Login");
        alert.setContentText(languageHandler.getBundle().getString("loginText"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) sceneHandler.createSideBar();
    }

    public void createRegistrationAlert(){
        languageHandler = LanguageHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setTitle(languageHandler.getBundle().getString("informationTitle"));
        alert.setContentText(languageHandler.getBundle().getString("registrationText"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) sceneHandler.createLoginScene();
    }

    public void createForgotPassAlert(String message){
        languageHandler = LanguageHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle(languageHandler.getBundle().getString("changePasswordTitle"));
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) sceneHandler.createChangePasswordFromForgot();
    }

    public void createLogoutAlert(String message){
        SqlService sqlService = SqlService.getInstance();
        LoggedHandler loggedHandler = LoggedHandler.getInstance();
        sceneHandler = SceneHandler.getInstance();
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
            sqlService.serviceLogout(LoginController.username);
            loggedHandler.stayLoggedWriting("null");
            sceneHandler.createLoginScene();
        }
        else if(result.get() == ButtonType.CANCEL) alert.close();
    }

    public void createChangedAlert(String details){
        languageHandler = LanguageHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle(languageHandler.getBundle().getString("changeTitle") + details);
        alert.setContentText(details + languageHandler.getBundle().getString("changeSuccessfulText"));
        alert.show();
    }

    public void passChangedFromForgot() {
        languageHandler = LanguageHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle(languageHandler.getBundle().getString("changePasswordTitle"));
        alert.setContentText(languageHandler.getBundle().getString("changePasswordText"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) sceneHandler.createLoginScene();
        else if(result.get() == ButtonType.OK) sceneHandler.createLoginScene();
    }

    public void restartAppAlert() {
        languageHandler = LanguageHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle(languageHandler.getBundle().getString("restartAppTitle"));
        alert.setContentText(languageHandler.getBundle().getString("restartAppText"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
        else if(result.get() == ButtonType.OK) System.exit(0);
    }

    public void createRestartAppAlertLan(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle(title);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
        else if(result.get() == ButtonType.OK) System.exit(0);
    }
}
