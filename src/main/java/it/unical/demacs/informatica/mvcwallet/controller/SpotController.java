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
    private final SettingsHandler settingsHandler= SettingsHandler.getInstance();
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
    private Label balanceLabel, operationLabel, totalLabel, currencyLabel, depositLabel, withdrawLabel, confirmLabel, cancelLabel;
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
        ResourceBundle bundle = languageHandler.getBundle();
        makeTransfer.setVisible(true);
        amountTextField.clear();
        operationLabel.setText(bundle.getString("depositButton"));
        x=1;
    }

    @FXML
    void onWithdrawClick() {
        ResourceBundle bundle = languageHandler.getBundle();
        makeTransfer.setVisible(true);
        amountTextField.clear();
        operationLabel.setText(bundle.getString("withdrawButton"));
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
                    alertHandler.createErrorAlert("ERROR: The amount of coins that you are trying to withdraw are higher than your current amount");
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
        currencyLabel.setText(settingsHandler.getCurrency());
        BTC = new Coin("Bitcoin",spots[0], "BTC",0);
        ETH = new Coin("Ethereum",spots[1], "ETH",0);
        SOL = new Coin("Solana",spots[2], "SOL",0);
        BNB = new Coin("Binance Coin",spots[3], "BNB",0);
        BTC.setEquivalent(BTC.getAmount()*getCurrentPriceSpot(BTC.getCode(),settingsHandler.getCurrency()));
        ETH.setEquivalent(ETH.getAmount()*getCurrentPriceSpot(ETH.getCode(),settingsHandler.getCurrency()));
        SOL.setEquivalent(SOL.getAmount()*getCurrentPriceSpot(SOL.getCode(),settingsHandler.getCurrency()));
        BNB.setEquivalent(BNB.getAmount()*getCurrentPriceSpot(BNB.getCode(),settingsHandler.getCurrency()));
        double total=BTC.getEquivalent()+ETH.getEquivalent()+SOL.getEquivalent()+BNB.getEquivalent();
        totalLabel.setText(String.valueOf((float) total));

        A = FXCollections.observableArrayList();
        A.addAll(BTC,ETH,SOL,BNB);
        coinTable.setItems(A);

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
            balanceLabel.setText(bundle.getString("balanceLabel"));
            confirmLabel.setText(bundle.getString("confirmButton"));
            cancelLabel.setText(bundle.getString("backButton"));
        }
    }
    private boolean checkNegativity(double newAmount){
        if (newAmount<0)
            return false;
        return true;
    }
}

