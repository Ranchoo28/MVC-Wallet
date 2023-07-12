package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.util.ResourceBundle;

public class ChangePassController {
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final RegexHandler regexHandler = RegexHandler.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
    private final String pathOfFont = PathHandler.getInstance().getPathOfFont();
    private boolean isGoodOldPassword = false;
    private boolean isGoodPassword = false;

    @FXML
    private Button changeButton, cancelButton;
    @FXML
    private Label eyeIconOldPassword, eyeIconNewPassword;
    @FXML
    private Label newPasswordLabel, oldPasswordLabel;
    @FXML
    private PasswordField oldPassPasswordField, newPassPasswordField;
    @FXML
    private TextField oldPasswordTextField, newPasswordTextField;

    @FXML
    void onCancelClick() {
        sceneHandler.createSideBar();
    }

    @FXML
    void onChangeClick() {
        if (sqlService.serviceChangePassword(newPasswordTextField.getText(), LoginController.username)) {
            //LoginController. = usernameTextField.getText();
            alertHandler.createChangedAlert();
            sceneHandler.createSideBar();
        }
    }

    @FXML
    void initialize() {
        uploadLanguage();
        changeButton.setDisable(true);
        addListenerOldPassword();
        addListenerNewPassword();
        Font font = Font.loadFont(String.valueOf(getClass().getResource(pathOfFont+"fa-solid-900.ttf")), 16);
        eyeIconOldPassword.setFont(font);
        eyeIconOldPassword.setText("\uF070");
        eyeIconNewPassword.setFont(font);
        eyeIconNewPassword.setText("\uF070");
    }

    @FXML
    void showNewPassword() {
        eyeIconNewPassword.setText("\uF06E");
        newPasswordTextField.setText(newPassPasswordField.getText());
        newPassPasswordField.setVisible(false);
        newPasswordTextField.setDisable(false);
    }
    @FXML
    void hideNewPassword(){
        eyeIconNewPassword.setText("\uF070");
        newPasswordTextField.setText(newPassPasswordField.getText());
        newPassPasswordField.setVisible(true);
        newPasswordTextField.setDisable(true);
    }
    @FXML
    void showOldPassword() {
        eyeIconOldPassword.setText("\uF06E");
        oldPasswordTextField.setText(oldPassPasswordField.getText());
        oldPassPasswordField.setVisible(false);
    }
    @FXML
    void hideOldPassword(){
        eyeIconOldPassword.setText("\uF070");
        oldPasswordTextField.setText(oldPassPasswordField.getText());
        oldPassPasswordField.setVisible(true);
    }

    private void addListenerOldPassword(){
        oldPassPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodOldPassword = SqlHandler.getInstance().checkPassword(LoginController.username, oldPassPasswordField.getText());
            if (isGoodOldPassword && isGoodPassword){
                newPassPasswordField.setDisable(false);
                eyeIconNewPassword.setDisable(false);
                changeButton.setDisable(false);
            } else if (isGoodOldPassword){
                newPassPasswordField.setDisable(false);
                eyeIconNewPassword.setDisable(false);
                changeButton.setDisable(true);
            } else {
                newPassPasswordField.setText("");
                newPasswordTextField.setText("");
                newPassPasswordField.setDisable(true);
                eyeIconNewPassword.setDisable(true);
                changeButton.setDisable(true);
            }
        });
    }

    private void addListenerNewPassword(){
        newPassPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Controlla se la password rispetta il Regex
            isGoodPassword = newValue.matches(regexHandler.regexPassword) && (!newPassPasswordField.getText().equals(oldPassPasswordField.getText()));
            changeButton.setDisable(!isGoodPassword || !isGoodOldPassword);
        });
    }

    private void uploadLanguage(){
        changeButton.setText(bundle.getString("applyButton"));
        cancelButton.setText(bundle.getString("backButton"));
        oldPasswordLabel.setText(bundle.getString("oldPassLabel"));
        newPasswordLabel.setText(bundle.getString("newPassLabel"));
        newPasswordTextField.setTooltip(new Tooltip(bundle.getString("tooltipPassword")));
    }

}