package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.LoggedHandler;
import it.unical.demacs.informatica.mvcwallet.handler.PathHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainViewController {
    String pathOfView = PathHandler.getInstance().getPathOfView();
    LoggedHandler loggedHandler = LoggedHandler.getInstance();
    @FXML
    private AnchorPane main;

    @FXML
    void initialize() throws IOException{
        if(loggedHandler.stayLoggedReading().equals("null")){
            loadFXML("login-view.fxml");
        } else {
            try{
                loadFXML("sidebar-view.fxml");
            } catch (Exception e){
                System.out.println("Error in MainController.java (rows: 24-28) " + e);
            }
        }
    }

    public void loadFXML(String nomeFXML){
        main.getChildren().clear();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathOfView + nomeFXML));
            AnchorPane pane = fxmlLoader.load();

            main.getChildren().add(pane);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);

        } catch (IOException e) {
            System.out.println("Error in MainViewController.java (rows: ...-...) " + e);
        }
    }
}

