package it.unical.demacs.informatica.mvcwallet.controller;

import com.jfoenix.controls.JFXToggleButton;
import it.unical.demacs.informatica.mvcwallet.handler.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SideBarController {
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    String pathOfFont = PathHandler.getInstance().getPathOfFont();
    String pathOfView = PathHandler.getInstance().getPathOfView();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SettingsHandler settingsHandler = SettingsHandler.getInstance();
    private final SettingsHandler settingsController = SettingsHandler.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();
    private final Timer Timer = new Timer();
    private final Font font = Font.loadFont(String.valueOf(getClass().getResource(pathOfFont+"fa-solid-900.ttf")), 20);

    @FXML
    private AnchorPane centerPage;
    @FXML
    private HBox infoHBox, logoutHBox, marketHBox, spotHBox;
    @FXML
    private JFXToggleButton highContrastButton, largeTextButton, screenReaderButton;
    @FXML
    private Label dateLabel, logoutLabel, marketLabel, settingsLabel, spotLabel, timeLabel, userLabel;
    @FXML
    private Label dateIcon, logoutIcon, marketIcon, settingIcon, spotIcon, timeIcon, userIcon;
    @FXML
    private VBox sideBar;

    @FXML
    private void onHighContrastClick(ActionEvent event){ event.consume();}// Impedisce la chiusura del MenuButton

    @FXML
    private void onLargeTextClick(ActionEvent event){ event.consume(); } // Impedisce la chiusura del MenuButton

    @FXML
    private void onScreenReaderClick(ActionEvent event){ event.consume(); } // Impedisce la chiusura del MenuButton

    @FXML
    void onLogoutClick(){ alertHandler.createLogoutAlert(languageHandler.getBundle().getString("logoutText")); }

    @FXML
    void onSpotClick(){
        userLabel.setDisable(false);
        settingsLabel.setDisable(false);
        spotHBox.setDisable(true);
        marketHBox.setDisable(false);
        loadFXML("spot-view.fxml");
    }

    @FXML
    void onMarketClick(){
        userLabel.setDisable(false);
        settingsLabel.setDisable(false);
        spotHBox.setDisable(false);
        marketHBox.setDisable(true);
        loadFXML("market-view.fxml");
    }

    @FXML
    void onSettingsClick(){
        userLabel.setDisable(false);
        settingsLabel.setDisable(true);
        spotHBox.setDisable(false);
        marketHBox.setDisable(false);
        loadFXML("settings-view.fxml");
    }

    @FXML
    void onAccountClick(){
        userLabel.setDisable(true);
        settingsLabel.setDisable(false);
        spotHBox.setDisable(false);
        marketHBox.setDisable(false);
        loadFXML("profile-view.fxml");
    }

    @FXML
    void initialize() throws IOException {
        updateLanguage();
        loadPage();
        loadFont();
        loadNameUsername();
        loadTimeDayManager();
    }

    // Metodi
    public void loadPage(){
        switch (settingsHandler.page){
            case "spot" -> {
                spotHBox.setDisable(true);
                marketHBox.setDisable(false);
                loadFXML("spot-view.fxml");
            }
            case "market" -> {
                spotHBox.setDisable(false);
                marketHBox.setDisable(true);
                loadFXML("market-view.fxml");
            }
            case "settings" -> {
                spotHBox.setDisable(true);
                marketHBox.setDisable(true);
                loadFXML("settings-view.fxml");
            }
        }
    }

    private void loadFont(){
        // Icona per l'utente
        userIcon.setText("\uF007");
        userIcon.setFont(font);

        // Icona per la data
        dateIcon.setText("\uF073");
        dateIcon.setFont(font);

        // Icona per l'ora
        timeIcon.setText("\uF017");
        timeIcon.setFont(font);

        // Icona del bottone dei settings
        settingIcon.setText("\uF013");
        settingIcon.setFont(font);

        // Icona dello spot
        spotIcon.setText("\uF555");
        spotIcon.setFont(font);

        // Icona del mercato
        marketIcon.setText("\uE0B4");
        spotIcon.setFont(font);

        //Icona del bottone del logout
        logoutIcon.setText("\uF2F5");
        logoutIcon.setFont(font);
    }

    private void loadNameUsername(){
        try{
            String[] nameSurname = sqlHandler.getNameSurname(LoginController.username);
            String first = nameSurname[0];
            String last = nameSurname[1];
            userLabel.setText(" " + first + " " + last);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private void loadTimeDayManager(){
        Timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    String data = settingsHandler.getDay();
                    if(!data.equals(dateLabel.getText()))
                        dateLabel.setText(data);
                    timeLabel.setText(settingsHandler.timeFormatter());
                });
            }
        }, 0, 1000);
    }

    public void loadFXML(String nomeFXML) {
        centerPage.getChildren().clear();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathOfView + nomeFXML));
            AnchorPane pane = fxmlLoader.load();

            centerPage.getChildren().add(pane);
            AnchorPane.setTopAnchor(pane, 5.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
        } catch (Exception e){
            e.printStackTrace();
            alertHandler.createErrorAlert(bundle.getString("menuErrorAlert"));
        }
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = languageHandler.getBundle();
        } catch (Exception e){
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null) {
            settingsLabel.setText(bundle.getString("settingsLabel"));
            logoutLabel.setText(bundle.getString("logoutLabel"));
        }
    }
}


