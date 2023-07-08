package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ChangePassController {



    @FXML
    private Button cancelButton;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField oldPasswordTextField;

    @FXML
    private Button saveButton;

    @FXML
    void onCancelClick(){
        SceneHandler.getInstance().createSideBar();
    }

    private boolean isGoodOldPassword(){
        return (SqlHandler.getInstance().checkPassword(LoginController.username,oldPasswordTextField.getText()));
    }


    @FXML
    void onSaveClick() {
        if(SqlService.getIstance().serviceChangePassword(newPasswordTextField.getText(),LoginController.username)){
            //LoginController. = usernameTextField.getText();
            SceneHandler.getInstance().createChangedAlert("Password");
            SceneHandler.getInstance().createSideBar();
        }
    }

    @FXML
    void initialize() {
        newPasswordTextField.setDisable(true);
        saveButton.setDisable(true);
    }
}
