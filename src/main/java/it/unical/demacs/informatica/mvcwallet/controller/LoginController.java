package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;
    @FXML
    private Button buttonLogin = new Button();
    @FXML
    private CheckBox stayLogged;

    public static String username;
    private boolean isGoodUsername;
    private boolean isGoodPassword;

    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler lanHandler = LanguageHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final LoggedHandler loggedHandler = LoggedHandler.getInstance();
    private final SettingsHandler settingsHandler = SettingsHandler.getInstance();

    @FXML
    void onLoginButtonClick() throws InterruptedException {

        // Una volta premuto il button, esegue il login tramite una query al database e
        // in base al risultato apre un popup.


        if(sqlService.serviceLogin(Username.getText(), Password.getText()) == 0){
            username = Username.getText();
            if(stayLogged.isSelected()){
                sqlHandler.stayLoggedOfLogin(username);
                loggedHandler.stayLoggedWriting(username);
            }
            settingsHandler.updateSettings();
            alertHandler.createLoginAlert();
            //for(String i: settingsHandler.settings) System.out.println("ciao" + i);
        }else if(sqlService.serviceLogin(Username.getText(), Password.getText()) == 1)
            alertHandler.createErrorAlert(lanHandler.getBundle().getString("loginErrorUsernameText"));
        else if(sqlService.serviceLogin(Username.getText(), Password.getText()) == 2)
            alertHandler.createErrorAlert(lanHandler.getBundle().getString("loginErrorPassText"));
        else if (sqlService.serviceLogin(Username.getText(), Password.getText()) == 3) {
            alertHandler.createErrorAlert(lanHandler.getBundle().getString("loginErrorAllText"));
        }

        //sceneHandler.createSideBar();
    }

    @FXML
    void onRegisterButtonClick() {
        sceneHandler.createRegistrationScene();
    }
    @FXML
    void onForgotPasswordClick() { sceneHandler.createForgotPasswordScene(); }

    @FXML
    void initialize(){

        Platform.runLater(() -> {
            if (!loggedHandler.stayLoggedReading().equals("null")) {
                // Non capisco perchè bisogna rifare la connessione.
                username = loggedHandler.stayLoggedReading();
                sqlHandler.newConnection();
                settingsHandler.updateSettings();
                sceneHandler.createSideBar();
            }
        });

        buttonLogin.setDisable(true);
        Username.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodUsername = newValue.length() >= 5;
            performBinding();
        });

        Password.textProperty().addListener((observable, oldValue, newValue) -> {
            // cambiare ad 8 (lasciare 4 per i test)
            isGoodPassword = newValue.length() >= 4;
            performBinding();
        });

    }

    private void performBinding() {
        // Serve a disabilitare il button del login qualora non venissero introdotte credenziali valide
        // durante il login. Il runLater() serve ad assicuraci che questo codice venga eseguito
        // solamente dopo aver scritto nei vari textField.
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(
                            Username.textProperty(),
                            Password.textProperty()
                    );
                }

                @Override
                protected boolean computeValue() {
                    return !(isGoodUsername && isGoodPassword);
                }
            };

            buttonLogin.disableProperty().bind(bb);
        });

    }
}
