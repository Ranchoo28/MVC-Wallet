package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private Label eyeIcon;
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
    private MenuItem italianLanguage, englishLanguage, frenchLanguage, spanishLanguage, cosentinoLanguage;

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
    private final String pathOfFont = PathHandler.getInstance().getPathOfFont();

    @FXML
    void onLoginButtonClick() throws InterruptedException {

        // Una volta premuto il button, esegue il login tramite una query al database e
        // in base al risultato apre un popup.
        switch(sqlService.serviceLogin(usernameText.getText(), passwordField.getText())){
            case 0 -> {
                username = usernameText.getText();
                if(stayLogged.isSelected()){
                    sqlHandler.stayLoggedOfLogin(username);
                    loggedHandler.stayLoggedWriting(username);
                }
                settingsHandler.updateSettings();
                alertHandler.createLoginAlert();
            }

            case 1 -> alertHandler.createErrorAlert(bundle.getString("loginErrorUsernameText"));
            case 2 ->  alertHandler.createErrorAlert(bundle.getString("loginErrorPassText"));
            default ->  alertHandler.createErrorAlert(bundle.getString("loginErrorAllText"));
        }
    }

    @FXML
    void onForgotPasswordClick() { sceneHandler.createForgotPasswordScene(); }

    @FXML
    void onItalianClick(){
        languageMenuButton.setText("Italiano");
        italianLanguageChoosen();
        settingsHandler.loginLanguage = "it";
        SceneHandler.getInstance().createLoginScene();
    }
    @FXML
    void onEnglishClick(){
        languageMenuButton.setText("English");
        englishLanguageChoosen();
        settingsHandler.loginLanguage = "en";
        SceneHandler.getInstance().createLoginScene();
    }
    @FXML
    void onFrenchClick(){
        languageMenuButton.setText("Français");
        frenchLanguageChoosen();
        settingsHandler.loginLanguage  = "fr";
        SceneHandler.getInstance().createLoginScene();

    }
    @FXML
    void onSpanishClick(){
        languageMenuButton.setText("Español");
        spanishLanguageChoosen();
        settingsHandler.loginLanguage  = "es";
        SceneHandler.getInstance().createLoginScene();
    }
    @FXML
    void onCosentinoClick(){
        languageMenuButton.setText("Cosentino");
        cosentinoLanguageChoosen();
        settingsHandler.loginLanguage  = "cs";
        SceneHandler.getInstance().createLoginScene();
    }

    @FXML
    void onRegisterButtonClick() {
        sceneHandler.createRegistrationScene();
    }

    @FXML
    void showPassword() {
        eyeIcon.setText("\uF06E");
        passwordText.setText(passwordField.getText());
        passwordField.setVisible(false);
        passwordText.setVisible(true);
    }
    @FXML
    void hidePassword(){
        eyeIcon.setText("\uF070");
        passwordField.setText(passwordText.getText());
        passwordField.setVisible(true);
        passwordText.setVisible(false);
    }

    @FXML
    void initialize(){
        Font font = Font.loadFont(String.valueOf(getClass().getResource(pathOfFont+"fa-solid-900.ttf")), 16);
        eyeIcon.setText("\uF070");
        eyeIcon.setFont(font);
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
            isGoodPassword = newValue.length() >= 8 || passwordText.getText().length() >= 8;
            performBinding();
        });

        passwordText.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodPassword = newValue.length() >= 8 || passwordField.getText().length() >= 8;
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
                            passwordText.textProperty(),
                            passwordField.textProperty()
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
        languageMenuButton.setText("Italiano");
    }
    private void englishLanguageChoosen(){
        languageMenuButton.setText("English");
    }
    private void frenchLanguageChoosen(){
        languageMenuButton.setText("Français");
    }

    private void spanishLanguageChoosen(){
        languageMenuButton.setText("Español");
    }

    private void cosentinoLanguageChoosen(){
        languageMenuButton.setText("Cosentino");
    }

    private void uploadLanguage(){
        switch(settingsHandler.loginLanguage){
            case "it" -> italianLanguageChoosen();
            case "en" -> englishLanguageChoosen();
            case "fr" -> frenchLanguageChoosen();
            case "es" -> spanishLanguageChoosen();
            case "cs" -> cosentinoLanguageChoosen();
        }
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = LanguageHandler.getInstance().getBundle();
        } catch (Exception e){
           alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null){
            stayLogged.setText(bundle.getString("staySignedLabel"));
            loginButton.setText(bundle.getString("loginButton"));
            forgotPassButton.setText(bundle.getString("forgotPassButton"));
            registerButton.setText(bundle.getString("registerButton"));
        }
    }
}
