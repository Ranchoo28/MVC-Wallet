package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.CustomThemeHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SceneHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SettingsHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ColorPickerController{

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
    private Button applyButton;
    @FXML
    private Button cancelButton;

    final static CustomThemeHandler customThemeHandler = CustomThemeHandler.getInstance();
    final static SceneHandler sceneHandler = SceneHandler.getInstance();
    final static SettingsHandler settingsHandler = SettingsHandler.getInstance();

    void getColorsFromColorPicker(){
        Color mainBgc = mbgColorPicker.getValue();
        Color secondBgc = sbgColorPicker.getValue();
        Color hoverColor = hovColorPicker.getValue();
        Color buttonColor = btnColorPicker.getValue();
        Color borderColor = bdrColorPicker.getValue();
        Color mainTxtColor = mntColorPicker.getValue();
        Color secondTxtColor = sntColorPicker.getValue();
        customThemeHandler.assignColorsFromColorPicker(mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor);
    }
    void setColorsInColorPicker(){
        try {
            Color[] colors = customThemeHandler.getColorsFromFile();
            mbgColorPicker.setValue(colors[0]);
            sbgColorPicker.setValue(colors[1]);
            hovColorPicker.setValue(colors[2]);
            btnColorPicker.setValue(colors[3]);
            bdrColorPicker.setValue(colors[4]);
            mntColorPicker.setValue(colors[5]);
            sntColorPicker.setValue(colors[6]);
        } catch (Exception e){
            System.out.println("Error in ColorPickerController.java (rows: 47-58) " + e);
        }
    }
    @FXML
    void onApplyButton(){
        getColorsFromColorPicker();
        customThemeHandler.setTheme();
        settingsHandler.theme = "custom";
        sceneHandler.closeCAT();
    }
    @FXML
    void onCancButton(){
        sceneHandler.closeCAT();
    }
    @FXML
    void initialize(){
        setColorsInColorPicker();
    }
}