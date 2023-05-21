package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InfoHandler {
    private InfoHandler(){}
    private static InfoHandler istance = new InfoHandler();
    public static InfoHandler getInstance(){return istance;}

    public String getTime(){
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getDay(){
        return LocalDate.now().toString();
    }

    public String getInfoAccount(){
        if(LocalTime.now().getHour() >= 5 && LocalTime.now().getHour() < 14)
            return "Buongiorno " + LoginController.username + "!";
        if(LocalTime.now().getHour() >= 14 && LocalTime.now().getHour() < 19)
            return "Buon pomeriggio " + LoginController.username + "!";
        if(LocalTime.now().getHour() >= 19)
            return "Buonasera " + LoginController.username + "!";
        return "Benvenuto " + LoginController.username + "!";
    }
}
