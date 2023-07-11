package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.AlertHandler;
import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;
import it.unical.demacs.informatica.mvcwallet.model.Coin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ResourceBundle;

public class SpotController {
    private final AlertHandler alertHandler = AlertHandler.getInstance();

    @FXML
    private TableColumn<Coin, Double> allColumn;
    @FXML
    private TableView<Coin> tableCoins;
    @FXML
    private TableColumn<Coin, String> coinsColmun;

    @FXML
    private Button depositButton;

    @FXML
    private Label totalText;

    @FXML
    private Button withdrawButton;

    private final SqlHandler sqlHandler = SqlHandler.getInstance();

    private final LanguageHandler languageHandler = LanguageHandler.getInstance();
    double [] spots = sqlHandler.getSpots(LoginController.username);

    @FXML
    void initialize(){
        updateLanguage();
        ObservableList<Coin> A= FXCollections.observableArrayList();
        A.add(new Coin("BTC",spots[0]));
        A.add(new Coin("Ethereum",spots[1]));
        A.add(new Coin("Solano",spots[2]));
        A.add(new Coin("Binance coin",spots[3]));
        tableCoins.setItems(A);

        coinsColmun.setCellValueFactory(new PropertyValueFactory<>("Name"));
        allColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));


    }

    private void updateLanguage() {
        ResourceBundle bundle = null;
        try {
            bundle = languageHandler.getBundle();
        } catch (Exception e) {
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if (bundle != null) {
            depositButton.setText(bundle.getString("depositButton"));
            withdrawButton.setText(bundle.getString("withdrawButton"));
            totalText.setText(bundle.getString("totalLabel"));
        }
    }
}

