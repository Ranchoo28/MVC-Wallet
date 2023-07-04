package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.APIsHandler;
import it.unical.demacs.informatica.mvcwallet.handler.InfoHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.TimeStampHandler;
import it.unical.demacs.informatica.mvcwallet.model.BarData;
import it.unical.demacs.informatica.mvcwallet.model.CandleStickChart;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import java.net.MalformedURLException;
import java.util.*;

public class SideBarController {
    @FXML
    private MenuButton accessMenu;
    @FXML
    private Label labelUser;
    @FXML
    private Label labelSettings, moneyLabel, cryptoLabel, logoutLabel;
    @FXML
    private AnchorPane centerPage;
    @FXML
    private Label dateLabel, timeLabel, userLabel;

    public void loadFXML(String nomeFXML) throws IOException {

    }

    @FXML
    void onLogoutClick() {
        SceneHandler.getInstance().createLogoutAlert("Sei sicuro di voler effettuare il logout?");
    }

    @FXML
    void onMoneyClick() {
        return ;
    }

    @FXML
    void onSettingsClick() {
           SceneHandler.getInstance().createSettingsScene();
    }

    @FXML
    void onAccountClick() {
        SceneHandler.getInstance().createAccountSettingsScene();
    }

    @FXML
    void initialize() {
        // Gestione dell'ora e della data in tempo reale.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
                    dateLabel.setText(InfoHandler.getInstance().getDay());
                    timeLabel.setText(InfoHandler.getInstance().getTime());
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        });

        /*
        String [] array = SQLHandler.getIstance().getNameSurname(LoginController.username);
        userLabel.setText(array[0] + " " + array[1]);
         */

        // Icona per l'utente
        FontIcon iconUsers = new FontIcon("mdi2a-account-circle");
        iconUsers.setIconSize(25);
        userLabel.setContentDisplay(ContentDisplay.LEFT);
        iconUsers.setIconColor(Paint.valueOf("#fff"));
        userLabel.setGraphic(iconUsers);

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
        labelSettings.setContentDisplay(ContentDisplay.LEFT);
        iconSettings.setIconColor(Paint.valueOf("#fff"));
        labelSettings.setGraphic(iconSettings);

        //icona dell'accessibilità
        //https://stackoverflow.com/questions/17467137/how-can-i-create-a-switch-button-in-javafx
        //Qua dentro dobbiamo mettere gli interruttori per attivare le opzioni di fianco alle
        //label Alto contrasto, Testo largo ecc...
        //Quelle che ci sono non vanno bene
        FontIcon accessIcon = new FontIcon("mdi2h-human");
        accessIcon.setIconSize(25);
        accessIcon.setIconColor(Paint.valueOf("#fff"));
        accessMenu.setContentDisplay(ContentDisplay.CENTER);
        accessMenu.setGraphic(accessIcon);

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
        moneyLabel.setContentDisplay(ContentDisplay.LEFT);
        moneyLabel.setGraphic(iconMoney);

        // Icona del bottone delle Crypto
        FontIcon iconCrypto = new FontIcon("mdi2b-bitcoin");
        iconCrypto.setIconSize(25);
        iconCrypto.setIconColor(Paint.valueOf("#fff"));
        cryptoLabel.setContentDisplay(ContentDisplay.LEFT);
        cryptoLabel.setGraphic(iconCrypto);

    }

    @FXML
    void onCryptoClick() throws IOException {
        String path = "/it/unical/demacs/informatica/mvcwallet/view/crypto-view.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        AnchorPane pane = fxmlLoader.load();

        List<BarData> array = buildBars();
        CandleStickChart chart = new CandleStickChart("SIUM", array, centerPage.getWidth());

        centerPage.getChildren().add(chart);
        centerPage.setTopAnchor(chart, 5.0);
        centerPage.setRightAnchor(chart, 5.0);
        centerPage.setBottomAnchor(chart, 5.0);
        centerPage.setLeftAnchor(chart, 5.0);
    }

    public List<BarData> buildBars() {

        double previusClose = 1850;

        final List<BarData> bars = new ArrayList<>();
        Map<String, ArrayList<Double>> dictionary;

        try {
            dictionary = APIsHandler.getInstance().getHistoricalData();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        for(String key : dictionary.keySet()){
            ArrayList<Double> dailyPrices = dictionary.get(key);

            double highPrice = Collections.max(dailyPrices);
            double lowPrice = Collections.min(dailyPrices);
            double openPrice = dailyPrices.get(0);
            double closePrice = dailyPrices.get(dailyPrices.size()-1);

            GregorianCalendar date = TimeStampHandler.getInstance().convertToGregorianCalendar(key);
            BarData bar = new BarData(date, openPrice, highPrice, lowPrice, closePrice, 1);
            bars.add(bar);
        }
        bars.sort(Comparator.comparing(BarData::getDateTime));
        return bars;
    }
}
