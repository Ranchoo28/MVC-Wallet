package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import it.unical.demacs.informatica.mvcwallet.handler.RegexHandler;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ChangePassController {
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private boolean isGoodOldPassword = false;
    private boolean isGoodPassword = false;
    private final RegexHandler regexHandler = RegexHandler.getInstance();

    @FXML
    private Button cancelButton;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField oldPasswordTextField;

    @FXML
    private Button saveButton;

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
        newPasswordTextField.setDisable(true);
        saveButton.setDisable(true);
        oldPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (SqlHandler.getInstance().checkPassword(LoginController.username, oldPasswordTextField.getText())) {
                isGoodOldPassword = true;
                newPasswordTextField.setDisable(false);
            } else {
                isGoodOldPassword = false;
                newPasswordTextField.setDisable(true);

            }
        });
        newPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Controlla se la password rispetta il Regex
            isGoodPassword = newValue.matches(regexHandler.regexPassword)&& (!newPasswordTextField.getText().equals(oldPasswordTextField.getText())  );
            if (isGoodPassword) {
                saveButton.setDisable(false);
            }
        });
    }
}