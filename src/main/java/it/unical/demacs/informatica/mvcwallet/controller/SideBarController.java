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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class SideBarController {
    @FXML
    private AnchorPane centerPage;
    @FXML
    private HBox infoHBox, logoutHBox, marketHBox, spotHBox;
    @FXML
    private Label dateLabel, logoutLabel, marketLabel, settingsLabel, spotLabel, timeLabel, userLabel;
    @FXML
    private Label accessLabel;
    @FXML
    private MenuButton accessMenuButton;
    @FXML
    private VBox sideBar;
    @FXML
    private JFXToggleButton highContrastButton, largeTextButton, screenReaderButton;

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

        if (SettingsHandler.getInstance().page.equals("spot")){
            spotHBox.setDisable(true);
            marketHBox.setDisable(false);
            loadFXML("spot-view.fxml");
        }
        if (SettingsHandler.getInstance().page.equals("market")){
            spotHBox.setDisable(false);
            marketHBox.setDisable(true);
            loadFXML("market-view.fxml");
        }

        // Gestione dell'ora e della data in tempo reale.
        Platform.runLater(() -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
                dateLabel.setText(SettingsHandler.getInstance().getDay());
                timeLabel.setText(SettingsHandler.getInstance().timeFormatter());
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });

        /* FIXME
        String [] array = SqlHandler.getIstance().getNameSurname(LoginController.username);
        userLabel.setText(array[0] + " " + array[1]);
         */


        // Icona per l'utente
        FontIcon iconUsers = new FontIcon("mdi2a-account-circle");
        iconUsers.setIconSize(25);
        userLabel.setContentDisplay(ContentDisplay.LEFT);
        iconUsers.setIconColor(Paint.valueOf("#fff"));
        userLabel.setGraphic(iconUsers);
        String[] nameSurname = SqlHandler.getInstance().getNameSurname(LoginController.username);
        String first = nameSurname[0];
        String last = nameSurname[1];
        userLabel.setText(" "+first+" "+last);

        // Icona per l'ora
        FontIcon iconTime = new FontIcon("mdi2c-calendar-today");
        iconTime.setIconSize(25);
        dateLabel.setContentDisplay(ContentDisplay.LEFT);
        iconTime.setIconColor(Paint.valueOf("#fff"));
        dateLabel.setGraphic(iconTime);

        // Icona per la data
        FontIcon iconDate = new FontIcon("mdi2c-clock");
        iconDate.setIconSize(25);
        timeLabel.setContentDisplay(ContentDisplay.LEFT);
        iconDate.setIconColor(Paint.valueOf("#fff"));
        timeLabel.setGraphic(iconDate);

        // Icon del bottone dei settings
        FontIcon iconSettings = new FontIcon("mdi2c-cog"); //mid2c-cogs
        iconSettings.setIconSize(25);
        settingsLabel.setContentDisplay(ContentDisplay.LEFT);
        iconSettings.setIconColor(Paint.valueOf("#fff"));
        settingsLabel.setGraphic(iconSettings);

        //icona dell'accessibilità
        //https://stackoverflow.com/questions/17467137/how-can-i-create-a-switch-button-in-javafx
        //Qua dentro dobbiamo mettere gli interruttori per attivare le opzioni di fianco alle
        //label Alto contrasto, Testo largo ecc...
        FontIcon accessIcon = new FontIcon("mdi2h-human");
        accessIcon.setIconSize(25);
        accessLabel.setContentDisplay(ContentDisplay.RIGHT);
        accessIcon.setIconColor(Paint.valueOf("#fff"));
        accessLabel.setGraphic(accessIcon);

        // Icona del bottone del logout
        FontIcon iconLogout = new FontIcon("mdi2a-account-off");
        iconLogout.setIconSize(25);
        logoutLabel.setContentDisplay(ContentDisplay.LEFT);
        iconLogout.setIconColor(Paint.valueOf("#fff"));
        logoutLabel.setGraphic(iconLogout);

        // Icona del bottone dei soldi
        FontIcon iconMoney = new FontIcon("mdi2c-currency-usd-circle"); //mdi2c-cash-usd
        iconMoney.setIconSize(25);
        iconMoney.setIconColor(Paint.valueOf("#fff"));
        spotLabel.setContentDisplay(ContentDisplay.LEFT);
        spotLabel.setGraphic(iconMoney);

        // Icona del bottone delle Crypto
        FontIcon iconCrypto = new FontIcon("mdi2b-bitcoin");
        iconCrypto.setIconSize(25);
        iconCrypto.setIconColor(Paint.valueOf("#fff"));
        marketLabel.setContentDisplay(ContentDisplay.LEFT);
        marketLabel.setGraphic(iconCrypto);
    }

    public void loadFXML(String nomeFXML) throws IOException {
        centerPage.getChildren().clear();

        try{

            String path = PathHandler.getInstance().getPathOfView();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path + nomeFXML));
            AnchorPane pane = fxmlLoader.load();

            centerPage.getChildren().add(pane);
            AnchorPane.setTopAnchor(pane, 5.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}


