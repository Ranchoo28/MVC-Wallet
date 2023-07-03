package it.unical.demacs.informatica.mvcwallet.model;

import java.util.Collections;
import java.util.List;

public record CurrencyDataResult (List<CurrencyData> allElements) {

    public CurrencyDataResult {
        Collections.sort(allElements);
    }
}
