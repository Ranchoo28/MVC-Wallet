package com.example.progettouid.application.controller;

import com.example.progettouid.application.handler.SceneHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import org.kordamp.ikonli.javafx.FontIcon;

public class SettingsController {
    @FXML
    private MenuButton languageMenu, themesMenu;

    @FXML
    private Label accessibilityLabel, accountLabel;

    @FXML
    void onCancelClick(){
        SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void initialize(){

        // Icona per l'account
        FontIcon iconAccount = new FontIcon("mdi2a-account-cog");
        iconAccount.setIconSize(30);
        accountLabel.setContentDisplay(ContentDisplay.RIGHT);
        accountLabel.setGraphic(iconAccount);

        // Icona per l'accessibilità
        FontIcon iconAccess = new FontIcon("mdi2h-human");
        iconAccess.setIconSize(30);
        accessibilityLabel.setContentDisplay(ContentDisplay.RIGHT);
        accessibilityLabel.setGraphic(iconAccess);

        // Icona per i temi
        FontIcon iconThemes = new FontIcon("mdi2t-theme-light-dark");
        iconThemes.setIconSize(30);
        themesMenu.setContentDisplay(ContentDisplay.RIGHT);
        themesMenu.setGraphic(iconThemes);

        // Icona per la lingua
        FontIcon iconLanguage = new FontIcon("mdi2e-earth");
        iconLanguage.setIconSize(30);
        languageMenu.setContentDisplay(ContentDisplay.RIGHT);
        languageMenu.setGraphic(iconLanguage);
    }

}
