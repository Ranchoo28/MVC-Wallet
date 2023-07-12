package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ResourceBundle;

public class ColorPickerController{

    @FXML
    private Label primaryBgLabel, secondaryBgLabel,
            hoverLabel, buttonLabel,
            borderLabel, primaryFontLabel, secondaryFontLabel, applyLabel, cancLabel;

    @FXML
    private ColorPicker bdrColorPicker, btnColorPicker,
            hovColorPicker, mbgColorPicker,
            mntColorPicker, sbgColorPicker, sntColorPicker;

    @FXML
    private Button applyButton, cancelButton;


    private final CustomThemeHandler customThemeHandler = CustomThemeHandler.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final SettingsHandler settingsHandler = SettingsHandler.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();

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
            alertHandler.createErrorAlert(bundle.getString("colorPickerErrorAlert"));
        }
    }
    @FXML
    void onApplyButton(){
        getColorsFromColorPicker();
        customThemeHandler.setTheme();
        settingsHandler.theme = "custom.css";
        if(!settingsHandler.page.equals("settings"))
            settingsHandler.old = settingsHandler.page;
        settingsHandler.page = "settings";
        sceneHandler.closeCAT();
    }

    @FXML
    void onCancButton(){
        sceneHandler.closeCAT();
    }
    @FXML
    void initialize(){
        updateLanguage();
        setColorsInColorPicker();
    }

    private void updateLanguage(){
        ResourceBundle bundle = null;
        try {
            bundle = LanguageHandler.getInstance().getBundle();
        } catch (Exception e){
            alertHandler.createErrorAlert("Error in loading the language");
        }
        if(bundle!=null){
           applyLabel.setText(bundle.getString("applyButton"));
           cancLabel.setText(bundle.getString("backButton"));
           primaryBgLabel.setText(bundle.getString("primaryBgLabel"));
           secondaryBgLabel.setText(bundle.getString("secondaryBgLabel"));
           hoverLabel.setText(bundle.getString("hoverLabel"));
           buttonLabel.setText(bundle.getString("buttonLabel"));
           borderLabel.setText(bundle.getString("borderLabel"));
           primaryFontLabel.setText(bundle.getString("primaryFontLabel"));
           secondaryFontLabel.setText(bundle.getString("secondaryFontLabel"));
        }
    }
}