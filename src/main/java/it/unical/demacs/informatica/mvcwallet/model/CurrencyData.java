package it.unical.demacs.informatica.mvcwallet.model;

public record CurrencyData (String symbol, Double priceChange, Double openPrice, Double highPrice, Double lowPrice) implements Comparable<CurrencyData>{

    @Override
    public int compareTo(CurrencyData o) {
        return symbol.compareTo(o.symbol);
    }

}
/*
    "symbol":"ETHBTC",
    "priceChange":"0.00106000",
"priceChangePercent":"1.688",
"weightedAvgPrice":"0.06313759",
"prevClosePrice":"0.06278000",
"lastPrice":"0.06384000",
"lastQty":"0.02750000",
"bidPrice":"0.06384000",
"bidQty":"25.14240000",
"askPrice":"0.06385000",
"askQty":"5.36150000",
    "openPrice":"0.06278000",
    "highPrice":"0.06390000",
    "lowPrice":"0.06256000",
"volume":"26157.15970000",
"quoteVolume":"1651.49996875",
"openTime":1688283374454,
"closeTime":1688369774454,
"firstId":420520506,
"lastId":420569823,
"count":49318
 */