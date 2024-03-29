package it.unical.demacs.informatica.mvcwallet.handler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.google.gson.*;
import javafx.application.Platform;

// Classe per la gestione dell API

public class APIsHandler {
    private static final TimeStampHandler timeStampHandler = TimeStampHandler.getInstance();
    private static final APIsHandler instance = new APIsHandler();
    public static APIsHandler getInstance() {
        return instance;
    }

    private final AlertHandler alertHandler = AlertHandler.getInstance();
    private final ResourceBundle bundle = LanguageHandler.getInstance().getBundle();

    /*Binance API*/
    public double getCurrentPrice(String code,String currency){

        OkHttpClient client = new OkHttpClient();
        Request request =null;
        if (currency.equals("USD")) {
            request = new Request.Builder().url("https://api.binance.com/api/v3/ticker/price?symbol=" + code + "USDT").build();
        }else if(currency.equals("EUR")){
            request = new Request.Builder().url("https://api.binance.com/api/v3/ticker/price?symbol=" + code + currency).build();
        }
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String jsonData = responseBody.string();
                String priceString = jsonData.split("\"price\":\"")[1].split("\"")[0];
                return Double.parseDouble(priceString);

                // Parse the JSON response to get the price
                // Assuming the response is in the format {"symbol":"BTCUSDT","price":"XXXXX"}
            } else {
                System.out.println("Empty response body");
            }
        } catch (IOException e) {
            return -1.0;
        }
        return -1.0;
    }

    /*Coingecko API*/
    String coingeckoAPI = "https://api.coingecko.com/api/v3/coins/";

    LocalDate today = LocalDate.now();
    LocalDate decemberTwelveTwelve = LocalDate.of(2012, 12, 12);

    //Calcola quanti giorni sono trascorsi dal 12-12-2012 a Oggi
    int days = (int) ChronoUnit.DAYS.between(decemberTwelveTwelve, today);

    public Map<String, ArrayList<Double>> getHistoricalData(String coin, String vsCurrency, String timeframe) throws MalformedURLException {
        LocalDate endDate = today;
        LocalDate startDate = endDate.minusDays(days);

        // Formatta le date nel formato richiesto da Coingecko
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String startFormatted = startDate.format(formatter);
        String endFormatted = endDate.format(formatter);
        String requestUrl;

        // Costruisce l'URL per la richiesta API
        if(Objects.equals(timeframe, "1D")) { //Limitato a 90 giorni
            requestUrl = coingeckoAPI + coin + "/market_chart?vs_currency=" + vsCurrency + "&days=90" + "&interval=1m";
        } else{
            requestUrl = coingeckoAPI + coin + "/market_chart?vs_currency=" + vsCurrency + "&days=" + days + "&start_date=" + startFormatted + "&end_date=" + endFormatted + "&interval=1m";
        }

        URL url = new URL(requestUrl);

        HashMap<String, ArrayList<Double>> dictionary = new HashMap<>();
        StringBuilder response = null;
        HttpURLConnection connection = null;
        int responseCode = 404;

        // Tenativo di connessione all'API
        try {
            connection = (HttpURLConnection) url.openConnection();
            // Effettua la richiesta GET all'API di CoinGecko
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            Platform.runLater(alertHandler::createConnectionErrorAlert);
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try{
                // Leggi la risposta JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } catch (Exception e) {
                Platform.runLater(alertHandler::createConnectionErrorAlert);
            }

            try {
                // Elabora la risposta JSON
                Gson gson = new Gson();
                assert response != null;
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                JsonArray prices = jsonResponse.get("prices").getAsJsonArray();

                for (JsonElement element : prices) {
                    JsonArray priceData = element.getAsJsonArray();
                    long timestamp = priceData.get(0).getAsLong();
                    String localDate = timeStampHandler.convertToString(timestamp, timeframe);

                    if (dictionary.containsKey(localDate)) {
                        dictionary.get(localDate).add(priceData.get(1).getAsDouble());
                    } else {
                        ArrayList<Double> dailyPrices = new ArrayList<>();
                        dictionary.put(localDate, dailyPrices);
                        dictionary.get(localDate).add(priceData.get(1).getAsDouble());
                    }
                }
            } catch (JsonSyntaxException e) {
                System.out.println("Errore nell'elaborazione del JSON");
            }
        } else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND){
            System.out.println("ERROR "+responseCode+": Coin not found");
        } else if(responseCode == 429){
            Platform.runLater(alertHandler::createRequestErrorAlert);
        }
        return dictionary;
    }
}