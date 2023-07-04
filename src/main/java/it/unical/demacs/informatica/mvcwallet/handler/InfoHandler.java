package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InfoHandler {
    private InfoHandler(){}
    private static InfoHandler istance = new InfoHandler();
    public static InfoHandler getInstance(){return istance;}

    // Classe che serve a calcolare ora e data in tempo reale
    public String getTime(){
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    public String getDay(){
        return LocalDate.now().toString();
    }

}
