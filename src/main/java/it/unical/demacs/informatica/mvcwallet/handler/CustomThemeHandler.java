package it.unical.demacs.informatica.mvcwallet.handler;

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

    String mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor ;

    public void setTheme(String mainBgc, String secondBgc, String hoverColor, String buttonColor, String borderColor, String mainTxtColor, String secondTxtColor){
        readCssFile();
        assignColors(mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor);
        changeColors();
        writeCssFile();
        for(String i: cssArray)
            System.out.println(i);
    }
    public String[] getColorsFromFile(){
        readCssFile();
        return new String[]{mainBgc, secondBgc, hoverColor, buttonColor, borderColor, mainTxtColor, secondTxtColor};
    }

    public void assignColors(String mainBgc, String secondBgc, String hoverColor, String buttonColor, String borderColor, String mainTxtColor, String secondTxtColor){
        this.mainBgc = mainBgc.replace(" ", "").replace(";","");
        this.secondBgc = secondBgc.replace(" ", "").replace(";","");
        this.hoverColor = hoverColor.replace(" ", "").replace(";","");
        this.buttonColor = buttonColor.replace(" ", "").replace(";","");
        this.borderColor = borderColor.replace(" ", "").replace(";","");
        this.mainTxtColor = mainTxtColor.replace(" ", "").replace(";","");
        this.secondTxtColor = secondTxtColor.replace(" ", "").replace(";","");
        changeColors();

    }
    private void changeColors(){
        if(!cssArray.isEmpty()){
            cssArray.add(2, mainBgc); // Sfondo primario
            cssArray.add(6, mainTxtColor); // Testo primario
            cssArray.add(10, buttonColor); // Pulsante primario
            cssArray.add(12, mainTxtColor); // Testo Primario
            cssArray.add(16, hoverColor); // Sfondo di evidenziazione
            cssArray.add(18, secondTxtColor); // Testo secondario
            cssArray.add(22, secondBgc); // Sfondo secondario
            cssArray.add(26, mainTxtColor); // Testo primario
            cssArray.add(30, mainTxtColor); // Testo primario
            cssArray.add(34, hoverColor); // Sfondo di evidenziazione
            cssArray.add(39, secondTxtColor); // Testo secondario
            cssArray.add(43, secondTxtColor); // Testo secondario
            cssArray.add(47, secondBgc); // Sfondo secondario
            cssArray.add(49, borderColor); // Bordo
            cssArray.add(61, hoverColor); // Sfondo di evidenziazione
            cssArray.add(66, secondTxtColor); // Testo secondario
            cssArray.add(70, mainTxtColor); // Testo primario
            /*
            cssArray.add(72, primaryBG); // Sfondo del toggle spento
            cssArray.add(74, primaryBG); // Cursore del toggle spento
            cssArray.add(76, primaryBG); // Sfondo del toggle acceso
            cssArray.add(78, primaryBG); // Cursore del toggle spento
            */
            cssArray.add(85, secondBgc); // Sfondo secondario
            cssArray.add(90, mainTxtColor); // Testo primario
            cssArray.add(94, mainTxtColor); // Testo primario
            cssArray.add(98, hoverColor); // Sfondo di evidenziazione
            cssArray.add(103, secondTxtColor); // Testo secondario
            cssArray.add(107, secondTxtColor); // Testo secondario
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
        assignColors(cssArray.get(2),cssArray.get(22),cssArray.get(16),cssArray.get(10),cssArray.get(49),cssArray.get(6),cssArray.get(18));
    }
    public void writeCssFile(){
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
    }
}

