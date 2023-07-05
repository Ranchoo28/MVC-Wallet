package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.APIsHandler;
import it.unical.demacs.informatica.mvcwallet.handler.TimeStampHandler;

import java.net.MalformedURLException;
import java.util.*;

public class BuildBars {
    private static final BuildBars instance = new BuildBars();
    public static BuildBars getInstance() {
        return instance;
    }

    public List<BarData> buildBars(String coin, String currency){

        final List<BarData> bars = new ArrayList<>();
        Map<String, ArrayList<Double>> dictionary;

        try {
            dictionary = APIsHandler.getInstance().getHistoricalData(coin, currency);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        for(String key : dictionary.keySet()){
            ArrayList<Double> dailyPrices = dictionary.get(key);

            double highPrice = Collections.max(dailyPrices);
            double lowPrice = Collections.min(dailyPrices);
            double openPrice = dailyPrices.get(0);
            double closePrice = dailyPrices.get(dailyPrices.size()-1);

            GregorianCalendar date = TimeStampHandler.getInstance().convertToGregorianCalendar(key);
            BarData bar = new BarData(date, openPrice, highPrice, lowPrice, closePrice, 1);
            bars.add(bar);
        }

        bars.sort(Comparator.comparing(BarData::getDateTime));

        return bars;
    }
}

