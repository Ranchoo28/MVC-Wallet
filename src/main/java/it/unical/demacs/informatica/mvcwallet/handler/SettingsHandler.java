package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Classe che serve a gestire le impostazioni
public class SettingsHandler {
    public String username;
    public String [] settings;
    public String format, page, theme, language, currency;
    public boolean logged;

    SqlService sqlService = SqlService.getInstance();

    private SettingsHandler(){}
    private static final SettingsHandler instance = new SettingsHandler();
    public static SettingsHandler getInstance(){ return instance; }
    public String getDay(){
        return LocalDate.now().toString();
    }

    public String timeFormatter(){ return LocalTime.now().format(DateTimeFormatter.ofPattern(format)); }

    public void updateSettings(){
        settings = sqlService.serviceSettings(LoginController.username);
        format = settings[0];
        page = settings[1];
        if(settings[2].equals("0")) logged = false;
        if(settings[2].equals("1")) logged = true;
        theme = settings[3];
        language = settings[4];
        currency = settings[5];
    }
}
