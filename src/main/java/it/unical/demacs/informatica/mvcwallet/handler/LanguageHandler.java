package it.unical.demacs.informatica.mvcwallet.handler;

import java.util.Locale;
import java.util.ResourceBundle;

// Classe per la gestione della lingua
public class LanguageHandler {

    private ResourceBundle bundle;
    private LanguageHandler(){}
    private static final LanguageHandler instance = new LanguageHandler();
    public static LanguageHandler getInstance(){return instance;}
    private final AlertHandler alertHandler = AlertHandler.getInstance();

    public void updateLanguage(String language){
        Locale locale = new Locale(language);
        String path = PathHandler.getInstance().pathOfLanguage;
        try {
            bundle = ResourceBundle.getBundle(path + locale);
        } catch (Exception e){
            alertHandler.createErrorAlert("Error in loading the language");
        }
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
