package it.unical.demacs.informatica.mvcwallet.model;

import java.util.*;

public class ListOfCoins {

    public static final ListOfCoins instance = new ListOfCoins();

    public static ListOfCoins getInstance() {
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
        return new ArrayList<>(coins.keySet());
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
