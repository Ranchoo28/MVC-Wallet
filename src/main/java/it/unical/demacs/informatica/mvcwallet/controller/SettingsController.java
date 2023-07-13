package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ResourceBundle;

public class SettingsController {
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LoggedHandler loggedHandler = LoggedHandler.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SettingsHandler settingsHandler = SettingsHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
    String pathOfFont = PathHandler.getInstance().getPathOfFont();

    @FXML
    private Label accessibilityLabel, accountLabel;
    @FXML
    private CheckBox stayLogged;
    @FXML
    private Label pageLabel, timeLabel, currencyLabel, languageLabel, themeLabel, signedLabel, aboutLabel;
    @FXML
    private Label applyLabel, cancelLabel;
    @FXML
    private Button applyButton, backButton;
    @FXML
    private AnchorPane colorPickerPane;
    @FXML
    private MenuItem spotMenuItem, marketMenuItem;
    @FXML
    private MenuItem h12MenuItem, h24MenuItem;
    @FXML
    private MenuItem eurMenuItem, usdMenuItem;
    @FXML
    private MenuItem itaMenuItem, engMenuItem, freMenuItem, spaMenuItem, cosMenuItem;
    @FXML
    private MenuItem backMenuItem, darkMenuItem, lightMenuItem, mvcMenuItem, blueMenuItem, customMenuItem, cpMenuItem;

    @FXML private MenuButton mainPageMenuButton, timeFormatMenuButton,
            currencyMenuButton, languageMenuButton, themeMenuButton;

    /* Click per cambiare formato ora */
    @FXML
    void on12hClick() { h12Choosen(); }
    @FXML
    void on24hClick() { h24Choosen(); }

    /* Click per schemata di avvio */
    @FXML
    void onSpotClick() { spotChoosen(); }
    @FXML
    void onMarketClick() { marketChoosen(); }

    /* Click per cambiare tema*/
    @FXML
    void onDarkClick() { darkThemeChoosen(); }
    @FXML
    void onLightClick() { lightThemeChoosen(); }
    @FXML
    void onMvcClick() { mvcThemeChoosen(); }
    @FXML
    void onBlueClick() { blueThemeChoosen(); }
    @FXML
    void onCustomClick() throws IOException { customThemeChoosen(); }

    /* Click per cambiare lingua */
    @FXML
    void onItalianClick(){italianLanguageChoosen();}
    @FXML
    void onEnglishClick(){ englishLanguageChoosen(); }
    @FXML
    void onFrenchClick() { frenchLanguageChoosen(); }
    @FXML
    void onSpanishClick() { spanishLanguageChoosen(); }
    @FXML
    void onCosentinoClick() { cosentinoLanguageChoosen(); }

    /* Click per cambiare valuta*/
    @FXML
    void onEurClick(){ eurCurrencyChoosen(); }

    @FXML
    void onUsdClick(){ usdCurrencyChoosen(); }

    @FXML
    void onSaveClick(){
        try{
            String [] settings = changeSettings();
            settingsHandler.uploadSettingOnDB(settings[0], settings[1], settings[2], settings[3], settings[4], settings[5]);

            if(settings[2].equals("0")) loggedHandler.stayLoggedWriting("null");
            if(settings[2].equals("1")) loggedHandler.stayLoggedWriting(LoginController.username);

            sceneHandler.closeCAT();
            settingsHandler.updateSettings();
            sceneHandler.createSideBar();
        }catch (Exception e){
            alertHandler.createErrorAlert(bundle.getString("errorChangeSettingsText"));
            SettingsHandler.getInstance().defaultSettings();
        }
    }

    @FXML
    void onCancelClick(){
        sceneHandler.closeCAT();
        sceneHandler.createSideBar();
    }

    @FXML
    void initialize(){
        updateLanguage();
        updateAllSettings();
        aboutLabel.setText("\uF129");
        Font font = Font.loadFont(String.valueOf(getClass().getResource(pathOfFont+"fa-solid-900.ttf")), 20);
        aboutLabel.setFont(font);

    }

    String setMainPage(String page){
        try{
            if(page!=null)
                switch (page){
                    case "spot" -> {
                        return "spot";
                    }
                    case "market" -> {
                        return "market";
                    }
                    case "settings" -> {
                        return setMainPage(settingsHandler.old);
                    }
                }
        } catch (Exception e){
          alertHandler.createErrorAlert(bundle.getString("errorChangeSettingsText"));
        }
        return "market";
    }

