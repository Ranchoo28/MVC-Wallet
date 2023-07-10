package it.unical.demacs.informatica.mvcwallet.handler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomThemeHandler {
   // Sfondo secondario (più scuro)
// Sfondo primario
 // Sfondo di evidenziazione
 // Pulsante primario
// Bordo
 // Testo secondario
 // Testo primario
   private CustomThemeHandler(){}
    private static final CustomThemeHandler instance = new CustomThemeHandler();
    public static CustomThemeHandler getInstance(){return instance;}

    String primaryBG, secondaryBG, hoverBG, primaryBT, border, primaryTXT, secondaryTXT ;

    public void createTheme(){
        //for(String i: readCssFile())
          //  System.out.println(i);


    }

    public List<String> readCssFile(){

        try {
            List<String > cssArray = new ArrayList<>();
            FileReader stream = new FileReader((
                    "C:\\Users\\savcr\\OneDrive\\Desktop\\Università\\GitHub\\MVC-Wallet\\src\\main\\resources\\it\\unical\\demacs\\informatica\\mvcwallet\\css\\mvc.css"));
            BufferedReader buff = new BufferedReader(stream);

            while(buff.ready()){
               cssArray.add(buff.readLine());
            }
            return cssArray;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toHex(javafx.scene.paint.Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }


}

