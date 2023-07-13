package it.unical.demacs.informatica.mvcwallet.model;

import java.util.Objects;

public class Coin {
    private String name;
    private final String code;
    private double amount;

    public Coin(String name, double amount, String code){
        this.name = name;
        this.amount = amount;
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public String getCode(){return code;}

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double all) {
        this.amount = all;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return Objects.equals(name, coin.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}
