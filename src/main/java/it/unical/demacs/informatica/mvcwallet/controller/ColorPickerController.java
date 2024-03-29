package it.unical.demacs.informatica.mvcwallet.controller;

import it.unical.demacs.informatica.mvcwallet.handler.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    MenuButton fontMenuButton;

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

    void getFontSizeFromColorPicker(){
        customThemeHandler.assignColorsFromColorPicker(fontMenuButton.getText());
    }

    boolean clicked = false;

    void addItemsFontSizeMenuButton(){
        for(int i = 10; i <= 16; i++){
            MenuItem item = new MenuItem(String.valueOf(i));
            item.setOnAction(event -> {
                fontMenuButton.setText(item.getText());
            });
            fontMenuButton.getItems().add(item);
        }
        for(int i = 18; i <= 26; i+=2){
            MenuItem item = new MenuItem(String.valueOf(i));
            item.setOnAction(event -> {
                fontMenuButton.setText(item.getText());
            });
            fontMenuButton.getItems().add(item);
        }
        for(int i = 28; i <= 44; i+=4){
            MenuItem item = new MenuItem(String.valueOf(i));
            item.setOnAction(event -> {
                fontMenuButton.setText(item.getText());
            });
            fontMenuButton.getItems().add(item);
        }
        for(int i = 48; i <= 72; i+=6){
            MenuItem item = new MenuItem(String.valueOf(i));
            item.setOnAction(event -> {
                fontMenuButton.setText(item.getText());
            });
            fontMenuButton.getItems().add(item);
        }
    }

    void setColorsInColorPickerFromFile(){
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

    void setColorsFromBD(){
        try {
            Color [] colors = customThemeHandler.getColorFromDB();
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

    void setFontSizeFromDB(){
        fontMenuButton.setText(customThemeHandler.getFontSizeFromDB());
    }
    @FXML
    void onApplyButton(){
        getColorsFromColorPicker();
        getFontSizeFromColorPicker();
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
        setColorsFromBD();
        setFontSizeFromDB();
        addItemsFontSizeMenuButton();
        addListener();
    }

    private void addListener(){
        fontMenuButton.setOnMouseClicked(mouseEvent -> {
            if(!clicked) {
                fontMenuButton.hide();
                clicked = true;
                fontMenuButton.show();
            } else {
                fontMenuButton.setOnMouseClicked(null);
            }
        });
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