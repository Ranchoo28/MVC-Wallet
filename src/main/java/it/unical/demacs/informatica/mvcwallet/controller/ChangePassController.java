package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.ResourceBundle;


public class ChangePassController {
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final RegexHandler regexHandler = RegexHandler.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
    private boolean isGoodOldPassword = false;
    private boolean isGoodPassword = false;


    @FXML
    private Button saveButton, cancelButton;
    @FXML
    private Label oldPasswordLabel, newPasswordLabel;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField oldPasswordTextField;


    @FXML
    void onCancelClick() {
        sceneHandler.createSideBar();
    }

    private boolean isGoodOldPassword() {
        return (sqlHandler.checkPassword(LoginController.username, oldPasswordTextField.getText()));
    }


    @FXML
    void onSaveClick() {
        if (sqlService.serviceChangePassword(newPasswordTextField.getText(), LoginController.username)) {
            //LoginController. = usernameTextField.getText();
            alertHandler.createChangedAlert("Password");
            sceneHandler.createSideBar();
        }
    }

    @FXML
    void initialize() {
        uploadLanguage();
        newPasswordTextField.setDisable(true);
        saveButton.setDisable(true);
        addListenerOldPassword();
        addListenerNewPassword();
    }

    private void addListenerOldPassword(){
        oldPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isGoodOldPassword = SqlHandler.getInstance().checkPassword(LoginController.username, oldPasswordTextField.getText());


            if (isGoodOldPassword&& isGoodPassword){
                newPasswordTextField.setDisable(false);
                saveButton.setDisable(false);
            } else if (isGoodOldPassword ){
                newPasswordTextField.setDisable(false);
            }else{
                newPasswordTextField.setDisable(true);
                saveButton.setDisable(true);
            }
        });
    }

    private void addListenerNewPassword(){
        newPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Controlla se la password rispetta il Regex
            isGoodPassword = newValue.matches(regexHandler.regexPassword) && (!newPasswordTextField.getText().equals(oldPasswordTextField.getText())  );
            if (isGoodPassword && isGoodOldPassword ) {
                saveButton.setDisable(false);
            }else{
                saveButton.setDisable(true);
            }
        });
    }

    private void uploadLanguage(){
        saveButton.setText(bundle.getString("applyButton"));
        cancelButton.setText(bundle.getString("backButton"));
        oldPasswordLabel.setText(bundle.getString("oldPassLabel"));
        newPasswordLabel.setText(bundle.getString("newPassLabel"));
        newPasswordTextField.setTooltip(new Tooltip(bundle.getString("tooltipPassword")));
    }

}