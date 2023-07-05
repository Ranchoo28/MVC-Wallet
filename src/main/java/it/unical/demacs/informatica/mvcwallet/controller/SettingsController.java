package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SettingsHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Set;

public class SettingsController {
    @FXML
    private MenuButton languageMenu, themesMenu;

    @FXML
    private Label accessibilityLabel, accountLabel;

    @FXML
    private RadioMenuItem italianLanguage, englishLanguage,
            whiteTheme, darkTheme, mvcTheme,
            h24Time, h12Time,
            spotPage, marketPage,
            eurCurrency, usdCurrency;

    @FXML
    void on12hClick() {
        h12Choosen();
        SettingsHandler.getInstance().format = "hh:mm:ss a";
    }

    @FXML
    void on24hClick() {
        h24Choosen();
        SettingsHandler.getInstance().format = "HH:mm:ss";
    }

    @FXML
    void onSpotClick() {
        spotChoosen();
        SettingsHandler.getInstance().format = "spot";
    }

    @FXML
    void onMarketClick() {
        marketChoosen();
        SettingsHandler.getInstance().format = "market";
    }



    @FXML
    void onCancelClick(){
        SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void initialize(){
        if(SettingsHandler.getInstance().format.equals("HH:mm:ss")) h24Choosen();
        if(SettingsHandler.getInstance().format.equals("hh:mm:ss a")) h12Choosen();

        if(SettingsHandler.getInstance().page.equals("spot")) spotChoosen();
        if(SettingsHandler.getInstance().page.equals("market")) marketChoosen();

        // Icona per i temi
        FontIcon iconThemes = new FontIcon("mdi2t-theme-light-dark");
        iconThemes.setIconSize(25);
        themesMenu.setContentDisplay(ContentDisplay.RIGHT);
        themesMenu.setGraphic(iconThemes);

        // Icona per la lingua
        FontIcon iconLanguage = new FontIcon("mdi2e-earth");
        iconLanguage.setIconSize(25);
        languageMenu.setContentDisplay(ContentDisplay.RIGHT);
        languageMenu.setGraphic(iconLanguage);
    }

    private void h12Choosen(){
        h12Time.setSelected(true);
        h24Time.setSelected(false);

    }

    private void h24Choosen(){
        h12Time.setSelected(false);
        h24Time.setSelected(true);
    }

    private void spotChoosen(){
        spotPage.setSelected(true);
        marketPage.setSelected(false);
    }

    private void marketChoosen(){
        spotPage.setSelected(false);
        marketPage.setSelected(true);
    }

}

