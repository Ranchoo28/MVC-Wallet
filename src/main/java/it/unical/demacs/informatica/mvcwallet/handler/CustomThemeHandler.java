package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;
import javafx.scene.paint.Color;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Classe per gestire il tema custom
public class CustomThemeHandler {
    private CustomThemeHandler(){}
    private static final CustomThemeHandler instance = new CustomThemeHandler();
    public static CustomThemeHandler getInstance(){return instance;}
    private final SqlService sqlService = SqlService.getInstance();
    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private static final String pathOfCSS = PathHandler.getInstance().pathOfCSS;
    private final List<String > cssArray = new ArrayList<>();

    String fontSize;
    Color mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor ;

    public void setTheme(){
        cssArray.clear();
        readCssFile();
        changeColorsInFile();
        writeCssFile();
    }

    /* From Database */

    public String getFontSizeFromDB(){
        assignFontSizeFromBD();
        return this.fontSize;
    }

    protected void assignFontSizeFromBD(){
        this.fontSize = sqlService.serviceGetCustomThemeFontSize();
    }

    public Color[] getColorFromDB(){
        assignColorsFromDB();
        return new Color[]{mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor};
    }

    protected void assignColorsFromDB(){
        String [] colors = sqlService.serviceGetCustomTheme(LoginController.username);
        this.mainBgc = Color.valueOf(colors[0].replace(";",""));
        this.secondBgc = Color.valueOf(colors[1].replace(";",""));
        this.hoverColor = Color.valueOf(colors[2].replace(";",""));
        this.buttonColor = Color.valueOf(colors[3].replace(";",""));
        this.borderColor = Color.valueOf(colors[4].replace(";",""));
        this.mainTxtColor = Color.valueOf(colors[5].replace(";",""));
        this.secondTxtColor = Color.valueOf(colors[6].replace(";",""));
    }

    /* From file */
    public void assignFontSizeInColorPickerFromFile(){

    }

    public Color[] getColorsFromFile(){
        readCssFile();
        return new Color[]{mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor};
    }

    public void assignColorsFromFile(String mainBgc, String secondBgc, String hoverColor, String buttonColor, String borderColor, String mainTxtColor, String secondTxtColor) {
        this.mainBgc = Color.valueOf(mainBgc.replace(" ", "").replace(";", ""));
        this.secondBgc = Color.valueOf(secondBgc.replace(" ", "").replace(";", ""));
        this.hoverColor = Color.valueOf(hoverColor.replace(" ", "").replace(";", ""));
        this.buttonColor = Color.valueOf(buttonColor.replace(" ", "").replace(";", ""));
        this.borderColor = Color.valueOf(borderColor.replace(" ", "").replace(";", ""));
        this.mainTxtColor = Color.valueOf(mainTxtColor.replace(" ", "").replace(";", ""));
        this.secondTxtColor = Color.valueOf(secondTxtColor.replace(" ", "").replace(";", ""));
    }

    public void assignColorsFromColorPicker(Color mainBgc, Color secondBgc, Color hoverColor, Color buttonColor, Color borderColor, Color mainTxtColor, Color secondTxtColor){
        this.mainBgc = mainBgc;
        this.secondBgc = secondBgc;
        this.hoverColor = hoverColor;
        this.buttonColor = buttonColor;
        this.borderColor = borderColor;
        this.mainTxtColor = mainTxtColor;
        this.secondTxtColor = secondTxtColor;
        sqlService.serviceCustomThemeOnDB(colorToHexString(mainTxtColor),colorToHexString(secondBgc),colorToHexString(hoverColor), colorToHexString(buttonColor),colorToHexString(borderColor), colorToHexString(mainTxtColor),colorToHexString(secondTxtColor));
    }

    public void assignColorsFromColorPicker(String fontSize){
        this.fontSize = fontSize;
        sqlService.serviceSetCustomThemeFontSize(this.fontSize);
    }

