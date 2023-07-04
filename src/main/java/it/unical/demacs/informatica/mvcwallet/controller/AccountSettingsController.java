package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class AccountSettingsController {

    String path = "/it/unical/demacs/informatica/mvcwallet/view/";
    @FXML
    private Label profileLabel, settingsLabel, backLabel;
    @FXML
    private AnchorPane centerPage;

    @FXML
    void onProfileClick() throws IOException {
        loadFXML("profile-view.fxml");
    }

    @FXML
    void onBackClick() {
        SceneHandler.getInstance().createSideBar();
    }

    @FXML
    void initialize(){

        // Togliere il colore delle icon
        FontIcon profileIcon = new FontIcon("mdi2a-account-cog");
        profileIcon.setIconColor(Paint.valueOf("#fff"));
        profileIcon.setIconSize(25);
        profileLabel.setContentDisplay(ContentDisplay.LEFT);
        profileLabel.setGraphic(profileIcon);

            /*
            FontIcon passwordIcon = new FontIcon("mdi2f-form-textbox-password");
            passwordIcon.setIconSize(25);
            passwordIcon.setIconColor(Paint.valueOf("fff"));
            passwordLabel.setContentDisplay(ContentDisplay.LEFT);
            passwordLabel.setGraphic(passwordIcon);

             */

        FontIcon settingsIcon = new FontIcon("mdi2c-cog");
        settingsIcon.setIconSize(25);
        settingsIcon.setIconColor(Paint.valueOf("#fff"));
        settingsLabel.setContentDisplay(ContentDisplay.LEFT);
        settingsLabel.setGraphic(settingsIcon);

        FontIcon backIcon = new FontIcon("mdi2k-keyboard-backspace");
        backIcon.setIconSize(25);
        backIcon.setIconColor(Paint.valueOf("#fff"));
        settingsLabel.setContentDisplay(ContentDisplay.LEFT);
        backLabel.setGraphic(backIcon);
    }

    public void loadFXML(String nomeFXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path + nomeFXML));
        AnchorPane pane = fxmlLoader.load();

        centerPage.getChildren().add(pane);

        centerPage.setTopAnchor(pane, 0.0);
        centerPage.setRightAnchor(pane, 0.0);
        centerPage.setBottomAnchor(pane, 0.0);
        centerPage.setLeftAnchor(pane, 0.0);
    }

}
