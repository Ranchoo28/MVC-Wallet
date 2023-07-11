package it.unical.demacs.informatica.mvcwallet.model;

import java.io.Serializable;
import java.util.*;

public class BarData implements Serializable {

    protected double open;
    protected double high;
    protected double low;
    protected double close;
    protected long volume;
    protected GregorianCalendar dateTime;
    protected String date;

    public BarData(String date, GregorianCalendar dateTime, double open, double high, double low, double close, long volume) {
        this.date = date;
        this.dateTime = dateTime;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.volume = volume;
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
