package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.boot.availability.ReadinessState;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AlertHandler {

    private SceneHandler sceneHandler;
    private ResourceBundle bundle;

    private AlertHandler(){}
    private static final AlertHandler instance = new AlertHandler();
    public static AlertHandler getInstance(){ return instance; }


    // Creazione dei vari alert
    public void createErrorAlert(String message) {
        bundle = LanguageHandler.getInstance().getBundle();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        FontIcon icon = new FontIcon("mdi2a-alert");
        icon.setIconColor(Paint.valueOf("#ff3333")); // Rosso
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle(bundle.getString("errorTitle"));
        alert.setContentText(message);
        alert.show();
    }

    public void createLoginAlert() {
        sceneHandler = SceneHandler.getInstance();
        bundle = LanguageHandler.getInstance().getBundle();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2c-check-circle-outline");
        icon.setIconColor(Paint.valueOf("#4d79ff")); // Blu
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle("Login");
        alert.setContentText(bundle.getString("loginText"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) sceneHandler.createSideBar();
    }

    public void createRegistrationAlert(){
        bundle = LanguageHandler.getInstance().getBundle();
        sceneHandler = SceneHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setTitle(bundle.getString("informationTitle"));
        alert.setContentText(bundle.getString("registrationText"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) alert.close();
        else if(result.get() == ButtonType.OK) sceneHandler.createLoginScene();
    }

    public void createForgotPassAlert(String message){
        bundle = LanguageHandler.getInstance().getBundle();
        sceneHandler = SceneHandler.getInstance();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle(bundle.getString("changePasswordTitle"));
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) sceneHandler.createChangePasswordFromForgot();
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

    public void createChangedAlert(){
        bundle = LanguageHandler.getInstance().getBundle();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2s-send-check");
        icon.setIconColor(Paint.valueOf("blue"));
        icon.getStyleClass().add("icons-color");
        icon.setIconSize(45);
        alert.setHeaderText("");
        alert.setGraphic(icon);
        alert.setTitle(bundle.getString("changeTitle"));
        alert.setContentText(bundle.getString("changeSuccessfulText"));
        alert.show();
    }

    public void passChangedFromForgotAlert() {
        bundle = LanguageHandler.getInstance().getBundle();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle(bundle.getString("changePasswordTitle"));
        alert.setContentText(bundle.getString("changePasswordText"));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) sceneHandler.createLoginScene();
        else if(result.get() == ButtonType.OK) sceneHandler.createLoginScene();
    }

    public void exitClickAlert(){
        bundle = LanguageHandler.getInstance().getBundle();
        ButtonType exitB = new ButtonType(bundle.getString("yesButton"));
        ButtonType stayB = new ButtonType(bundle.getString("noButton"));

        Alert alert = new Alert(Alert.AlertType.ERROR, "", exitB, stayB);
        FontIcon icon = new FontIcon("mdi2e-email-send");
        icon.getStyleClass().add("icons-color");
        icon.setIconColor(Paint.valueOf("#4d79ff"));
        icon.setIconSize(45);
        alert.setGraphic(icon);
        alert.setHeaderText("");
        alert.setTitle(bundle.getString("exitAppTitle"));
        alert.setContentText(bundle.getString("exitAppText"));
        alert.setOnCloseRequest(e -> alert.hide());
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            if(buttonType.equals(exitB)){
                Platform.exit();
                System.exit(0);
            }
            else if(buttonType.equals(stayB)){
                alert.close();
            }
        });
        alert.setOnCloseRequest(event -> {
            event.consume();
            alert.close();
        });
    }

}
