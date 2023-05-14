package com.example.progettouid.application.controller;

import com.example.progettouid.application.handler.SQLHandler;
import com.example.progettouid.application.handler.SceneHandler;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static java.lang.Thread.sleep;


public class LoginController {
    public static String username;
    private boolean isGoodUsername;
    private boolean isGoodPassword;
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;

    @FXML
    private Button buttonLogin = new Button();

    @FXML
    void onLoginButtonClick() {
        // Una volta premuto il button, esegue il login tramite una query al database e
        // in base al risultato apre un popup.
        if(SQLHandler.getIstance().checkLogin(Username.getText(), Password.getText()) == 0){
            username = Username.getText();
            SceneHandler.getInstance().createLoginAlert();
        }else if(SQLHandler.getIstance().checkLogin(Username.getText(), Password.getText()) == 1)
            SceneHandler.getInstance().createErrorAlert("Username inesistente. Riprova");
        else if(SQLHandler.getIstance().checkLogin(Username.getText(), Password.getText()) == 2)
            SceneHandler.getInstance().createErrorAlert("Password errata. Riprova");
        else if (SQLHandler.getIstance().checkLogin(Username.getText(), Password.getText()) == 3) {
            SceneHandler.getInstance().createErrorAlert("Username o password errati. Riprova");
        }
    }

    @FXML
    void onRegisterButtonClick() {
        SceneHandler.getInstance().createRegistrationScene();
    }
    @FXML
    void onForgotPasswordClick() { SceneHandler.getInstance().createForgotPasswordScene(); }

    @FXML
    void initialize(){

        buttonLogin.setDisable(true);
        Username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() < 5)
                    isGoodUsername = false;
                else
                    isGoodUsername = true;

                performBinding();
            }
        });

        Password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // cambiare ad 8 (lasciare 4 per i test)
                if(newValue.length() < 4)
                    isGoodPassword = false;
                else
                    isGoodPassword = true;

                performBinding();
            }
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
