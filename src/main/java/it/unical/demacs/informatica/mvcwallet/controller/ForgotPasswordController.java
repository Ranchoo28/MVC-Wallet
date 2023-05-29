package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.EmailHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SQLHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.UUID;

public class ForgotPasswordController {

    @FXML
    private TextField fieldMail;

    @FXML
    void onNewPasswordClick() {
        //savcreaa.kr99@gmail.com

        // Una volta premuto il button genera una nuova password, controlla se la mail esiste, manda la nuova password
        // via mail, esegue una query per cambiare password ed infine esce un popup come avviso.
        String newPassword =  UUID.randomUUID().toString();
        if(SQLHandler.getIstance().forgotPassword(fieldMail.getText(), newPassword)){
            EmailHandler.getInstance().startThreadForgotPassword(fieldMail.getText(),
                    "Nuova password",
                    "Ecco a te la nuova password: ", newPassword);
            SceneHandler.getInstance().createForgotPassAlert(
                    "La nuova password è stata inviata via email. Cambiala il prima possibile");
        }else{
            SceneHandler.getInstance().createErrorAlert("Email non presente nel sistema. Riprova");
        }
    }

    @FXML
    void onCancelButtonClick(){
        SceneHandler.getInstance().createLoginScene();
    }

}
