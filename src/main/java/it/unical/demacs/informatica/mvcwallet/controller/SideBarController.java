package it.unical.demacs.informatica.mvcwallet.controller;

import com.jfoenix.controls.JFXToggleButton;
import it.unical.demacs.informatica.mvcwallet.handler.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SideBarController {
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
        SceneHandler.getInstance().createLogoutAlert("Sei sicuro di voler effettuare il logout?");
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

        Thread timeThread = new Thread(() -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
                dateLabel.setText(SettingsHandler.getInstance().getDay());
                timeLabel.setText(SettingsHandler.getInstance().timeFormatter());
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });

        Thread languageThread = new Thread(this::updateLanguage);

        if (SettingsHandler.getInstance().page.equals("spot")) {
            spotHBox.setDisable(true);
            marketHBox.setDisable(false);
            loadFXML("spot-view.fxml");
        }
        if (SettingsHandler.getInstance().page.equals("market")) {
            spotHBox.setDisable(false);
            marketHBox.setDisable(true);
            loadFXML("market-view.fxml");
        }

        String pathFont = PathHandler.getInstance().getPathOfFont();
        Font font = null;
        try {
            font = Font.loadFont(String.valueOf(getClass().getResource(pathFont+"fa-solid-900.ttf")), 20);
        } catch (Exception e){
            System.out.println("Error in SideBarController.java (rows: 115-121) " + e);
        }

        // Icona per l'utente
        userIcon.setText("\uF007");
        userIcon.setFont(font);

        String[] nameSurname = SqlHandler.getInstance().getNameSurname(LoginController.username);
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

    public void loadFXML(String nomeFXML) throws IOException {
        centerPage.getChildren().clear();
        try {
            String path = PathHandler.getInstance().getPathOfView();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path + nomeFXML));
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
        try{
            settingsLabel.setText(LanguageHandler.getInstance().getBundle().getString("settingsLabel"));
        } catch (Exception e){
            System.out.println("Error in SideBarController.java (rows: 184-188) " + e);
        }
        try{
            logoutLabel.setText(LanguageHandler.getInstance().getBundle().getString("logoutLabel"));
        }catch (Exception e) {
            System.out.println("Error in SideBarController.java (rows: 189-193) " + e);
        }
    }
}


