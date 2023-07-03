package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class AccountSettingsController {
    @FXML
    private HBox hboxProfile, hboxSettings, hboxBack, hboxPass;
    @FXML
    private Label profileLabel, settingsLabel, backLabel;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField passwordText;
    @FXML
    CheckBox showPasswordCheckBox;

    @FXML
    void onBackClick() {
        SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void onSaveClick() {
        SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void changeVisibility(){
        if(showPasswordCheckBox.isSelected()){
            passwordText.setText(passwordField.getText());
            passwordText.setVisible(true);
            passwordField.setVisible(false);
            return ;
        }
        passwordField.setText(passwordText.getText());
        passwordField.setVisible(true);
        passwordText.setVisible(false);
    }

    @FXML
    void initialize(){

        // Togliere il colore delle icon
        FontIcon profileIcon = new FontIcon("mdi2a-account-cog");
        profileIcon.setIconColor(Paint.valueOf("#fff"));
        profileIcon.setIconSize(25);
        profileLabel.setContentDisplay(ContentDisplay.LEFT);
        profileLabel.setGraphic(profileIcon);

            /*
            FontIcon passwordIcon = new FontIcon("mdi2f-form-textbox-password");
            passwordIcon.setIconSize(25);
            passwordIcon.setIconColor(Paint.valueOf("fff"));
            passwordLabel.setContentDisplay(ContentDisplay.LEFT);
            passwordLabel.setGraphic(passwordIcon);

             */

        FontIcon settingsIcon = new FontIcon("mdi2c-cog");
        settingsIcon.setIconSize(25);
        settingsIcon.setIconColor(Paint.valueOf("#fff"));
        settingsLabel.setContentDisplay(ContentDisplay.LEFT);
        settingsLabel.setGraphic(settingsIcon);

        FontIcon backIcon = new FontIcon("mdi2k-keyboard-backspace");
        backIcon.setIconSize(25);
        backIcon.setIconColor(Paint.valueOf("#fff"));
        settingsLabel.setContentDisplay(ContentDisplay.LEFT);
        backLabel.setGraphic(backIcon);
    }
}
