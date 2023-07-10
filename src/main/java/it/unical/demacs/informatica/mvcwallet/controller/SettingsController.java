package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane colorPickerPane;

    @FXML
    private MenuItem italianLanguage, englishLanguage,
            lightTheme, darkTheme, mvcTheme, marketPage, spotPage,
            h24Time, h12Time, blueTheme,
            eurCurrency, usdCurrency;

    @FXML private MenuButton mainPageMenuButton, timeFormatMenuButton,
            currencyMenuButton, languageMenuButton, themeMenuButton, colorPickerMenuButton;
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
    void onCustomClick() {
        customThemeChoosen();
    }
    @FXML
    void onColorPickerClick(ActionEvent event) {
        event.consume();
    }

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
        if(settingsHandler.format.equals("HH:mm:ss")) h24Choosen();
        if(settingsHandler.format.equals("hh:mm:ss a")) h12Choosen();

        if(settingsHandler.page.equals("spot")) spotChoosen();
        if(settingsHandler.page.equals("market")) marketChoosen();

        if(settingsHandler.logged) stayLogged();
        if(!settingsHandler.logged) noStayLogged();

        if(settingsHandler.theme.equals("mvc.css")) mvcThemeChoosen();
        if(settingsHandler.theme.equals("dark.css")) darkThemeChoosen();
        if(settingsHandler.theme.equals("light.css")) lightThemeChoosen();
        if(settingsHandler.theme.equals("blue.css")) blueThemeChoosen();

        if(settingsHandler.language.equals("it")) italianLanguageChoosen();

        if(settingsHandler.language.equals("en")) englishLanguageChoosen();

        if(settingsHandler.currency.equals("eur")) eurCurrencyChoosen();
        if(settingsHandler.currency.equals("usd")) usdCurrencyChoosen();
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
            AnchorPane.setBottomAnchor(pane, 0.0);

            colorPickerPane.setFocusTraversable(true);
        } catch (Exception e){
            System.out.println("Error in SideBarController.java (rows: 185-196) " + e);
        }

    }
    private void italianLanguageChoosen(){
        languageMenuButton.setText("Italiano");
    }
    private void englishLanguageChoosen(){
        languageMenuButton.setText("English");
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

