package it.unical.demacs.informatica.mvcwallet.model;

import it.unical.demacs.informatica.mvcwallet.handler.APIsHandler;
import it.unical.demacs.informatica.mvcwallet.handler.SqlHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class PriceService {
    private static final PriceService priceService = PriceService.getInstance();

    private static final PriceService instance = new PriceService();
    public static PriceService getInstance() { return instance; }

    private final APIsHandler apisHandler = APIsHandler.getInstance();

    public double serviceGetCurrentPrice(String code, String currency){
        final double[] res = new double[1];
        ExecutorService queryExe = Executors.newSingleThreadExecutor();
        Future<?> future = queryExe.submit(() -> res[0] = apisHandler.getCurrentPrice(code, currency));

        try { future.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace();}
        finally { queryExe.shutdown(); }
        return res[0];
    }
}
