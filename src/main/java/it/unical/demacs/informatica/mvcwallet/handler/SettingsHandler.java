package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SettingsHandler {
    // Classe che serve a gestire le impostazioni

    private SettingsHandler(){}
    private static SettingsHandler istance = new SettingsHandler();
    public static SettingsHandler getInstance(){ return istance; }

    public String username;
    public String [] settings;
    public String format; //"HH:mm:ss";
    public String page; //"market";
    public boolean logged;

    //public String format = "HH:mm:ss";
    //public String page = "market";

    //public String language = SqlService.getIstance().getLanguage();
    //public String logged = SqlService.getIstance().getLogged();
    //public String currency = SqlService.getIstance().getCurrency();

    public String getDay(){
        return LocalDate.now().toString();
    }

    public String timeFormatter(){ return LocalTime.now().format(DateTimeFormatter.ofPattern(format)); }

    public void updateSettings(){
        settings = SqlService.getIstance().serviceSettings(LoginController.username);
        format = settings[0];
        page = settings[1];
        //for(String i: settings) System.out.println(i);
        if(settings[2].equals("0")) logged = false;
        if(settings[2].equals("1")) logged = true;
    }
}
