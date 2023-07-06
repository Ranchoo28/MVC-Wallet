package it.unical.demacs.informatica.mvcwallet.handler;

import it.unical.demacs.informatica.mvcwallet.model.Coin;

import java.util.*;

public class CoinsHandler {

    public static final CoinsHandler instance = new CoinsHandler();

    public static CoinsHandler getInstance() {
        return instance;
    }

    private static Coin btc = new Coin("Bitcoin", 0);
    private static Coin eth = new Coin("Ethereum", 0);
    private static Coin sol = new Coin("Solana", 0);
    private static Coin bnb = new Coin("Binance Coin", 0);

    private final static Map<String, Coin> coins;

    static {
        coins = new HashMap<>();
        coins.put(btc.getName(), btc);
        coins.put(eth.getName(), eth);
        coins.put(sol.getName(), sol);
        coins.put(bnb.getName(), bnb);
    }
    public Map<String, Coin> getCoins() {
        return coins;
    }
    public List<String> getCoinNames() {
        ArrayList<String> coinNames = new ArrayList<>(coins.keySet());
        Collections.sort(coinNames);
        return coinNames;
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
