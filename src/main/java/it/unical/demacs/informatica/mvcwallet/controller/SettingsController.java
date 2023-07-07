package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.LoggedHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SettingsHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kordamp.ikonli.javafx.FontIcon;

public class SettingsController {

    @FXML
    private Label accessibilityLabel, accountLabel;

    @FXML
    private CheckBox stayLogged;

    @FXML
    private RadioMenuItem italianLanguage, englishLanguage,
            lightTheme, darkTheme, mvcTheme, marketPage, spotPage,
            h24Time, h12Time,
            eurCurrency, usdCurrency;

    @FXML private MenuButton mainPageMenuButton, timeFormatMenuButton,
            currencyMenuButton, languageMenuButton, themeMenuButton;
    @FXML
    void on12hClick() {
        h12Choosen();
    }

    @FXML
    void on24hClick() {
        h24Choosen();
    }

    @FXML
    void onSpotClick() {
        spotChoosen();
    }

    @FXML
    void onMarketClick() {
        marketChoosen();
    }


    @FXML
    void onSaveClick(){
        String [] settings = changeSettings();
        SqlHandler.getIstance().setSettingsQuery(
                LoginController.username,
                settings[0] ,
                settings[1],
                settings[2]);

        if(settings[2].equals("0")) LoggedHandler.getInstance().stayLoggedWriting("null");
        if(settings[2].equals("1")) LoggedHandler.getInstance().stayLoggedWriting(LoginController.username);

        SettingsHandler.getInstance().updateSettings();
        SceneHandler.getInstance().createSideBar();
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

        if(SettingsHandler.getInstance().logged) stayLogged();
        if(!SettingsHandler.getInstance().logged) noStayLogged();

        // Icona per i temi
        FontIcon iconThemes = new FontIcon("mdi2t-theme-light-dark");
        iconThemes.setIconSize(25);
        themeMenuButton.setContentDisplay(ContentDisplay.RIGHT);
        themeMenuButton.setGraphic(iconThemes);

        // Icona per la lingua
        FontIcon iconLanguage = new FontIcon("mdi2e-earth");
        iconLanguage.setIconSize(25);
        languageMenuButton.setContentDisplay(ContentDisplay.RIGHT);
        languageMenuButton.setGraphic(iconLanguage);
    }

    private void h12Choosen(){
        h12Time.setSelected(true);
        h24Time.setSelected(false);
        timeFormatMenuButton.setText("12H");
    }

    private void h24Choosen(){
        h12Time.setSelected(false);
        h24Time.setSelected(true);
        timeFormatMenuButton.setText("24H");
    }

    private void spotChoosen(){
        spotPage.setSelected(true);
        marketPage.setSelected(false);
        mainPageMenuButton.setText("Spot");
    }

    private void marketChoosen(){
        spotPage.setSelected(false);
        marketPage.setSelected(true);
        mainPageMenuButton.setText("Market");
    }

    private void stayLogged(){
        stayLogged.setSelected(true);
    }

    private void noStayLogged(){
        stayLogged.setSelected(false);
    }

    private String [] changeSettings(){
        String [] settings = new String[3];

        if(h24Time.isSelected()) settings[0] = "HH:mm:ss";
        if(h12Time.isSelected()) settings[0] = "hh:mm:ss a";

        if(marketPage.isSelected()) settings[1] = "market";
        if(spotPage.isSelected()) settings[1] = "spot";

        if(stayLogged.isSelected()) settings[2] = "1";
        else settings[2] = "0";

        return settings;
    }

}

