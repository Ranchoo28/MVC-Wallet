package it.unical.demacs.informatica.mvcwallet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class BarData implements Serializable {

    protected double open;
    protected BigDecimal formattedOpen;
    protected double high;
    protected BigDecimal formattedHigh;
    protected double low;
    protected BigDecimal formattedLow;
    protected double close;
    protected BigDecimal formattedClose;
    protected long volume = 0;
    protected long openInterest = 0;
    protected GregorianCalendar dateTime;
    protected String date;

    public BarData() {}

    public BarData(String date, GregorianCalendar dateTime, double open, double high, double low, double close, long volume) {
        this.date = date;
        this.dateTime = dateTime;
        this.open = open;
        this.formattedOpen = format(open);
        this.close = close;
        this.formattedClose = format(close);
        this.low = low;
        this.formattedLow = format(low);
        this.high = high;
        this.formattedHigh = format(high);
        this.volume = volume;
    }

    //constructor()
    public BarData(String date, GregorianCalendar dateTime, double open, double high, double low, double close, long volume, long openInterest) {
        this(date, dateTime, open, high, low, close, volume);
        this.openInterest = openInterest;
    }

    public String getDate(){return date;}
    public GregorianCalendar getDateTime() {
        return dateTime;
    }
    public double getOpen() {
        return open;
    }
    public double getHigh() {
        return high;
    }
    public double getLow() {
        return low;
    }
    public double getClose() {
        return close;
    }

    public double setOpen() {
        return open;
    }
    public double setHigh() {
        return high;
    }
    public double setLow() {
        return low;
    }
    public double setClose() {
        return close;
    }

    protected BigDecimal format( double price ) {
        return BigDecimal.ZERO;
    }

    public void update( double close ) {
        if( close > high ) {
            high = close;
        }

        if( close < low ) {
            low = close;
        }
        this.close = close;
    }

    @Override
    public String toString() {
        return "Date: " + dateTime.getTime() +
                " Open: " + open +
                " High: " + high +
                " Low: " + low +
                " Close: " + close;
    }








}
