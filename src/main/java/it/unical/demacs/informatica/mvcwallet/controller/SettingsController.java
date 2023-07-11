package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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
            currencyMenuButton, languageMenuButton;
    @FXML
    protected MenuButton themeMenuButton;
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
    void onBackClick(){
        themeChooser();
    }
    @FXML
    void onCustomClick() {
        themeEditor();
    }
    void themeChooser(){
        themeMenuButton.setText(settingsHandler.theme);
        cpMenuItem.setVisible(false);
        backMenuItem.setVisible(cpMenuItem.isVisible());
        darkMenuItem.setVisible(!cpMenuItem.isVisible());
        lightMenuItem.setVisible(!cpMenuItem.isVisible());
        mvcMenuItem.setVisible(!cpMenuItem.isVisible());
        blueMenuItem.setVisible(!cpMenuItem.isVisible());
        customMenuItem.setVisible(!cpMenuItem.isVisible());
    }
    void themeEditor(){
        themeMenuButton.setText("Custom");
        darkMenuItem.setVisible(cpMenuItem.isVisible());
        lightMenuItem.setVisible(cpMenuItem.isVisible());
        mvcMenuItem.setVisible(cpMenuItem.isVisible());
        blueMenuItem.setVisible(cpMenuItem.isVisible());
        customMenuItem.setVisible(cpMenuItem.isVisible());
        cpMenuItem.setVisible(true);
        backMenuItem.setVisible(cpMenuItem.isVisible());
        customThemeChoosen();
    }
    @FXML
    void onColorPickerClick(ActionEvent event) {
        event.consume();
    }

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

    @FXML
    void onEurClick(){ eurCurrencyChoosen(); }

    @FXML
    void handleMenuValidation(ActionEvent event) {
        timeFormatMenuButton.show();
    }

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
        timeFormatMenuButton.setText("12H");
    }

    private void h24Choosen(){
        timeFormatMenuButton.setText("24H");
    }

    private void spotChoosen(){
        mainPageMenuButton.setText("Spot");
    }

    private void marketChoosen(){
        mainPageMenuButton.setText("Market");
    }
    private void blueThemeChoosen(){
        themeMenuButton.setText("Blue");
    }
    private void mvcThemeChoosen(){
        themeMenuButton.setText("MVC");
    }
    private void darkThemeChoosen(){
        themeMenuButton.setText("Dark");

    }
    private void lightThemeChoosen(){
        themeMenuButton.setText("Light");
    }
    private void customThemeChoosen(){
        colorPickerPane.getChildren().clear();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathHandler.getInstance().getPathOfView() + "color-picker-view.fxml"));
            AnchorPane pane = fxmlLoader.load();

            colorPickerPane.getChildren().add(pane);

            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);

        } catch (Exception e){
            System.out.println("Error in SettingsController.java (rows: 203-216) " + e);
        }

    }
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

        if(timeFormatMenuButton.getText().equals("24H")) settings[0] = "HH:mm:ss";
        if(timeFormatMenuButton.getText().equals("12H")) settings[0] = "hh:mm:ss a";

        if(mainPageMenuButton.getText().equals("Market")) settings[1] = "market";
        if(mainPageMenuButton.getText().equals("Spot")) settings[1] = "spot";

        if(stayLogged.isSelected()) settings[2] = "1";
        else settings[2] = "0";

        if(themeMenuButton.getText().equals("MVC")) settings[3] = "mvc.css";
        if(themeMenuButton.getText().equals("Dark")) settings[3] = "dark.css";
        if(themeMenuButton.getText().equals("Light")) settings[3] = "light.css";
        if(themeMenuButton.getText().equals("Blue")) settings[3] = "blue.css";

        if(languageMenuButton.getText().equals("Italiano")) settings[4] = "it";
        if(languageMenuButton.getText().equals("English")) settings[4] = "en";
        if(languageMenuButton.getText().equals("Español")) settings[4] = "es";
        if(languageMenuButton.getText().equals("Français")) settings[4] = "fr";
        if(languageMenuButton.getText().equals("Cosentino")) settings[4] = "cs";

        if(currencyMenuButton.getText().equals("EUR")) settings[5] = "eur";
        if(currencyMenuButton.getText().equals("USD")) settings[5] = "usd";

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

