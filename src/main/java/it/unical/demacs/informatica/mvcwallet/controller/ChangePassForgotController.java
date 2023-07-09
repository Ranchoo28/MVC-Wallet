package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.handler.RegexHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.util.Duration;

import java.lang.ref.PhantomReference;
import java.security.Provider;

public class ChangePassForgotController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField tokenField, passwordText;
    @FXML
    private Button changeButton;
    @FXML
    private CheckBox showPassBox;


    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler lanHandler = LanguageHandler.getInstance();
    private boolean isGoodToken, isGoodPassword;

    @FXML
    void onShowClick() {
        if(showPassBox.isSelected()){
            passwordText.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordText.setVisible(true);
        }
        else {
            passwordField.setText(passwordText.getText());
            passwordField.setVisible(false);
            passwordField.setVisible(true);
        }
    }

    @FXML
    void onChangeClick(){
        if(SqlService.getIstance().serviceForgotPassword(ForgotPasswordController.email, passwordField.getText()))
            alertHandler.passChangedFromForgot();
        else{
            alertHandler.createErrorAlert(lanHandler.getBundle().getString("changePassErrorText"));
            sceneHandler.createLoginScene();
        }
    }

    @FXML
    void onCancelClick(){
        sceneHandler.createLoginScene();
    }

    @FXML
    void initialize(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (changeButton.isDisabled() || !changeButton.isDisabled()) {
                isGoodToken = tokenField.getText().equals(ForgotPasswordController.token);
                if(passwordField.getText().matches(RegexHandler.getInstance().regexPassword) || passwordText.getText().matches(RegexHandler.getInstance().regexPassword))
                    isGoodPassword = true;
                checkTokenAndPassword();
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void checkTokenAndPassword() {
        // Serve a disabilitare il button del check qualora non venissero introdotte credenziali valide
        // durante il cambio password.
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(changeButton.armedProperty());
                }

                @Override
                protected boolean computeValue() {
                    return !(isGoodPassword && isGoodToken);
                }
            };

            changeButton.disableProperty().bind(bb);
        });

    }
}
