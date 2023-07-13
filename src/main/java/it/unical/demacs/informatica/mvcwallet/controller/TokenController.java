package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.model.EmailService;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class TokenController {
    public static String token;
    public static String email;
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler lanHandler = LanguageHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final EmailService emailService = EmailService.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
    @FXML
    private Button backButton, submitButton;

    @FXML
    private TextField emailTextField;

    @FXML
    void onNewPasswordClick() {
        // savcreaa.kr99@gmail.com

        // Una volta premuto il button genera una nuova password, controlla se la mail esiste, manda la nuova password
        // via mail, esegue una query per cambiare password ed infine esce un popup come avviso.

        if(sqlService.getEmail(emailTextField.getText())){
            email = emailTextField.getText();
            emailService.emailServiceForgotPassword(emailTextField.getText(),
                    lanHandler.getBundle().getString("forgotPassEmailTitle"),
                    lanHandler.getBundle().getString("forgotPassEmailText1"), " " + token + " \n" +
                            lanHandler.getBundle().getString("forgotPassEmailText2"));

            alertHandler.createForgotPassAlert(lanHandler.getBundle().getString("forgotPassTextEmailSent"));

        }else alertHandler.createErrorAlert(lanHandler.getBundle().getString("notExistingEmailText"));
    }

    @FXML
    void onCancelButtonClick(){
        sceneHandler.createLoginScene();
    }


    @FXML
    void initialize(){
        updateLanguage();
        generateToken();
    }

    private void generateToken(){
        final long duration = 309000; // 5 minuti + 15 secondi
        Timer timer = new Timer();
        token = UUID.randomUUID().toString();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                token = UUID.randomUUID().toString();
            }
        },duration);
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = lanHandler.getBundle();
        } catch (Exception e){
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null) {
            submitButton.setText(bundle.getString("submitButton"));
            backButton.setText(bundle.getString("backButton"));
        }
    }

}
