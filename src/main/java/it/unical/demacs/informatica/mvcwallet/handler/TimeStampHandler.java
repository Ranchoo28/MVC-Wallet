package it.unical.demacs.informatica.mvcwallet.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class TimeStampHandler {
    private static final TimeStampHandler instance = new TimeStampHandler();

    public static TimeStampHandler getInstance() {
        return instance;
    }

    public String convertTimeStamp(long timeStamp){
        Instant specificTimeStamp = Instant.ofEpochMilli(timeStamp);
        LocalDate specificDate = specificTimeStamp.atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return specificDate.format(formatter);
    }

    public GregorianCalendar convertToGregorianCalendar(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        GregorianCalendar calendar = new GregorianCalendar();

        try{
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            System.out.println("Errore durante l'analisi della data.");
        }
        return calendar;
    }
}
