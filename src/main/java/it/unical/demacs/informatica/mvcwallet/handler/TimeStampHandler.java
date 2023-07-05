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

    public String convertTimeStamp(long timeStamp, String timeframe){
        Instant specificTimeStamp;
        LocalDate specificDate;
        DateTimeFormatter formatter;
        switch (timeframe){
            case "1W":
                specificTimeStamp = Instant.ofEpochMilli(timeStamp);
                specificDate = specificTimeStamp.atZone(ZoneId.systemDefault()).toLocalDate();
                formatter = DateTimeFormatter.ofPattern("yyyy-ww");
                return specificDate.format(formatter);
            case "1M":
                specificTimeStamp = Instant.ofEpochMilli(timeStamp);
                specificDate = specificTimeStamp.atZone(ZoneId.systemDefault()).toLocalDate();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                return specificDate.format(formatter);
            case "1Y":
                specificTimeStamp = Instant.ofEpochMilli(timeStamp);
                specificDate = specificTimeStamp.atZone(ZoneId.systemDefault()).toLocalDate();
                formatter = DateTimeFormatter.ofPattern("yyyy");
                return specificDate.format(formatter);
            }
        return null;
    }

    public GregorianCalendar convertToGregorianCalendar(String date, String timeframe){
        SimpleDateFormat dateFormat;
        GregorianCalendar calendar = new GregorianCalendar();

        switch (timeframe){
            case "1W":
                dateFormat = new SimpleDateFormat("yyyy-ww");
                try{
                    calendar.setTime(dateFormat.parse(date));
                } catch (ParseException e) {
                    System.out.println("Errore durante l'analisi della data.");
                }
                return calendar;
            case "1M":
                dateFormat = new SimpleDateFormat("yyyy-MM");
                try{
                    calendar.setTime(dateFormat.parse(date));
                } catch (ParseException e) {
                    System.out.println("Errore durante l'analisi della data.");
                }
                return calendar;
            case "1Y":
                dateFormat = new SimpleDateFormat("yyyy");
                try{
                    calendar.setTime(dateFormat.parse(date));
                } catch (ParseException e) {
                    System.out.println("Errore durante l'analisi della data.");
                }
                return calendar;
        }
        return null;
    }
}
