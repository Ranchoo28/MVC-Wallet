package it.unical.demacs.informatica.mvcwallet.handler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SettingsHandler {
    // Classe che serve a calcolare ora e data in tempo reale

    private SettingsHandler(){}
    private static SettingsHandler istance = new SettingsHandler();
    public static SettingsHandler getInstance(){return istance;}

    // Questi dovranno essere presi da database
    public String format = "HH:mm:ss";
    public String page = "market";

    public String getDay(){
        return LocalDate.now().toString();
    }

    public String timeFormatter(){ return LocalTime.now().format(DateTimeFormatter.ofPattern(format)); }
}
