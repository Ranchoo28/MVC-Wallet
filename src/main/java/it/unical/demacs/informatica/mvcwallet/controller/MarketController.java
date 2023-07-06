package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.model.BarData;
import it.unical.demacs.informatica.mvcwallet.model.BuildBars;
import it.unical.demacs.informatica.mvcwallet.view.CandleStickChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketController {
    private List<String> coins = new ArrayList<>(Arrays.asList("Bitcoin", "Ethereum", "Binancecoin", "Solana"));
    private List<String> currencies = new ArrayList<>(Arrays.asList("EUR", "USD"));
    private List<String> timeframes = new ArrayList<>(Arrays.asList("1W", "1M", "1Y"));
    private static String coin = "Bitcoin";
    private static String currency = "USD";
    private static String timeframe = "1M";

    @FXML
    private ChoiceBox<String> coinChoiceBox;

    @FXML
    private ChoiceBox<String> currencyChoiceBox;

    @FXML
    private ChoiceBox<String> timeChoiceBox;

    @FXML
    private AnchorPane market;

    public void updateChart(){
        market.getChildren().clear();

        List<BarData> array = BuildBars.getInstance().buildBars(coin.toLowerCase(), currency.toLowerCase(), timeframe);
        CandleStickChart chart = new CandleStickChart(array, market.getWidth());

        market.getChildren().add(chart);

        market.setTopAnchor(chart, 0.0);
        market.setRightAnchor(chart, 5.0);
        market.setBottomAnchor(chart, 5.0);
        market.setLeftAnchor(chart, 0.0);
    }

    public void addCoin(String coin){
        coins.add(coin);
    }

    public void removeCoin(String coin){
        if(coin.contains(coin)) coins.remove(coin);
    }

    public void addCurrency(String currency){
        currencies.add(currency);
    }

    public void removeCurrency(String currency){
        if(currencies.contains(currency)) currencies.remove(currency);
    }

    @FXML
    public void getSelectedtCoin(ActionEvent event) {
        this.coin = coinChoiceBox.getValue().toLowerCase();
        updateChart();
    }

    @FXML
    public void getSelectedCurrency(ActionEvent event) {
        this.currency = currencyChoiceBox.getValue().toLowerCase();
        updateChart();
    }

    public void getSelectedTime(ActionEvent event) {
        this.timeframe = timeChoiceBox.getValue();
        updateChart();
    }

    @FXML
    void initialize() {
        coinChoiceBox.getItems().addAll(coins);
        currencyChoiceBox.getItems().addAll(currencies);
        timeChoiceBox.getItems().addAll(timeframes);

        coinChoiceBox.setValue(coin);
        currencyChoiceBox.setValue(currency);
        timeChoiceBox.setValue(timeframe);

        coinChoiceBox.setOnAction(this::getSelectedtCoin);
        currencyChoiceBox.setOnAction(this::getSelectedCurrency);
        timeChoiceBox.setOnAction(this::getSelectedTime);

        updateChart();
    }
}

