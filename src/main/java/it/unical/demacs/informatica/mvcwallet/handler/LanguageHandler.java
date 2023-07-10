package it.unical.demacs.informatica.mvcwallet.handler;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageHandler {

    private ResourceBundle bundle;
    private LanguageHandler(){}
    private static final LanguageHandler instance = new LanguageHandler();
    public static LanguageHandler getInstance(){return instance;}

    public void updateLanguage(String language){
        Locale locale = new Locale(language);
        String path = PathHandler.getInstance().pathOfLanguage;
        try {
            bundle = ResourceBundle.getBundle(path + locale);
        } catch (Exception e){
            System.out.println("Error in LanguageHandler.java (rows: 21-25) " + e);
        }
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
