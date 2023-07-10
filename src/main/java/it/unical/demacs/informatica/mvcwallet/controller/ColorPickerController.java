package it.unical.demacs.informatica.mvcwallet.controller;

import com.jfoenix.controls.JFXColorPicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorPickerController extends SettingsController{

    @FXML
    private ColorPicker bdrColorPicker;

    @FXML
    private ColorPicker btnColorPicker;

    @FXML
    private ColorPicker hovColorPicker;

    @FXML
    private ColorPicker mbgColorPicker;

    @FXML
    private ColorPicker mntColorPicker;

    @FXML
    private ColorPicker sbgColorPicker;

    @FXML
    private ColorPicker sntColorPicker;

    @FXML
    private Button backButton;

    @FXML
    private Button applyButton;

    @FXML
    private Button removeButton;

    @FXML
    void onRemoveButton(){
        themeMenuButton.setText("Dark");
    }
    @FXML
    void onApplyButton(){
        themeMenuButton.setText("Dark");
    }
    @FXML
    void onBackButton(){
        themeMenuButton.setText("Dark");
    }
}