    private void updateAllSettings(){
        switch (setMainPage(settingsHandler.page)){
            case "spot" -> spotChoosen();
            case "market" -> marketChoosen();
        }

        switch (settingsHandler.format){
            case "HH:mm:ss" -> h24Choosen();
            case "hh:mm:ss a" -> h12Choosen();
        }

        if(settingsHandler.logged) stayLogged();
        if(!settingsHandler.logged) noStayLogged();

        switch(settingsHandler.theme){
            case "mvc.css" -> mvcThemeChoosen();
            case "dark.css" -> darkThemeChoosen();
            case "light.css" -> lightThemeChoosen();
            case "blue.css" -> blueThemeChoosen();
            case "custom.css" -> setCustomTheme();
        }

        switch(settingsHandler.language){
            case "it" -> italianLanguageChoosen();
            case "en" -> englishLanguageChoosen();
            case "fr" -> frenchLanguageChoosen();
            case "es" -> spanishLanguageChoosen();
            case "cs" -> cosentinoLanguageChoosen();
        }

        switch(settingsHandler.currency){
            case "eur" -> eurCurrencyChoosen();
            case "usd" -> usdCurrencyChoosen();
        }
    }

    private void h12Choosen(){ timeFormatMenuButton.setText("12H"); }
    private void h24Choosen(){
        timeFormatMenuButton.setText("24H");
    }
    private void spotChoosen(){
        mainPageMenuButton.setText("Spot");
    }
    private void marketChoosen(){
        mainPageMenuButton.setText("Market");
    }
    private void darkThemeChoosen(){
        themeMenuButton.setText("Dark");
    }
    private void lightThemeChoosen(){
        themeMenuButton.setText("Light");
    }
    private void mvcThemeChoosen(){
        themeMenuButton.setText("MVC");
    }
    private void blueThemeChoosen(){ themeMenuButton.setText("Blue"); }
    private void setCustomTheme() { themeMenuButton.setText("Custom"); }
    private void customThemeChoosen() throws IOException { sceneHandler.openCAT(); }
    private void italianLanguageChoosen(){
        languageMenuButton.setText("Italiano");
    }
    private void englishLanguageChoosen(){
        languageMenuButton.setText("English");
    }
    private void spanishLanguageChoosen(){
        languageMenuButton.setText("Español");
    }
    private void frenchLanguageChoosen(){
        languageMenuButton.setText("Français");
    }
    private void cosentinoLanguageChoosen(){
        languageMenuButton.setText("Cosentino");
    }
    private void eurCurrencyChoosen(){
        currencyMenuButton.setText("EUR");
    }
    private void usdCurrencyChoosen(){
        currencyMenuButton.setText("USD");
    }
    private void stayLogged(){
        stayLogged.setSelected(true);
    }
    private void noStayLogged(){
        stayLogged.setSelected(false);
    }

    private String [] changeSettings(){
        String [] settings = new String[6];

        switch (timeFormatMenuButton.getText()){
            case "24H" -> settings[0] = "HH:mm:ss";
            case "12H" -> settings[0] = "hh:mm:ss a";
        }

        switch (mainPageMenuButton.getText()){
            case "Market" -> settings[1] = "market";
            case "Spot" -> settings[1] = "spot";
        }

        if(stayLogged.isSelected()) settings[2] = "1";
        else settings[2] = "0";

        switch (themeMenuButton.getText()){
            case "MVC" -> settings[3] = "mvc.css";
            case "Dark" -> settings[3] = "dark.css";
            case "Light" -> settings[3] = "light.css";
            case "Blue" -> settings[3] = "blue.css";
            case "Custom" -> settings[3] = "custom.css";
        }

        switch (languageMenuButton.getText()){
            case "Italiano" -> settings[4] = "it";
            case "English" -> settings[4] = "en";
            case "Español" -> settings[4] = "es";
            case "Français" -> settings[4] = "fr";
            case "Cosentino" -> settings[4] = "cs";
        }

        switch(currencyMenuButton.getText()){
            case "EUR" -> settings[5] = "eur";
            case "USD" -> settings[5] = "usd";
        }

        return settings;
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = LanguageHandler.getInstance().getBundle();
        } catch (Exception e){
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null){
            pageLabel.setText(bundle.getString("pageLabel"));
            timeLabel.setText(bundle.getString("formatTimeLabel"));
            currencyLabel.setText(bundle.getString("currencyLabel"));
            languageLabel.setText(bundle.getString("languageLabel"));
            themeLabel.setText(bundle.getString("themeLabel"));
            signedLabel.setText(bundle.getString("staySignedLabel"));
            applyLabel.setText(bundle.getString("applyButton"));
            cancelLabel.setText(bundle.getString("backButton"));
        }
    }
}