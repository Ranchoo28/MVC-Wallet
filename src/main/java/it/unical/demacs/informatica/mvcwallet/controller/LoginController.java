package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private Label languageLabel;
    @FXML
    private TextField usernameText, passwordText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton = new Button(), forgotPassButton = new Button(), registerButton = new Button();
    @FXML
    private CheckBox stayLogged;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private RadioMenuItem italianLanguage, englishLanguage, frenchLanguage, spanishLanguage, cosentinoLanguage;

    public static String username;
    private boolean isGoodUsername;
    private boolean isGoodPassword;

    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final LoggedHandler loggedHandler = LoggedHandler.getInstance();
    private final SettingsHandler settingsHandler = SettingsHandler.getInstance();
    private final LanguageHandler lanHandler = LanguageHandler.getInstance();

    @FXML
    void onLoginButtonClick() throws InterruptedException {

        // Una volta premuto il button, esegue il login tramite una query al database e
        // in base al risultato apre un popup.

        if(sqlService.serviceLogin(usernameText.getText(), passwordField.getText()) == 0){
            username = usernameText.getText();
            if(stayLogged.isSelected()){
                sqlHandler.stayLoggedOfLogin(username);
                loggedHandler.stayLoggedWriting(username);
            }
            settingsHandler.updateSettings();
            alertHandler.createLoginAlert();
        }else
            if(sqlService.serviceLogin(usernameText.getText(), passwordField.getText()) == 1)
                alertHandler.createErrorAlert(bundle.getString("loginErrorUsernameText"));
        else
            if(sqlService.serviceLogin(usernameText.getText(), passwordField.getText()) == 2)
                alertHandler.createErrorAlert(bundle.getString("loginErrorPassText"));
        else
            if (sqlService.serviceLogin(usernameText.getText(), passwordField.getText()) == 3) {
                alertHandler.createErrorAlert(bundle.getString("loginErrorAllText"));
        }
    }

    @FXML
    void onForgotPasswordClick() { sceneHandler.createForgotPasswordScene(); }

    @FXML
    void onItalianClick(){
        italianLanguageChoosen();
        changeLanguage();
        SceneHandler.getInstance().createLoginScene();
    }
    @FXML
    void onEnglishClick(){
        englishLanguageChoosen();
        changeLanguage();
        SceneHandler.getInstance().createLoginScene();
    }
    @FXML
    void onFrenchClick(){
        frenchLanguageChoosen();
        changeLanguage();
        SceneHandler.getInstance().createLoginScene();

    }

    @FXML
    void onSpanishClick(){
        spanishLanguageChoosen();
        changeLanguage();
        SceneHandler.getInstance().createLoginScene();
    }
    @FXML
    void onCosentinoClick(){
        cosentinoLanguageChoosen();
        changeLanguage();
        SceneHandler.getInstance().createLoginScene();
    }

    @FXML
    void onRegisterButtonClick() {
        sceneHandler.createRegistrationScene();
    }


    @FXML
    void initialize(){
        loginButton.setDisable(true);
        uploadLanguage();
        updateLanguage();
        checkLogged();
        listenerUsername();
        listenerPassword();
    }

    // Metodi
    private void checkLogged(){
        Platform.runLater(() -> {
            if (!loggedHandler.stayLoggedReading().equals("null")) {
                username = loggedHandler.stayLoggedReading();
                settingsHandler.updateSettings();
                sceneHandler.createSideBar();
            }
        });
    }

    private void listenerUsername(){
        usernameText.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodUsername = newValue.length() >= 5;
            performBinding();
        });
    }

    private void listenerPassword(){
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodPassword = newValue.length() >= 8;
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
                            usernameText.textProperty(),
                            passwordText.textProperty()
                    );
                }

                @Override
                protected boolean computeValue() {
                    return !(isGoodUsername && isGoodPassword);
                }
            };

            loginButton.disableProperty().bind(bb);
        });
    }

    private void italianLanguageChoosen(){
        italianLanguage.setSelected(true);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("Italiano");
    }
    private void englishLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(true);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("English");
    }
    private void frenchLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(true);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("Français");
    }

    private void spanishLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(true);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("Español");
    }

    private void cosentinoLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(true);
        languageMenuButton.setText("Cosentino");
    }

    private void uploadLanguage(){
        if(settingsHandler.loginLanguage.equals("it")) italianLanguageChoosen();
        if(settingsHandler.loginLanguage.equals("en")) englishLanguageChoosen();
        if(settingsHandler.loginLanguage.equals("fr")) frenchLanguageChoosen();
        if(settingsHandler.loginLanguage.equals("es")) spanishLanguageChoosen();
        if(settingsHandler.loginLanguage.equals("cs")) cosentinoLanguageChoosen();
    }

    private void changeLanguage(){
        if(italianLanguage.isSelected()) settingsHandler.loginLanguage = "it";
        if(englishLanguage.isSelected()) settingsHandler.loginLanguage = "en";
        if(frenchLanguage.isSelected()) settingsHandler.loginLanguage  = "fr";
        if(spanishLanguage.isSelected()) settingsHandler.loginLanguage  = "es";
        if(cosentinoLanguage.isSelected()) settingsHandler.loginLanguage  = "cs";
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = LanguageHandler.getInstance().getBundle();
        } catch (Exception e){
           alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null){
            languageLabel.setText(bundle.getString("languageLabel"));
            stayLogged.setText(bundle.getString("staySignedLabel"));
            loginButton.setText(bundle.getString("loginButton"));
            forgotPassButton.setText(bundle.getString("forgotPassButton"));
            registerButton.setText(bundle.getString("registerButton"));
        }
    }
}
