package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import it.unical.demacs.informatica.mvcwallet.model.Coin;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class SpotController {
    private final APIsHandler apisHandler = APIsHandler.getInstance();
    private final SqlService sqlService = SqlService.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    private ObservableList<Coin> A;
    double [] spots = sqlHandler.getSpots(LoginController.username);
    @FXML
    private TextField amountTextField;
    @FXML
    private Button cancelButton, confirmButton, depositButton, withdrawButton;
    @FXML
    private HBox makeTransfer;
    @FXML
    private Label balanceLabel, operationLabel, totalLabel, currencyLabel, depositLabel, withdrawLabel, confirmLable, cancelLable;
    @FXML
    private MenuButton coinMenuButton;
    @FXML
    private TableColumn<Coin, Double> equivalentColumn;
    @FXML
    private TableColumn<Coin, String> coinColmun;
    @FXML
    private TableColumn<Coin, Double> amountColumn;
    @FXML
    private TableView<Coin> coinTable;
    private int x=0;

    @FXML
    void onDepositClick() {
        makeTransfer.setVisible(true);
        amountTextField.clear();
        operationLabel.setText("Deposit");
        x=1;
    }

    @FXML
    void onWithdrawClick() {
        makeTransfer.setVisible(true);
        amountTextField.clear();
        operationLabel.setText("Withdraw");
        x=-1;
    }

    @FXML
    void onCancelClick(){
        makeTransfer.setVisible(false);
        amountTextField.clear();
    }
    @FXML
    void onConfirmClick(){
        double amount=Double.parseDouble(amountTextField.getText());
        switch (coinMenuButton.getText()){
            case "BTC"->{

                double newAmount = spots[0] + (x * amount);
                if (checkNegativity(newAmount)) {
                    sqlService.serviceSpotBTC(LoginController.username,newAmount);
                }else{
                    alertHandler.createErrorAlert("ERROR: the amount of coins that you are trying to withdraw are higher than your current amount");
                }
            }
            case "ETH"->{
                double newAmount = spots[1] + (x * amount);
                if (checkNegativity(newAmount)) {
                    sqlService.serviceSpotETH(LoginController.username,newAmount);
                }else{
                    alertHandler.createErrorAlert("ERROR: the amount of coins that you are trying to withdraw are higher than your current amount");
                }
            }
            case "SOL"->{
                double newAmount = spots[2] + (x * amount);
                if (checkNegativity(newAmount)) {
                    sqlService.serviceSpotSOL(LoginController.username,newAmount);
                }else{
                    alertHandler.createErrorAlert("ERROR: the amount of coins that you are trying to withdraw are higher than your current amount");
                }
            }
            case "BNB"->{
                double newAmount = spots[3] + (x * amount);
                if (checkNegativity(newAmount)) {
                    sqlService.serviceSpotBNB(LoginController.username,newAmount);
                }else{
                    alertHandler.createErrorAlert("ERROR: the amount of coins that you are trying to withdraw are higher than your current amount");
                }
            }
        }

        sceneHandler.createSideBar();
    }
    private Coin BTC, ETH, SOL, BNB;
    private double getCurrentPriceSpot(String code,String currency){
        return apisHandler.getCurrentPrice(code,currency);
    }
    @FXML
    void initialize(){
        updateLanguage();
        BTC = new Coin("Bitcoin",spots[0], "BTC",(spots[0]*getCurrentPriceSpot(BTC.getCode(), SettingsHandler.getInstance().currency)));
        ETH = new Coin("Ethereum",spots[1], "ETH",(spots[1]*getCurrentPriceSpot(BTC.getCode(), SettingsHandler.getInstance().currency)));
        SOL = new Coin("Solana",spots[2], "SOL",(spots[2]*getCurrentPriceSpot(BTC.getCode(), SettingsHandler.getInstance().currency)));
        BNB = new Coin("Binance Coin",spots[3], "BNB",(spots[3]*getCurrentPriceSpot(BTC.getCode(), SettingsHandler.getInstance().currency)));
        A = FXCollections.observableArrayList();
        A.addAll(BTC,ETH,SOL,BNB);
        coinTable.setItems(A);
        System.out.println(getCurrentPriceSpot("BTC","EUR"));

        for(Coin i: A){
            MenuItem item = new MenuItem(i.getCode());
            item.setOnAction(event -> {
                coinMenuButton.setText(item.getText());
            });
            coinMenuButton.getItems().add(item);
        }
        amountTextField.setDisable(true);
        addListenerCoin();
        coinColmun.setCellValueFactory(new PropertyValueFactory<>("Name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        equivalentColumn.setCellValueFactory(new PropertyValueFactory<>("Equivalent"));
    }

    private void addListenerCoin(){
        coinMenuButton.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!coinMenuButton.getText().equals("Coin"))
                amountTextField.setDisable(false);
        });
    }
    private void updateLanguage() {
        ResourceBundle bundle = null;
        try {
            bundle = languageHandler.getBundle();
        } catch (Exception e) {
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if (bundle != null) {
            depositLabel.setText(bundle.getString("depositButton"));
            withdrawLabel.setText(bundle.getString("withdrawButton"));
        }
    }
    private boolean checkNegativity(double newAmount){
        if (newAmount<0)
            return false;
        return true;
    }
    }

