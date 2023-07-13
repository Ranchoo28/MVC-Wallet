package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.APIsHandler;
import it.unical.demacs.informatica.mvcwallet.handler.TimeStampHandler;

import java.net.MalformedURLException;
import java.util.*;

// Classe usata per costruire il grafico in market
public class BarBuilder {
    private static final APIsHandler apisHandler = APIsHandler.getInstance();
    private static final TimeStampHandler timeStampHandler = TimeStampHandler.getInstance();

    private static final BarBuilder instance = new BarBuilder();
    public static BarBuilder getInstance() {
        return instance;
    }

    public List<BarData> make(String coin, String currency, String timeframe) {

        final List<BarData> bars = new ArrayList<>();
        Map<String, ArrayList<Double>> dictionary;

        try {
            dictionary = apisHandler.getHistoricalData(coin, currency, timeframe);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            for (String key : dictionary.keySet()) {
                ArrayList<Double> dailyPrices = dictionary.get(key);

                double highPrice = Collections.max(dailyPrices);
                double lowPrice = Collections.min(dailyPrices);
                double openPrice = dailyPrices.get(0);
                double closePrice = dailyPrices.get(dailyPrices.size() - 1);

                GregorianCalendar date = timeStampHandler.convertToGregorianCalendar(key, timeframe);

                BarData bar = new BarData(key, date, openPrice, highPrice, lowPrice, closePrice, 1);
                bars.add(bar);
            }
            bars.sort(Comparator.comparing(BarData::getDateTime));
        } catch (Exception e){
            System.out.println("Error Message: You have exceeded the API request rate limit. Please wait before submitting a new request.");
        }
        return bars;
    }
}

