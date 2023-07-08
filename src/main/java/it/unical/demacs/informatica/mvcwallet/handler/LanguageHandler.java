package it.unical.demacs.informatica.mvcwallet.handler;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageHandler {

    private ResourceBundle bundle;
    private LanguageHandler(){}
    private static final LanguageHandler instance = new LanguageHandler();
    public static LanguageHandler getInstance(){return instance;}
    private Locale locale;

    public void updateLanguage(String language){
        Locale locale = new Locale(language);
        bundle = ResourceBundle.getBundle(PathHandler.getInstance().pathOfLanguage + locale);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }


}
