package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.model.Coin;

import java.util.*;

public class CoinsHandler {

    public static final CoinsHandler instance = new CoinsHandler();

    public static CoinsHandler getInstance() {
        return instance;
    }

    private static final Coin btc = new Coin("Bitcoin", 0, "BTC",0.0);
    private static final Coin eth = new Coin("Ethereum", 0, "ETH",0.0);
    private static final Coin sol = new Coin("Solana", 0, "SOL",0.0);
    private static final Coin bnb = new Coin("Binance Coin", 0, "BNB",0.0);

    private final static Map<String, Coin> coins;

    static {
        coins = new HashMap<>();
        coins.put(btc.getCode(), btc);
        coins.put(eth.getCode(), eth);
        coins.put(sol.getCode(), sol);
        coins.put(bnb.getCode(), bnb);
    }
    public Map<String, Coin> getCoins() {
        return coins;
    }
    public List<String> getCoinCode() {
        ArrayList<String> coinCodes = new ArrayList<>(coins.keySet());
        Collections.sort(coinCodes);
        return coinCodes;
    }
    /*
    Bisogna implementare un metodo per controllare che il nome sia corretto.

    public void addCoin(String name, double amount){
        Coin coin = new Coin(name, amount);
        if(!coins.containsKey(name)){
            coins.put(name, coin);
        }
    }
    */
    public void setAmount(String coin, double value){
        coins.get(coin).setAmount(value);
    }
}
