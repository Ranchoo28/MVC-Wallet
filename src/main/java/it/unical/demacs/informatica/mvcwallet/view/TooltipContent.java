package it.unical.demacs.informatica.mvcwallet.view;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;

public class TooltipContent extends GridPane {

    private final Label openValue = new Label();
    private final Label closeValue = new Label();
    private final Label highValue = new Label();
    private final Label lowValue = new Label();

    TooltipContent() {
        Label open = new Label("Open:");
        Label close = new Label("Close:");
        Label high = new Label("High:");
        Label low = new Label("Low:");
        open.getStyleClass().add("candlestick-tooltip-label");
        close.getStyleClass().add("candlestick-tooltip-label");
        high.getStyleClass().add("candlestick-tooltip-label");
        low.getStyleClass().add("candlestick-tooltip-label");
        openValue.getStyleClass().add("candlestick-tooltip-data-label");
        closeValue.getStyleClass().add("candlestick-tooltip-data-label");
        highValue.getStyleClass().add("candlestick-tooltip-data-label");
        lowValue.getStyleClass().add("candlestick-tooltip-data-label");
        setConstraints(open, 0, 0);
        setConstraints(openValue, 1, 0);
        setConstraints(close, 0, 1);
        setConstraints(closeValue, 1, 1);
        setConstraints(high, 0, 2);
        setConstraints(highValue, 1, 2);
        setConstraints(low, 0, 3);
        setConstraints(lowValue, 1, 3);
        getChildren().addAll(open, openValue, close, closeValue, high, highValue, low, lowValue);
    }

    public void update(double open, double close, double high, double low) {
        openValue.setText(String.valueOf(new BigDecimal(open).setScale(2, BigDecimal.ROUND_HALF_UP)));
        closeValue.setText(String.valueOf(new BigDecimal(close).setScale(2, BigDecimal.ROUND_HALF_UP)));
        highValue.setText(String.valueOf(new BigDecimal(high).setScale(2, BigDecimal.ROUND_HALF_UP)));
        lowValue.setText(String.valueOf(new BigDecimal(low).setScale(2, BigDecimal.ROUND_HALF_UP)));
    }
}
