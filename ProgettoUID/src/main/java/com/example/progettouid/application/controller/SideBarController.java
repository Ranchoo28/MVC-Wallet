package com.example.progettouid.application.controller;

import com.example.progettouid.application.handler.InfoHandler;
import com.example.progettouid.application.handler.SceneHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SideBarController {
    @FXML
    private Label labelUser;
    @FXML
    private VBox sideBar;
    @FXML
    private Label labelSettings;
    @FXML
    private Button logoutButton, moneyButton, cryptoButton;
    @FXML
    private BorderPane bp;
    @FXML
    private HBox boxInfo;
    @FXML
    private AnchorPane homeAP;
    @FXML
    private Label dateLabel, timeLabel, userLabel;

    @FXML
    void onCryptoClick() {
        try {
            bp.setCenter(FXMLLoader.load(getClass().getResource("/com/example/progettouid/crypto-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // da aggiustare
        //cryptoButton.setStyle("-fx-background-color: #ff9900");
        //moneyButton.setStyle("-fx-background-color: #3366ff");
    }

    @FXML
    void onLogoutClick() {
        SceneHandler.getInstance().createLogoutAlert("Sei sicuro di voler effettuare il logout?");
    }

    @FXML
    void onMoneyClick() {
        bp.setCenter(homeAP);
        //moneyButton.setStyle("-fx-background-color: #ff9900");
        //cryptoButton.setStyle("-fx-background-color: #3366ff");
    }

    @FXML
    void onSettingsClick() {
           SceneHandler.getInstance().createSettingsScene();
    }

    @FXML
    void initialize() {

        // Thread per calcolare ora e data sempre aggiornati.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                    dateLabel.setText(InfoHandler.getInstance().getDay());
                    timeLabel.setText(InfoHandler.getInstance().getTime());
                    userLabel.setText(InfoHandler.getInstance().getInfoDay());
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        });

        // Icona per l'utente
        FontIcon iconUsers = new FontIcon("mdi2e-emoticon-excited");
        iconUsers.setIconSize(25);
        userLabel.setContentDisplay(ContentDisplay.LEFT);
        userLabel.setGraphic(iconUsers);

        // Icona per l'ora
        FontIcon iconTime = new FontIcon("mdi2c-calendar-today");
        iconTime.setIconSize(25);
        dateLabel.setContentDisplay(ContentDisplay.LEFT);
        dateLabel.setGraphic(iconTime);

        // Icona per la data
        FontIcon iconDate = new FontIcon("mdi2c-clock");
        iconDate.setIconSize(25);
        timeLabel.setContentDisplay(ContentDisplay.LEFT);
        timeLabel.setGraphic(iconDate);

        // Icon del bottone dei settings
        FontIcon iconSettings = new FontIcon("mdi2c-cog"); //mid2c-cogs
        iconSettings.setIconSize(30);
        labelSettings.setContentDisplay(ContentDisplay.RIGHT);
        labelSettings.setGraphic(iconSettings);

        // Icona del bottone del logout
        FontIcon iconLogout = new FontIcon("mdi2a-account-off");
        iconLogout.setIconSize(25);
        logoutButton.setContentDisplay(ContentDisplay.RIGHT);
        logoutButton.setGraphic(iconLogout);

        // Icona del bottone dei soldi
        FontIcon iconMoney = new FontIcon("mdi2c-currency-usd-circle"); //mdi2c-cash-usd
        iconMoney.setIconSize(25);
        moneyButton.setContentDisplay(ContentDisplay.RIGHT);
        moneyButton.setGraphic(iconMoney);

        // Icona del bottone delle Crypto
        FontIcon iconCrypto = new FontIcon("mdi2b-bitcoin");
        iconCrypto.setIconSize(25);
        cryptoButton.setContentDisplay(ContentDisplay.RIGHT);
        cryptoButton.setGraphic(iconCrypto);

    }
}
