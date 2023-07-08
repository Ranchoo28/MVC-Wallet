package it.unical.demacs.informatica.mvcwallet.view;

import it.unical.demacs.informatica.mvcwallet.handler.PathHandler;
import it.unical.demacs.informatica.mvcwallet.model.BarData;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CandleStickChart extends XYChart<String, Number> {
    SimpleDateFormat sdf = new SimpleDateFormat();
    private static final String css = PathHandler.getInstance().getPathOfCSS();
    protected static final Logger logger = Logger.getLogger(CandleStickChart.class.getName());
    protected static CandleStickChart chart;
    protected int maxBarsToDisplay;
    protected ObservableList<Series<String, Number>> dataSeries;
    protected BarData lastBar;
    protected NumberAxis yAxis;
    protected CategoryAxis xAxis;

    public CandleStickChart(List<BarData> bars, double paneWidth) {
        this(bars,(int)paneWidth/28);
    }

    public CandleStickChart(List<BarData> bars, int maxBarsToDisplay) {
        this(new CategoryAxis(), new NumberAxis(), bars, maxBarsToDisplay);
    }

    public CandleStickChart(CategoryAxis xAxis, NumberAxis yAxis, List<BarData> bars, int maxBarsToDisplay) {
        super(xAxis, yAxis);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.maxBarsToDisplay = maxBarsToDisplay;

        yAxis.forceZeroInRangeProperty().setValue(Boolean.FALSE);
        setAnimated(true);
        setLegendVisible(false);
        getStylesheets().add(Objects.requireNonNull(getClass().getResource(css + "CandleStickChartStyles.css")).toExternalForm());
        xAxis.setAnimated(true);
        yAxis.setAnimated(true);
        verticalGridLinesVisibleProperty().set(false);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        List<BarData> sublist = getSubList(bars, maxBarsToDisplay);

        for (BarData bar : sublist) {
            String label = bar.getDate();
            series.getData().add(new Data<>(label, bar.getOpen(), bar));
            //logger.log(Level.INFO, "Adding bar with date/time: {0}", bar.getDateTime().getTime());
            //logger.log(Level.INFO, "Adding bar with price: {0}", bar.getOpen());
        }

        dataSeries = FXCollections.observableArrayList(series);
        setData(dataSeries);

        if(sublist.size()>0)
            lastBar = sublist.get(sublist.size() - 1);
    }

    public void addBar(BarData bar) {

        if (dataSeries.get(0).getData().size() >= maxBarsToDisplay) {
            dataSeries.get(0).getData().remove(0);
        }

        int datalength = dataSeries.get(0).getData().size();
        dataSeries.get(0).getData().get(datalength - 1).setYValue(bar.getOpen());
        dataSeries.get(0).getData().get(datalength - 1).setExtraValue(bar);
        String label = sdf.format(bar.getDateTime().getTime());
        logger.log(Level.INFO, "Adding bar with actual time:  {0}", bar.getDateTime().getTime());
        logger.log(Level.INFO, "Adding bar with formated time: {0}", label);

        lastBar = new BarData(bar.getDate(), bar.getDateTime(), bar.getClose(), bar.getClose(), bar.getClose(), bar.getClose(), 1);
        Data<String, Number> data = new XYChart.Data<>(label, lastBar.getOpen(), lastBar);
        dataSeries.get(0).getData().add(data);
    }

    public void updateLast(double price) {
        if (lastBar != null) {
            lastBar.update(price);
            logger.log(Level.INFO, "Updating last bar with date/time: {0}", lastBar.getDateTime().getTime());

            int datalength = dataSeries.get(0).getData().size();
            dataSeries.get(0).getData().get(datalength - 1).setYValue(lastBar.getOpen());

            dataSeries.get(0).getData().get(datalength - 1).setExtraValue(lastBar);
            logger.log(Level.INFO, "Updating last bar with formatteddate/time: {0}", dataSeries.get(0).getData().get(datalength - 1).getXValue());
        }
    }

    protected List<BarData> getSubList(List<BarData> bars, int maxBars) {
        List<BarData> sublist;
        if(maxBars == 0) maxBars = 22;
        if (bars.size() > maxBars) {
            return bars.subList(bars.size() - 1 - maxBars, bars.size() - 1);
        } else {
            return bars;
        }
    }

    // -------------- METHODS ------------------------------------------------------------------------------------------
    /**
     * Called to update and layout the content for the plot
     */
    @Override
    protected void layoutPlotChildren() {
        // we have nothing to layout if no data is present
        if (getData() == null) {
            return;
        }
        // update candle positions
        for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
            Series<String, Number> series = getData().get(seriesIndex);
            Iterator<Data<String, Number>> iter = getDisplayedDataIterator(series);
            Path seriesPath = null;
            if (series.getNode() instanceof Path) {
                seriesPath = (Path) series.getNode();
                seriesPath.getElements().clear();
            }
            while (iter.hasNext()) {
                Data<String, Number> item = iter.next();
                double x = getXAxis().getDisplayPosition(getCurrentDisplayedXValue(item));
                double y = getYAxis().getDisplayPosition(getCurrentDisplayedYValue(item));
                Node itemNode = item.getNode();
                BarData bar = (BarData) item.getExtraValue();
                if (itemNode instanceof Candle && item.getYValue() != null) {
                    Candle candle = (Candle) itemNode;

                    double close = getYAxis().getDisplayPosition(bar.getClose());
                    double high = getYAxis().getDisplayPosition(bar.getHigh());
                    double low = getYAxis().getDisplayPosition(bar.getLow());
                    double candleWidth = 10;
                    // update candle
                    candle.update(close - y, high - y, low - y, candleWidth);

                    // update tooltip content
                    candle.updateTooltip(bar.getOpen(), bar.getClose(), bar.getHigh(), bar.getLow());

                    // position the candle
                    candle.setLayoutX(x);
                    candle.setLayoutY(y);
                }

            }
        }
    }

    @Override
    protected void dataItemChanged(Data<String, Number> item) {
    }

    @Override
    protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
        Node candle = createCandle(getData().indexOf(series), item, itemIndex);
        if (shouldAnimate()) {
            candle.setOpacity(0);
            getPlotChildren().add(candle);
            // fade in new candle
            FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(1);
            ft.play();
        } else {
            getPlotChildren().add(candle);
        }
        // always draw average line on top
        if (series.getNode() != null) {
            series.getNode().toFront();
        }
    }

    @Override
    protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
        final Node candle = item.getNode();
        if (shouldAnimate()) {
            // fade out old candle
            FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(0);
            ft.setOnFinished((ActionEvent actionEvent) -> {
                getPlotChildren().remove(candle);
            });
            ft.play();
        } else {
            getPlotChildren().remove(candle);
        }
    }

    @Override
    protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
        // handle any data already in series
        for (int j = 0; j < series.getData().size(); j++) {
            Data item = series.getData().get(j);
            Node candle = createCandle(seriesIndex, item, j);
            if (shouldAnimate()) {
                candle.setOpacity(0);
                getPlotChildren().add(candle);
                // fade in new candle
                FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(1);
                ft.play();
            } else {
                getPlotChildren().add(candle);
            }
        }
        // create series path
        Path seriesPath = new Path();
        seriesPath.getStyleClass().setAll("candlestick-average-line", "series" + seriesIndex);
        series.setNode(seriesPath);
        getPlotChildren().add(seriesPath);
    }

    @Override
    protected void seriesRemoved(Series<String, Number> series) {
        // remove all candle nodes
        for (XYChart.Data<String, Number> d : series.getData()) {
            final Node candle = d.getNode();
            if (shouldAnimate()) {
                // fade out old candle
                FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(0);
                ft.setOnFinished((ActionEvent actionEvent) -> {
                    getPlotChildren().remove(candle);
                });
                ft.play();
            } else {
                getPlotChildren().remove(candle);
            }
        }
    }

    /**
     * Create a new Candle node to represent a single data item
     *
     * @param seriesIndex The index of the series the data item is in
     * @param item The data item to create node for
     * @param itemIndex The index of the data item in the series
     * @return New candle node to represent the give data item
     */
    private Node createCandle(int seriesIndex, final Data item, int itemIndex) {
        Node candle = item.getNode();
        // check if candle has already been created
        if (candle instanceof Candle) {
            ((Candle) candle).setSeriesAndDataStyleClasses("series" + seriesIndex, "data" + itemIndex);
        } else {
            candle = new Candle("series" + seriesIndex, "data" + itemIndex);
            item.setNode(candle);
        }
        return candle;
    }

    /**
     * This is called when the range has been invalidated and we need to update
     * it. If the axis are auto ranging then we compile a list of all data that
     * the given axis has to plot and call invalidateRange() on the axis passing
     * it that data.
     */
    @Override
    protected void updateAxisRange() {
        // For candle stick chart we need to override this method as we need to let the axis know that they need to be able
        // to cover the whole area occupied by the high to low range not just its center data value
        final Axis<String> xa = getXAxis();
        final Axis<Number> ya = getYAxis();
        List<String> xData = null;
        List<Number> yData = null;
        if (xa.isAutoRanging()) {
            xData = new ArrayList<>();
        }
        if (ya.isAutoRanging()) {
            yData = new ArrayList<>();
        }
        if (xData != null || yData != null) {
            for (Series<String, Number> series : getData()) {
                for (Data<String, Number> data : series.getData()) {
                    if (xData != null) {
                        xData.add(data.getXValue());
                    }
                    if (yData != null) {
                        BarData extras = (BarData) data.getExtraValue();
                        if (extras != null) {
                            yData.add(extras.getHigh());
                            yData.add(extras.getLow());
                        } else {
                            yData.add(data.getYValue());
                        }
                    }
                }
            }
            if (xData != null) {
                xa.invalidateRange(xData);
            }
            if (yData != null) {
                ya.invalidateRange(yData);
            }
        }
    }
}

