package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.controller.LoginController;
import it.unical.demacs.informatica.mvcwallet.model.SqlService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Classe che serve a gestire le impostazioni
public class SettingsHandler {
    public String [] themes = {"dark.css","light.css","mvc.css","blue.css","custom.css","hunter.css"};
    public String username;
    public String [] settings;
    public String format, page = "market", theme, language, currency="EUR", loginLanguage = "it", old;

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
        switch (settings[2]){
            case "0" -> logged = false;
            case "1" -> logged = true;
        }
        theme = settings[3];
        language = settings[4];
        currency = settings[5];
    }

    public void defaultSettings(){
        sqlService.serviceChangeSetting(
                LoginController.username,
                "HH:mm:ss",
                "spot",
                String.valueOf(0),
                "mvc.css",
                "en",
                "eur");
        updateSettings();
        SceneHandler.getInstance().createSideBar();
    }

    public String getCurrency() {
        return currency.toUpperCase();
    }

    public void uploadSettingOnDB(String time, String page, String logged, String theme, String language, String currency) {
        sqlService.serviceChangeSetting(LoginController.username, time, page, logged, theme, language, currency);
    }
}