    private void changeColorsInFile(){
        if(!cssArray.isEmpty()){
            cssArray.set(1, this.fontSize+"px;");
            cssArray.set(3, colorToHexString(mainTxtColor));    /* Testo primario */
            cssArray.set(5, colorToHexString(mainBgc));         /* Sfondo primario */
            cssArray.set(7, colorToHexString(buttonColor));     /* Pulsante */
            cssArray.set(9, colorToHexString(mainTxtColor));    /* Testo Primario */

            cssArray.set(11, colorToHexString(hoverColor));     /* Sfondo di evidenziazione */
            cssArray.set(13, colorToHexString(secondTxtColor)); /* Testo secondario */
            cssArray.set(15, colorToHexString(buttonColor));    /* Pulsante */
            cssArray.set(17, colorToHexString(hoverColor));     /* Sfondo di evidenziazione */
            cssArray.set(19, colorToHexString(mainTxtColor));   /* Testo primario */

            cssArray.set(21, colorToHexString(secondTxtColor)); /* Testo secondario */
            cssArray.set(23, colorToHexString(secondTxtColor)); /* Sfondo secondario */
            cssArray.set(25, colorToHexString(mainTxtColor));   /* Testo primario */
            cssArray.set(27, colorToHexString(mainTxtColor));   /* Testo primario */
            cssArray.set(29, colorToHexString(hoverColor));     /* Sfondo di evidenziazione */

            cssArray.set(31, colorToHexString(secondTxtColor)); /* Testo secondario */
            cssArray.set(33, colorToHexString(secondTxtColor)); /* Testo secondario */
            cssArray.set(35, colorToHexString(mainBgc));        /* Sfondo primario */
            cssArray.set(37, colorToHexString(mainBgc));        /* Bordo */
            cssArray.set(39, colorToHexString(hoverColor));     /* Sfondo di evidenziazione */

            cssArray.set(41, colorToHexString(mainTxtColor));   /* Testo primario */
            cssArray.set(43, colorToHexString(secondTxtColor)); /* Testo secondario */
            cssArray.set(45, colorToHexString(hoverColor));     /* Sfondo di evidenziazione */
            cssArray.set(47, colorToHexString(secondTxtColor)); /* Testo secondario */
            cssArray.set(49, colorToHexString(mainTxtColor));   /* Testo primario */

            cssArray.set(51, colorToHexString(secondTxtColor)); /* Sfondo del toggle spento */
            cssArray.set(53, colorToHexString(secondTxtColor)); /* Colore del cursore del toggle spento */
            cssArray.set(55, colorToHexString(secondTxtColor)); /* Sfondo del toggle acceso */
            cssArray.set(57, colorToHexString(secondTxtColor)); /* Colore del cursore del toggle acceso */
            cssArray.set(59, colorToHexString(secondBgc));      /* Sfondo secondario */

            cssArray.set(61, colorToHexString(mainTxtColor));   /* Testo primario */
            cssArray.set(63, colorToHexString(mainTxtColor));   /* Testo primario */
            cssArray.set(65, colorToHexString(hoverColor));     /* Sfondo di evidenziazione */
            cssArray.set(67, colorToHexString(secondTxtColor)); /* Testo secondario */
            cssArray.set(69, colorToHexString(secondTxtColor)); /* Testo secondario */
        }
        writeCssFile();
    }

    void readCssFile(){
        try {
            File file = new File(Objects.requireNonNull(getClass().getResource(pathOfCSS + "custom.css")).toURI());
            FileReader stream = new FileReader(file);
            BufferedReader buff = new BufferedReader(stream);

            while(buff.ready()){
                cssArray.add(buff.readLine());
            }
            buff.close();
            stream.close();

        } catch (Exception e) {
            System.out.println("Error in CustomThemeHandler.java (rows 141-157) ");
            e.printStackTrace();
        }
    }

    public void writeCssFile(){
        try {
            File file = new File(Objects.requireNonNull(getClass().getResource(pathOfCSS + "custom.css")).toURI());
            FileWriter stream = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(stream);
            for(String cssLine : cssArray){
                bufferedWriter.write(cssLine);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            stream.close();
        } catch (IOException | URISyntaxException e) {
            alertHandler.createErrorAlert("customCssErrorAlert");
        }
    }
    public static String colorToHexString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return String.format("#%02X%02X%02X;", r, g, b);
    }
}
