package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.model.BarData;
import it.unical.demacs.informatica.mvcwallet.handler.CoinsHandler;
import it.unical.demacs.informatica.mvcwallet.model.BuildBarsService;
import it.unical.demacs.informatica.mvcwallet.model.Coin;
import it.unical.demacs.informatica.mvcwallet.view.CandleStickChart;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class MarketController {
    private final BuildBarsService buildBarsService = BuildBarsService.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final Map<String, Coin> coins = CoinsHandler.getInstance().getCoins();
    private final List<String> currencies = new ArrayList<>(Arrays.asList("EUR", "USD"));
    private final List<String> timeframes = new ArrayList<>(Arrays.asList("1D", "1W", "1M", "1Y"));
    private final String coin = "BTC";
    private String formattedCoin = formatCoin(coins.get(coin).getName());
    private String currency = currencies.get(0);
    private String formattedCurrency = formatCurrency(currency);
    private String timeframe = timeframes.get(0);

    @FXML
    private Label currencyLabel, timeLabel;

    @FXML
    private MenuButton coinMenuButton, currencyMenuButton, timeMenuButton;

    @FXML
    private AnchorPane market;

    private String formatCoin(String coinName){
        return coinName.toLowerCase().replaceAll(" ", "");
    }

    private String formatCurrency(String coinName){
        return coinName.toLowerCase().replaceAll(" ", "");
    }


    public void getSelectedtCoin() {
        String key = coinMenuButton.getText();
        String coinName = coins.get(key).getName();
        this.formattedCoin = formatCoin(coinName);
        updateChart();
    }

    public void getSelectedCurrency() {
        this.currency = currencyMenuButton.getText();;
        this.formattedCurrency = formatCurrency(this.currency);
        updateChart();
    }

    public void getSelectedTime() {
        this.timeframe = timeMenuButton.getText();
        updateChart();
    }

    @FXML
    void initialize() {
        Platform.runLater(this::updateLanguage);
        setItems();
        addListner();
        updateChart();
    }

    public void addListner(){
        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            getSelectedtCoin();
            getSelectedCurrency();
            getSelectedTime();
        };

        coinMenuButton.textProperty().addListener(listener);
        currencyMenuButton.textProperty().addListener(listener);
        timeMenuButton.textProperty().addListener(listener);

    }

    public void updateChart(){
        this.market.getChildren().clear();

        List<BarData> array = buildBarsService.buildBars(this.formattedCoin, this.formattedCurrency, this.timeframe);
        CandleStickChart chart = new CandleStickChart(array, market.getWidth());

        this.market.getChildren().add(chart);

        AnchorPane.setTopAnchor(chart, 0.0);
        AnchorPane.setRightAnchor(chart, 5.0);
        AnchorPane.setBottomAnchor(chart, 5.0);
        AnchorPane.setLeftAnchor(chart, 0.0);
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = languageHandler.getBundle();
        } catch (Exception e){
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null) {
            currencyLabel.setText(bundle.getString("currencyLabel"));
            timeLabel.setText(bundle.getString("timeLabel"));
        }
    }

    private void setItems(){
        coinMenuButton.setText(this.coin);
        currencyMenuButton.setText(this.currency);
        timeMenuButton.setText(this.timeframe);

        for (String key : coins.keySet()) {
            MenuItem item = new MenuItem(key);
            item.setOnAction(event -> {
                coinMenuButton.setText(item.getText());
            });
            coinMenuButton.getItems().add(item);
        }

        for(String cur:currencies){
            MenuItem item = new MenuItem(cur);
            item.setOnAction(event -> {
                currencyMenuButton.setText(item.getText());
            });
            currencyMenuButton.getItems().add(item);
        }

        for(String time:timeframes){
            MenuItem item = new MenuItem(time);
            item.setOnAction(event -> {
                timeMenuButton.setText(item.getText());
            });
            timeMenuButton.getItems().add(item);
        }
    }
}

