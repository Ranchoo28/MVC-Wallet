package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.CustomThemeHandler;
import javafx.event.Event;
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

    final static CustomThemeHandler customThemeHandler = CustomThemeHandler.getInstance();

    void getColorsFromColorPicker(){
        String mainBgc = mbgColorPicker.getCustomColors().toString();
        System.out.println(mainBgc);
        String secondBgc = sbgColorPicker.getCustomColors().toString();
        String hoverColor = hovColorPicker.getCustomColors().toString();
        String buttonColor = btnColorPicker.getCustomColors().toString();
        String borderColor = bdrColorPicker.getCustomColors().toString();
        String mainTxtColor = mntColorPicker.getCustomColors().toString();
        String secondTxtColor = sntColorPicker.getCustomColors().toString();
        customThemeHandler.assignColors(mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor);
    }
    void setColorsInColorPicker(){
        try {
            String[] colors = customThemeHandler.getColorsFromFile();
            mbgColorPicker.setValue(Color.valueOf(colors[0]));
            sbgColorPicker.setValue(Color.valueOf(colors[1]));
            hovColorPicker.setValue(Color.valueOf(colors[2]));
            btnColorPicker.setValue(Color.valueOf(colors[3]));
            bdrColorPicker.setValue(Color.valueOf(colors[4]));
            mntColorPicker.setValue(Color.valueOf(colors[5]));
            sntColorPicker.setValue(Color.valueOf(colors[6]));
        } catch (Exception e){
            System.out.println("Error in ColorPickerController.java (rows: 47-58) " + e);
        }
    }
    @FXML
    void onApplyButton(){
        getColorsFromColorPicker();
        customThemeHandler.writeCssFile();
    }
    @FXML
    void initialize(){
        // Annulla l'evento di chiusura
        mbgColorPicker.setOnHiding(Event::consume);
        setColorsInColorPicker();
    }
}
