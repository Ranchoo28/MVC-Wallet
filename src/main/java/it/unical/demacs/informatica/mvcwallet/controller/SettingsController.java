package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ResourceBundle;

public class SettingsController {
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LoggedHandler loggedHandler = LoggedHandler.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SettingsHandler settingsHandler = SettingsHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();

    @FXML
    private Label accessibilityLabel, accountLabel;
    @FXML
    private CheckBox stayLogged;
    @FXML
    private Label pageLabel, timeLabel, currencyLabel, languageLabel, themeLabel, signedLabel;
    @FXML
    private Button applyButton, backButton;

    @FXML
    private RadioMenuItem italianLanguage, englishLanguage, frenchLanguage, spanishLanguage, cosentinoLanguage,
            lightTheme, darkTheme, mvcTheme, marketPage, spotPage,
            h24Time, h12Time, blueTheme,
            eurCurrency, usdCurrency;

    @FXML private MenuButton mainPageMenuButton, timeFormatMenuButton,
            currencyMenuButton, languageMenuButton, themeMenuButton;
    @FXML
    void on12hClick() { h12Choosen(); }

    @FXML
    void on24hClick() { h24Choosen(); }

    @FXML
    void onSpotClick() { spotChoosen(); }

    @FXML
    void onMarketClick() { marketChoosen(); }

    @FXML
    void onDarkClick() { darkThemeChoosen(); }

    @FXML
    void onLightClick() { lightThemeChoosen(); }

    @FXML
    void onMvcClick() { mvcThemeChoosen(); }
    @FXML
    void onBlueClick() { blueThemeChoosen(); }

    @FXML
    void onItalianClick(){ italianLanguageChoosen(); }
    @FXML
    void onEnglishClick(){ englishLanguageChoosen(); }
    @FXML
    void onFrenchClick() { frenchLanguageChoosen(); }
    @FXML
    void onSpanishClick() { spanishLanguageChoosen(); }
    @FXML
    void onCosentinoClick() { cosentinoLanguageChoosen(); }

    @FXML
    void onEurClick(){ eurCurrencyChoosen(); }

    @FXML
    void onUsdClick(){ usdCurrencyChoosen(); }


    @FXML
    void onSaveClick(){
        try{
            String [] settings = changeSettings();
            sqlService.serviceChangeSetting(
                    LoginController.username,
                    settings[0] ,
                    settings[1],
                    settings[2],
                    settings[3],
                    settings[4],
                    settings[5]);

            if(settings[2].equals("0")) loggedHandler.stayLoggedWriting("null");
            if(settings[2].equals("1")) loggedHandler.stayLoggedWriting(LoginController.username);

            settingsHandler.updateSettings();
            sceneHandler.createSideBar();
        }catch (Exception e){
            alertHandler.createErrorAlert(bundle.getString("errorChangeSettingsText"));
            SettingsHandler.getInstance().defaultSettings();
        }
    }

    @FXML
    void onCancelClick(){
        sceneHandler.createSideBar();
    }

    @FXML
    void initialize(){
        updateLanguage();
        updateAllSettings();

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

    private void updateAllSettings(){
        switch (settingsHandler.format){
            case "HH:mm:ss" -> h24Choosen();
            case "hh:mm:ss a" -> h12Choosen();
        }

        switch (settingsHandler.page){
            case "spot" -> spotChoosen();
            case "hh:mm:ss a" -> marketChoosen();
        }

        if(settingsHandler.logged) stayLogged();
        if(!settingsHandler.logged) noStayLogged();

        switch(settingsHandler.theme){
            case "mvc.css" -> mvcThemeChoosen();
            case "dark.css" -> darkThemeChoosen();
            case "light.css" -> lightThemeChoosen();
            case "blue.css" -> blueThemeChoosen();
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
    private void blueThemeChoosen(){
        mvcTheme.setSelected(false);
        darkTheme.setSelected(false);
        lightTheme.setSelected(false);
        blueTheme.setSelected(true);
        themeMenuButton.setText("Blue");
    }
    private void mvcThemeChoosen(){
        mvcTheme.setSelected(true);
        darkTheme.setSelected(false);
        lightTheme.setSelected(false);
        blueTheme.setSelected(false);
        themeMenuButton.setText("MVC");
    }
    private void darkThemeChoosen(){
        mvcTheme.setSelected(false);
        darkTheme.setSelected(true);
        lightTheme.setSelected(false);
        blueTheme.setSelected(false);
        themeMenuButton.setText("Dark");
    }
    private void lightThemeChoosen(){
        mvcTheme.setSelected(false);
        darkTheme.setSelected(false);
        lightTheme.setSelected(true);
        blueTheme.setSelected(false);
        themeMenuButton.setText("Light");
    }
    private void italianLanguageChoosen(){
        italianLanguage.setSelected(true);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("Italiano");
    }
    private void englishLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(true);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("English");
    }
    private void frenchLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(true);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("Français");
    }

    private void spanishLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(true);
        cosentinoLanguage.setSelected(false);
        languageMenuButton.setText("Español");
    }

    private void cosentinoLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(false);
        frenchLanguage.setSelected(false);
        spanishLanguage.setSelected(false);
        cosentinoLanguage.setSelected(true);
        languageMenuButton.setText("Cosentino");

    }
    private void eurCurrencyChoosen(){
        eurCurrency.setSelected(true);
        usdCurrency.setSelected(false);
        currencyMenuButton.setText("EUR");
    }
    private void usdCurrencyChoosen(){
        eurCurrency.setSelected(false);
        usdCurrency.setSelected(true);
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

        if(h24Time.isSelected()) settings[0] = "HH:mm:ss";
        if(h12Time.isSelected()) settings[0] = "hh:mm:ss a";

        if(marketPage.isSelected()) settings[1] = "market";
        if(spotPage.isSelected()) settings[1] = "spot";

        if(stayLogged.isSelected()) settings[2] = "1";
        else settings[2] = "0";

        if(mvcTheme.isSelected()) settings[3] = "mvc.css";
        if(darkTheme.isSelected()) settings[3] = "dark.css";
        if(lightTheme.isSelected()) settings[3] = "light.css";
        if(blueTheme.isSelected()) settings[3] = "blue.css";

        if(italianLanguage.isSelected()) settings[4] = "it";
        if(englishLanguage.isSelected()) settings[4] = "en";
        if(frenchLanguage.isSelected()) settings[4] = "fr";
        if(spanishLanguage.isSelected()) settings[4] = "es";
        if(cosentinoLanguage.isSelected()) settings[4] = "cs";

        if(eurCurrency.isSelected()) settings[5] = "eur";
        if(usdCurrency.isSelected()) settings[5] = "usd";

        return settings;
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = LanguageHandler.getInstance().getBundle();
        } catch (Exception e){
            System.out.println("Error in SideBarController.java (rows: 101-105) " + e);
        }
        if(bundle!=null){
            pageLabel.setText(bundle.getString("pageLabel"));
            timeLabel.setText(bundle.getString("formatTimeLabel"));
            currencyLabel.setText(bundle.getString("currencyLabel"));
            languageLabel.setText(bundle.getString("languageLabel"));
            themeLabel.setText(bundle.getString("themeLabel"));
            signedLabel.setText(bundle.getString("staySignedLabel"));
            applyButton.setText(bundle.getString("applyButton"));
            backButton.setText(bundle.getString("backButton"));
        }
    }
}

