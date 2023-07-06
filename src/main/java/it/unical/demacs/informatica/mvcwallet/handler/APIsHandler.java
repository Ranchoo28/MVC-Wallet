package it.unical.demacs.informatica.mvcwallet.handler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class APIsHandler {

    private static final APIsHandler instance = new APIsHandler();

    public static APIsHandler getInstance() {
        return instance;
    }

    /*Coingecko API*/
    String coingeckoAPI = "https://api.coingecko.com/api/v3/coins/";

    LocalDate today = LocalDate.now();
    LocalDate decemberTwelveTwelve = LocalDate.of(2012, 12, 12);
    int days = (int) ChronoUnit.DAYS.between(decemberTwelveTwelve, today);

    public Map<String, ArrayList<Double>> getHistoricalData(String coin, String vsCurrency, String timeframe) throws MalformedURLException {
        LocalDate endDate = today;
        LocalDate startDate = endDate.minusDays(days);

        // Formatta le date nel formato richiesto da Coingecko
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String startFormatted = startDate.format(formatter);
        String endFormatted = endDate.format(formatter);

        // Costruisce l'URL per la richiesta API
        String requestUrl = coingeckoAPI + coin + "/market_chart?vs_currency=" + vsCurrency + "&days=" + days
                + "&start_date=" + startFormatted + "&end_date=" + endFormatted;

        // Effettua la richiesta GET all'API di CoinGecko
        try {
            // Effettua la richiesta API
            URL url = new URL(requestUrl);
            //System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leggi la risposta JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Elabora la risposta JSON
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                JsonArray prices = jsonResponse.get("prices").getAsJsonArray();

                HashMap<String, ArrayList<Double>> dictionary = new HashMap<>();

                for(JsonElement element: prices){
                    JsonArray priceData = element.getAsJsonArray();
                    long timestamp = priceData.get(0).getAsLong();

                    String localDate = TimeStampHandler.getInstance().convertToString(timestamp, timeframe);
                    if(dictionary.containsKey(localDate)){
                        dictionary.get(localDate).add(priceData.get(1).getAsDouble());
                    }
                    else{
                        ArrayList<Double> dailyPrices = new ArrayList<>();
                        dictionary.put(localDate, dailyPrices);
                        dictionary.get(localDate).add(priceData.get(1).getAsDouble());
                    }
                }
                return dictionary;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}