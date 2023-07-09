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

    @FXML
    private Label accessibilityLabel, accountLabel;

    @FXML
    private CheckBox stayLogged;

    @FXML
    private Label pageLabel, timeLabel, currencyLabel, languageLabel, themeLabel, signedLabel;
    @FXML
    private Button applyButton, backButton;

    @FXML
    private RadioMenuItem italianLanguage, englishLanguage,
            lightTheme, darkTheme, mvcTheme, marketPage, spotPage,
            h24Time, h12Time,
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
    void onItalianClick(){ italianLanguageChoosen(); }

    @FXML
    void onEnglishClick(){ englishLanguageChoosen(); }

    @FXML
    void onEurClick(){ eurCurrencyChoosen(); }

    @FXML
    void onUsdClick(){ usdCurrencyChoosen(); }


    @FXML
    void onSaveClick(){
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


        if(!settings[4].equals(settingsHandler.language) || !settings[3].equals(settingsHandler.theme)){
            settingsHandler.updateSettings();
            alertHandler.restartAppAlert();
        }else{
            settingsHandler.updateSettings();
            sceneHandler.createSideBar();
        }

       //MainApplication.restartApplication();

    }

    @FXML
    void onCancelClick(){
        sceneHandler.createSideBar();
    }

    @FXML
    void initialize(){
        updateLanguage();
        if(settingsHandler.format.equals("HH:mm:ss")) h24Choosen();
        if(settingsHandler.format.equals("hh:mm:ss a")) h12Choosen();

        if(settingsHandler.page.equals("spot")) spotChoosen();
        if(settingsHandler.page.equals("market")) marketChoosen();

        if(settingsHandler.logged) stayLogged();
        if(!settingsHandler.logged) noStayLogged();

        if(settingsHandler.theme.equals("mvc.css")) mvcThemeChoosen();
        if(settingsHandler.theme.equals("dark.css")) darkThemeChoosen();
        if(settingsHandler.theme.equals("light.css")) lightThemeChoosen();

        if(settingsHandler.language.equals("it")) italianLanguageChoosen();
        if(settingsHandler.language.equals("en")) englishLanguageChoosen();

        if(settingsHandler.currency.equals("eur")) eurCurrencyChoosen();
        if(settingsHandler.currency.equals("usd")) usdCurrencyChoosen();

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

    private void mvcThemeChoosen(){
        mvcTheme.setSelected(true);
        darkTheme.setSelected(false);
        lightTheme.setSelected(false);
        themeMenuButton.setText("MVC");
    }

    private void darkThemeChoosen(){
        mvcTheme.setSelected(false);
        darkTheme.setSelected(true);
        lightTheme.setSelected(false);
        themeMenuButton.setText("Dark");
    }

    private void lightThemeChoosen(){
        mvcTheme.setSelected(false);
        darkTheme.setSelected(false);
        lightTheme.setSelected(true);
        themeMenuButton.setText("Light");
    }

    private void italianLanguageChoosen(){
        italianLanguage.setSelected(true);
        englishLanguage.setSelected(false);
        languageMenuButton.setText("Italiano");
    }

    private void englishLanguageChoosen(){
        italianLanguage.setSelected(false);
        englishLanguage.setSelected(true);
        languageMenuButton.setText("English");
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

        if(italianLanguage.isSelected()) settings[4] = "it";
        if(englishLanguage.isSelected()) settings[4] = "en";

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

