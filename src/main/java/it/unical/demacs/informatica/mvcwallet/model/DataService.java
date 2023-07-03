package it.unical.demacs.informatica.mvcwallet.model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataService extends Service<CurrencyDataResult> {

    private final String data = "https://data.binance.com/api/v3/ticker/24hr";

    @Override
    protected Task<CurrencyDataResult> createTask() {
        return new Task<>() {
            @Override
            protected CurrencyDataResult call() throws Exception {
                URL csvData = new URL(data);
                BufferedReader reader = new BufferedReader(new InputStreamReader(csvData.openStream()));
                reader.readLine(); //Ignore first row
                String line = reader.readLine();
                List<CurrencyData> l = new ArrayList<>();
                while (line != null) {
                    String[] res = line.split(",");
                    l.add(new CurrencyData(res[0], Double.parseDouble(res[1]), Double.parseDouble(res[11]), Double.parseDouble(res[12]), Double.parseDouble(res[13])));
                    line = reader.readLine();
                }
                reader.close();
                return new CurrencyDataResult(l);
            }
        };
    }

}