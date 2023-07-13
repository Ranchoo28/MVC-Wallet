package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import it.unical.demacs.informatica.mvcwallet.model.Coin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class SpotController {
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final SqlHandler sqlHandler = SqlHandler.getInstance();
    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    double [] spots = sqlHandler.getSpots(LoginController.username);

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

    @FXML
    void onDepositClick() {
        makeTransfer.setVisible(true);
        operationLabel.setText("Deposit");
    }

    @FXML
    void onWithdrawClick() {
        makeTransfer.setVisible(true);
        operationLabel.setText("Withdraw");
    }

    @FXML
    void initialize(){
        updateLanguage();
        ObservableList<Coin> A = FXCollections.observableArrayList();
        A.add(new Coin("Bitcoin",spots[0], "BTC"));
        A.add(new Coin("Ethereum",spots[1], "ETH"));
        A.add(new Coin("Solana",spots[2], "SOL"));
        A.add(new Coin("Binance Coin",spots[3], "BNB"));
        coinTable.setItems(A);

        for(Coin i: A){
            MenuItem item = new MenuItem(i.getCode());
            item.setOnAction(event -> {
                coinMenuButton.setText(item.getText());
            });
            coinMenuButton.getItems().add(item);
        }

        coinColmun.setCellValueFactory(new PropertyValueFactory<>("Name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
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
}

