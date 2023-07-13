package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.model.BarData;
import it.unical.demacs.informatica.mvcwallet.handler.CoinsHandler;
import it.unical.demacs.informatica.mvcwallet.model.BuildBarsService;
import it.unical.demacs.informatica.mvcwallet.view.CandleStickChart;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController {
    private final BuildBarsService buildBarsService = BuildBarsService.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final List<String> coinCodes = CoinsHandler.getInstance().getCoinCode();
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
    private MenuButton coinMenuButton, currencyMenuButton, timeMenuButton;

    @FXML
    private AnchorPane market;


    @FXML
    public void getSelectedtCoin(ActionEvent event) {
        String value = coinMenuButton.getText();
        this.coin = value;
        this.formattedCoin = value.toLowerCase().replaceAll(" ", "");
        updateChart();
    }

    @FXML
    public void getSelectedCurrency(ActionEvent event) {
        String value = currencyMenuButton.getText();
        this.currency = value;
        this.formattedCurrency = value.toLowerCase().replaceAll(" ", "");
        updateChart();
    }

    public void getSelectedTime(ActionEvent event) {
        this.timeframe = timeMenuButton.getText();
        updateChart();
    }

    @FXML
    void initialize() {
        Platform.runLater(this::updateLanguage);
        setItems();
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
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null) {
            currencyLabel.setText(bundle.getString("currencyLabel"));
            timeLabel.setText(bundle.getString("timeLabel"));
        }
    }

    private void setItems(){
        for(String code:coinCodes){
            MenuItem item = new MenuItem(code);
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

