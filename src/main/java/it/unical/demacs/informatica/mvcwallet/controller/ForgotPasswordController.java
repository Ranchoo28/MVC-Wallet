package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.model.EmailService;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ForgotPasswordController {
    public static String token;
    public static String email;
    @FXML
    private TextField fieldMail;

    @FXML
    void onNewPasswordClick() {
        // savcreaa.kr99@gmail.com

        // Una volta premuto il button genera una nuova password, controlla se la mail esiste, manda la nuova password
        // via mail, esegue una query per cambiare password ed infine esce un popup come avviso.

        if(SqlService.getIstance().getEmail(fieldMail.getText())){
            email = fieldMail.getText();
            EmailService.getInstance().startThreadForgotPassword(fieldMail.getText(),
                    "Password dimenticata",
                    "Ecco a te il token da inserire per cambiare la password (scade dopo 5 minuti): ", token  +
                    "\nSe non sei stato tu a richiederlo puoi ignorare questa email.");

            SceneHandler.getInstance().createForgotPassAlert("Ti abbiamo inviato sulla mail un token da inserire per il cambio password" +
                    ". Scade dopo 5 minuti");

        }else SceneHandler.getInstance().createErrorAlert("Email non presente nel sistema. Riprova");
    }

    @FXML
    void onCancelButtonClick(){
        SceneHandler.getInstance().createLoginScene();
    }


    @FXML
    void initialize(){
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

}
