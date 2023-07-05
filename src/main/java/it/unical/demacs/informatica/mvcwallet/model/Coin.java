package it.unical.demacs.informatica.mvcwallet.model;

import java.util.Objects;

public class Coin {
    private String name;
    private double all;
    private double equivalent;

    public Coin(String name, double all, double equivalent){
            this.name = name;
            this.all = all;
            this.equivalent = equivalent;
    }


    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public double getAll() {
        return all;
    }

    public void setAll(double all) {
        this.all = all;
    }

    public double getEquivalent() {
        return equivalent;
    }

    public void setEquivalent(double equivalent) {
        this.equivalent = equivalent;
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
