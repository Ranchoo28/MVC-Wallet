package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BuildBarsService {
    private static final BarBuilder barBuilder = BarBuilder.getInstance();
    private BuildBarsService(){}
    private static final BuildBarsService instance = new BuildBarsService();
    public static BuildBarsService getInstance(){return instance;}

    public List<BarData> buildBars(String coin, String currency, String timeframe){
        var ref = new Object() {
             List<BarData> bars = new ArrayList<>();
        };

        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> ref.bars = barBuilder.make(coin, currency, timeframe));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return ref.bars;
    }
}
