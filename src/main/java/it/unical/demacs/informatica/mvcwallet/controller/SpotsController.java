package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.LanguageHandler;
import it.unical.demacs.informatica.mvcwallet.model.Coin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.codec.language.bm.Lang;

public class SpotsController {


    @FXML
    private TableColumn<Coin, Double> allColumn;

    @FXML
    private TableColumn<Coin, String> coinsColmun;

    @FXML
    private Button depositButton;

    @FXML
    private TableColumn<Coin,Double > equivalentColumn;

    @FXML
    private AnchorPane spots;

    @FXML
    private TableView<Coin> tableCoins;

    @FXML
    private Label totalText;

    @FXML
    private Button withdrawButton;

    @FXML
    void initialize(){
        //Platform.runLater(() -> updateLanguage());
        updateLanguage();

        ObservableList<Coin> A= FXCollections.observableArrayList();
        A.add(new Coin("BTC",0.102));
        coinsColmun.setCellValueFactory(new PropertyValueFactory<>("Name"));
        allColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        //equivalentColumn.setCellValueFactory(new PropertyValueFactory<>("Equivalent"));
        tableCoins.setItems(A);
    }

    private void updateLanguage(){
        depositButton.setText(LanguageHandler.getInstance().getBundle().getString("depositButton"));
        withdrawButton.setText(LanguageHandler.getInstance().getBundle().getString("withdrawButton"));
        totalText.setText(LanguageHandler.getInstance().getBundle().getString("totalLabel"));
    }

}

