package it.unical.demacs.informatica.mvcwallet.handler;

import javafx.scene.paint.Color;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomThemeHandler {
   private CustomThemeHandler(){}
    private static final CustomThemeHandler instance = new CustomThemeHandler();
    public static CustomThemeHandler getInstance(){return instance;}
    private static final String pathOfCSS = PathHandler.getInstance().pathOfCSS;
    private final List<String > cssArray = new ArrayList<>();

    Color mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor ;

    public void setTheme(){
        changeColorsInFile();
        writeCssFile();
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
    }
    private void changeColorsInFile(){
        if(!cssArray.isEmpty()){
            cssArray.set(2, colorToHexString(mainBgc)); // Sfondo primario
            cssArray.set(6, colorToHexString(mainTxtColor)); // Testo primario
            cssArray.set(10, colorToHexString(buttonColor)); // Pulsante primario
            cssArray.set(12, colorToHexString(mainTxtColor)); // Testo Primario
            cssArray.set(16, colorToHexString(hoverColor)); // Sfondo di evidenziazione
            cssArray.set(18, colorToHexString(secondTxtColor)); // Testo secondario
            cssArray.set(22, colorToHexString(secondBgc)); // Sfondo secondario
            cssArray.set(26, colorToHexString(mainTxtColor)); // Testo primario
            cssArray.set(30, colorToHexString(mainTxtColor)); // Testo primario
            cssArray.set(34, colorToHexString(hoverColor)); // Sfondo di evidenziazione
            cssArray.set(39, colorToHexString(secondTxtColor)); // Testo secondario
            cssArray.set(43, colorToHexString(secondTxtColor)); // Testo secondario
            cssArray.set(47, colorToHexString(secondBgc)); // Sfondo secondario
            cssArray.set(49, colorToHexString(borderColor)); // Bordo
            cssArray.set(61, colorToHexString(hoverColor)); // Sfondo di evidenziazione
            cssArray.set(66, colorToHexString(secondTxtColor)); // Testo secondario
            cssArray.set(70, colorToHexString(mainTxtColor)); // Testo primario
            /*
            cssArray.set(72, primaryBG); // Sfondo del toggle spento
            cssArray.set(74, primaryBG); // Cursore del toggle spento
            cssArray.set(76, primaryBG); // Sfondo del toggle acceso
            cssArray.set(78, primaryBG); // Cursore del toggle spento
             */
            cssArray.set(85, colorToHexString(secondBgc)); // Sfondo secondario
            cssArray.set(90, colorToHexString(mainTxtColor)); // Testo primario
            cssArray.set(94, colorToHexString(mainTxtColor)); // Testo primario
            cssArray.set(98, colorToHexString(hoverColor)); // Sfondo di evidenziazione
            cssArray.set(103, colorToHexString(secondTxtColor)); // Testo secondario
            cssArray.set(107, colorToHexString(secondTxtColor)); // Testo secondario
        }
    }

    private void readCssFile(){
        try {
            File file = new File(Objects.requireNonNull(getClass().getResource(pathOfCSS + "custom.css")).toURI());
            FileReader stream = new FileReader(file);
            BufferedReader buff = new BufferedReader(stream);

            while(buff.ready()){
               cssArray.add(buff.readLine());
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assignColorsFromFile(cssArray.get(2),cssArray.get(22),cssArray.get(16),cssArray.get(10),cssArray.get(49),cssArray.get(6),cssArray.get(18));
    }
    public void writeCssFile(){
        try {
            File file = new File(Objects.requireNonNull(getClass().getResource(pathOfCSS + "custom.css")).toURI());
            FileWriter stream = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(stream);
            for(String cssLine : cssArray){
                bufferedWriter.write(cssLine);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static String colorToHexString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return String.format("#%02X%02X%02X;", r, g, b);
    }
}

