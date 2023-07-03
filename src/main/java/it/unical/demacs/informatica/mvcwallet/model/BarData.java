package it.unical.demacs.informatica.mvcwallet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

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
    protected int barLength = 1;
    protected GregorianCalendar dateTime;

    public BarData() {}

    public BarData(GregorianCalendar dateTime, double open, double high, double low, double close, long volume, long openInterest) {
        this(dateTime, open, high, low, close, volume);
        this.openInterest = openInterest;
    }//constructor()

    public BarData( GregorianCalendar dateTime, double open, double high, double low, double close, long volume) {
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

    protected BigDecimal format( double price ) {
        return BigDecimal.ZERO;
    }
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
    public long getVolume() {
        return volume;
    }
    public long getOpenInterest() {
        return openInterest;
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
    public long setVolume() {
        return volume;
    }
    public long setOpenInterest() {
        return openInterest;
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
    public void setOpenInterest(long openInterest) {
        this.openInterest = openInterest;
    }

    @Override
    public String toString() {
        return "Date: " + dateTime.getTime() +
                " Open: " + open +
                " High: " + high +
                " Low: " + low +
                " Close: " + close +
                " Volume: " + volume +
                " Open Int " + openInterest;
    }//toString()








}
