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
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
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
    private CheckBox stayLogged;

    @FXML
    void onLoginButtonClick() throws InterruptedException {

        // Una volta premuto il button, esegue il login tramite una query al database e
        // in base al risultato apre un popup.


        if(SqlService.getIstance().serviceLogin(Username.getText(), Password.getText()) == 0){
            username = Username.getText();
            if(stayLogged.isSelected()){
                SqlHandler.getInstance().stayLoggedOfLogin(username);
                LoggedHandler.getInstance().stayLoggedWriting(username);
            }
            SettingsHandler.getInstance().updateSettings();
            alertHandler.createLoginAlert();
            //for(String i: SettingsHandler.getInstance().settings) System.out.println("ciao" + i);
        }else if(SqlService.getIstance().serviceLogin(Username.getText(), Password.getText()) == 1)
            alertHandler.createErrorAlert("Username inesistente. Riprova");
        else if(SqlService.getIstance().serviceLogin(Username.getText(), Password.getText()) == 2)
            alertHandler.createErrorAlert("Password errata. Riprova");
        else if (SqlService.getIstance().serviceLogin(Username.getText(), Password.getText()) == 3) {
            alertHandler.createErrorAlert("Username o password errati. Riprova");
        }

        //SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void onRegisterButtonClick() {
        SceneHandler.getInstance().createRegistrationScene();
    }
    @FXML
    void onForgotPasswordClick() { SceneHandler.getInstance().createForgotPasswordScene(); }

    @FXML
    void initialize(){

        Platform.runLater(() -> {
            if (!LoggedHandler.getInstance().stayLoggedReading().equals("null")) {
                // Non capisco perchè bisogna rifare la connessione.
                username = LoggedHandler.getInstance().stayLoggedReading();
                SqlHandler.getInstance().newConnection();
                SettingsHandler.getInstance().updateSettings();
                SceneHandler.getInstance().createSideBar();
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
