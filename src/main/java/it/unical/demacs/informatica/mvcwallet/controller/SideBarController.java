package it.unical.demacs.informatica.mvcwallet.controller;

import com.jfoenix.controls.JFXToggleButton;
import it.unical.demacs.informatica.mvcwallet.handler.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ResourceBundle;

public class SideBarController {
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    String pathOfFont = PathHandler.getInstance().getPathOfFont();
    String pathOfView = PathHandler.getInstance().getPathOfView();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SettingsHandler settingsHandler = SettingsHandler.getInstance();
    @FXML
    private AnchorPane centerPage;
    @FXML
    private HBox infoHBox, logoutHBox, marketHBox, spotHBox;
    @FXML
    private JFXToggleButton highContrastButton, largeTextButton, screenReaderButton;
    @FXML
    private Label dateLabel, logoutLabel, marketLabel, settingsLabel, spotLabel, timeLabel, userLabel;
    @FXML
    private Label dateIcon, logoutIcon, marketIcon, settingIcon, spotIcon, timeIcon, userIcon, accessMenuIcon;
    @FXML
    private VBox sideBar;

    @FXML
    private void onHighContrastClick(ActionEvent event){
        event.consume(); // Impedisce la chiusura del MenuButton
    }
    @FXML
    private void onLargeTextClick(ActionEvent event){
        event.consume(); // Impedisce la chiusura del MenuButton
    }
    @FXML
    private void onScreenReaderClick(ActionEvent event){
        event.consume(); // Impedisce la chiusura del MenuButton
    }
    @FXML
    void onLogoutClick() {
        alertHandler.createLogoutAlert(languageHandler.getBundle().getString("logoutText"));
    }

    @FXML
    void onSpotClick() throws IOException {
        userLabel.setDisable(false);
        settingsLabel.setDisable(false);
        spotHBox.setDisable(true);
        marketHBox.setDisable(false);
        loadFXML("spot-view.fxml");
    }

    @FXML
    void onMarketClick() throws IOException {
        userLabel.setDisable(false);
        settingsLabel.setDisable(false);
        spotHBox.setDisable(false);
        marketHBox.setDisable(true);
        loadFXML("market-view.fxml");
    }

    @FXML
    void onSettingsClick() throws IOException {
        userLabel.setDisable(false);
        settingsLabel.setDisable(true);
        spotHBox.setDisable(false);
        marketHBox.setDisable(false);
        loadFXML("settings-view.fxml");
    }

    @FXML
    void onAccountClick() throws IOException {
        userLabel.setDisable(true);
        settingsLabel.setDisable(false);
        spotHBox.setDisable(false);
        marketHBox.setDisable(false);
        loadFXML("profile-view.fxml");
    }

    @FXML
    void initialize() throws IOException {

        Thread languageThread = new Thread(this::updateLanguage);

        Thread timeThread = new Thread(() -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
                dateLabel.setText(settingsHandler.getDay());
                timeLabel.setText(settingsHandler.timeFormatter());
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });

        if (settingsHandler.page != null) {
            if (settingsHandler.page.equals("spot")) {
                spotHBox.setDisable(true);
                marketHBox.setDisable(false);
                try {
                    loadFXML("spot-view.fxml");
                } catch (Exception e) {
                    System.out.println("Error in SideBarController.java (rows: 109-113) " + e);
                }
            }
            if (settingsHandler.page.equals("market")) {
                spotHBox.setDisable(false);
                marketHBox.setDisable(true);
                try {
                    loadFXML("market-view.fxml");
                } catch (Exception e) {
                    System.out.println("Error in SideBarController.java (rows: 119-123) " + e);
                }
            }
        } else {
            System.out.println(settingsHandler.username);
            System.out.println("settingsHandler.page: null");
            loadFXML("market-view.fxml");
        }

        Font font = null;
        try {
            font = Font.loadFont(String.valueOf(getClass().getResource(pathOfFont+"fa-solid-900.ttf")), 20);
        } catch (Exception e){
            System.out.println("Error in SideBarController.java (rows: 115-121) " + e);
        }

        // Icona per l'utente
        userIcon.setText("\uF007");
        userIcon.setFont(font);

        String[] nameSurname = sqlHandler.getNameSurname(LoginController.username);
        String first = nameSurname[0];
        String last = nameSurname[1];

        userLabel.setText(" " + first + " " + last);

        // Icona per la data
        dateIcon.setText("\uF073");
        dateIcon.setFont(font);

        // Icona per l'ora
        timeIcon.setText("\uF017");
        timeIcon.setFont(font);

        // Icona del bottone dei settings
        settingIcon.setText("\uF013");
        settingIcon.setFont(font);

        // Icona dell'accessibilitá uF183 uF256
        accessMenuIcon.setText("\uF183");
        accessMenuIcon.setFont(font);

        // Icona dello spot
        spotIcon.setText("\uF555");
        spotIcon.setFont(font);

        // Icona del mercato
        marketIcon.setText("\uE0B4");
        spotIcon.setFont(font);

        //Icona del bottone del logout
        logoutIcon.setText("\uF2F5");
        logoutIcon.setFont(font);

        languageThread.start();
        timeThread.start();

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
            System.out.println("Error in SideBarController.java (rows: 166-178) " + e);
        }
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = languageHandler.getBundle();
        } catch (Exception e){
            System.out.println("Error in SideBarController.java (rows: 184-188) " + e);
        }
        if(bundle!=null) {
            settingsLabel.setText(bundle.getString("settingsLabel"));
            logoutLabel.setText(bundle.getString("logoutLabel"));
        } else {
            System.out.println("SideBarController.java: bundle is null");
        }
    }
}


