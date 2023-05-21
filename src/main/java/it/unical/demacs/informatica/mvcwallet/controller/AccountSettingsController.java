package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class AccountSettingsController {
    @FXML
    private HBox hboxProfile, hboxPassword, hboxSettings, hboxBack;
    @FXML
    private Label passwordLabel, profileLabel, settingsLabel, backLabel;


    @FXML
    void onBackClick() {
        SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void onSaveClick() {
        SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void initialize(){
        hboxProfile.getStyleClass().add("hbox");
        hboxPassword.getStyleClass().add("hbox");
        hboxSettings.getStyleClass().add("hbox");
        hboxBack.getStyleClass().add("hbox");

        FontIcon profileIcon = new FontIcon("mdi2a-account-cog");
        profileIcon.setIconColor(Paint.valueOf("#fff"));
        profileIcon.setIconSize(25);
        profileLabel.setContentDisplay(ContentDisplay.LEFT);
        profileLabel.setGraphic(profileIcon);

        FontIcon passwordIcon = new FontIcon("mdi2f-form-textbox-password");
        passwordIcon.setIconSize(25);
        passwordIcon.setIconColor(Paint.valueOf("fff"));
        passwordLabel.setContentDisplay(ContentDisplay.LEFT);
        passwordLabel.setGraphic(passwordIcon);

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
