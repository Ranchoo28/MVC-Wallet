package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.model.BarData;
import it.unical.demacs.informatica.mvcwallet.handler.CoinsHandler;
import it.unical.demacs.informatica.mvcwallet.model.BuildBarsService;
import it.unical.demacs.informatica.mvcwallet.view.CandleStickChart;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController {
    private final BuildBarsService buildBarsService = BuildBarsService.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    private final List<String> coins = CoinsHandler.getInstance().getCoinNames();
    private final List<String> currencies = new ArrayList<>(Arrays.asList("EUR", "USD"));
    private final List<String> timeframes = new ArrayList<>(Arrays.asList("1D", "1W", "1M", "1Y"));
    private String coin = "Bitcoin";
    private String formattedCoin = "bitcoin";
    private String currency = "USD";
    private String formattedCurrency = "usd";
    private String timeframe = "1D";

    @FXML
    private Label currencyLabel, timeLabel;

    @FXML
    private ChoiceBox<String> coinChoiceBox;

    @FXML
    private ChoiceBox<String> currencyChoiceBox;

    @FXML
    private ChoiceBox<String> timeChoiceBox;

    @FXML
    private AnchorPane market;


    @FXML
    public void getSelectedtCoin(ActionEvent event) {
        String value = coinChoiceBox.getValue();
        this.coin = value;
        this.formattedCoin = value.toLowerCase().replaceAll(" ", "");
        updateChart();
    }

    @FXML
    public void getSelectedCurrency(ActionEvent event) {
        String value = currencyChoiceBox.getValue();
        this.currency = value;
        this.formattedCurrency = value.toLowerCase().replaceAll(" ", "");
        updateChart();
    }

    public void getSelectedTime(ActionEvent event) {
        this.timeframe = timeChoiceBox.getValue();
        updateChart();
    }

    @FXML
    void initialize() {
        Platform.runLater(this::updateLanguage);
        //updateLanguage();

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
            System.out.println("Error in MarketController.java (rows: 103-107) " + e);
        }
        if(bundle!=null) {
            currencyLabel.setText(bundle.getString("currencyLabel"));
            timeLabel.setText(bundle.getString("timeLabel"));
        } else {
            System.out.println("MarketController.java: bundle is null");
        }
    }
}

