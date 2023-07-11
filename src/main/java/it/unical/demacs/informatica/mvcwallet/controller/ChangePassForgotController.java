package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class ChangePassForgotController {
    @FXML
    private Label eyeIcon;
    @FXML
    private Label newPassLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField tokenField, passwordText;
    @FXML
    private Button changeButton, backButton;

    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler lanHandler = LanguageHandler.getInstance();
    private final RegexHandler regexHandler = RegexHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
    private final String pathOfFont = PathHandler.getInstance().getPathOfFont();
    private boolean isGoodToken, isGoodPassword;

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
    void onChangeClick(){
        if(sqlService.serviceForgotPassword(TokenController.email, passwordField.getText()))
            alertHandler.passChangedFromForgotAlert();
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
        Font font = Font.loadFont(String.valueOf(getClass().getResource(pathOfFont+"fa-solid-900.ttf")), 16);
        eyeIcon.setText("\uF070");
        eyeIcon.setFont(font);
        updloadLanguage();
        createTimeline();
    }

    private void checkTokenAndPassword() {
        // Serve a disabilitare il button del check qualora non venissero introdotte credenziali valide
        // durante il cambio password.
        Platform.runLater(() -> {
            BooleanBinding bb = new BooleanBinding() {
                {
                    super.bind(
                            tokenField.textProperty(),
                            passwordField.textProperty(),
                            passwordText.textProperty()
                            );
                }

                @Override
                protected boolean computeValue() {
                    return !(isGoodPassword && isGoodToken);
                }
            };

            changeButton.disableProperty().bind(bb);
        });
    }

    private void createTimeline(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (changeButton.isDisabled() || !changeButton.isDisabled()) {
                isGoodToken = tokenField.getText().equals(TokenController.token);
                isGoodPassword = passwordField.getText().matches(regexHandler.regexPassword) || passwordText.getText().matches(regexHandler.regexPassword);
                checkTokenAndPassword();
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updloadLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = LanguageHandler.getInstance().getBundle();
        } catch (Exception e){
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null){
            passwordText.setTooltip(new Tooltip(bundle.getString("tooltipPassword")));
            passwordField.setTooltip(new Tooltip(bundle.getString("tooltipPassword")));
            changeButton.setText(bundle.getString("changePasswordButton"));
            newPassLabel.setText(bundle.getString("newPassLabel"));
            backButton.setText(bundle.getString("backButton"));
        }
    }
}
