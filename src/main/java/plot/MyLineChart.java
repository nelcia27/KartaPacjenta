package plot;

import fhir.PlotData;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyLineChart extends LineChart<Number, Number> {
    private final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    private final ArrayList<PlotData> plotData;
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private int upperBound = 1;
    private int lowerBound = 0;

    public MyLineChart(ArrayList<PlotData> plotData) {
        super(new NumberAxis(), new NumberAxis());
        this.plotData = plotData;
        initialize();
    }

    public MyLineChart(ObservableList data, ArrayList<PlotData> plotData) {
        super(new NumberAxis(), new NumberAxis(), data);
        this.plotData = plotData;
    }

    private void initialize(){
        for(PlotData data : plotData){
            XYChart.Data<Number, Number> d = new XYChart.Data<Number, Number>(upperBound++, data.getValue());
            Button but = new Button();
            but.getStyleClass().add("stackPanePlot");
            String res = String.format("%.2f", data.getValue());
            but.setTooltip(new Tooltip(res));
            d.setNode(but);
            series.getData().add(d);
        }
        this.getData().add(series);
        formatAxis();
    }

    private void updateAxis(){
        NumberAxis ax = (NumberAxis) this.getXAxis();
        ax.setLowerBound(lowerBound);
        ax.setUpperBound(upperBound);
    }

    private void formatAxis(){
        NumberAxis ax = (NumberAxis) this.getXAxis();
        this.setAnimated(false);
        ax.setTickUnit(1.);
        ax.setAutoRanging(false);
        ax.setLowerBound(lowerBound);
        ax.setUpperBound(upperBound);
        ax.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                try {
                    return format.format(plotData.get(object.intValue() - 1).getDate());
                }catch (IndexOutOfBoundsException e){
                    return null;
                }
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });

    }

    public void zoomIn(){
        lowerBound++;
        upperBound--;
        updateAxis();
    }

    public void zoomOut(){
        lowerBound--;
        upperBound++;
        updateAxis();
    }

    public void gotoNext(){
        lowerBound++;
        upperBound++;
        updateAxis();
    }

    public void gotoPrev(){
        lowerBound--;
        upperBound--;
        updateAxis();
    }

    public boolean canZoomIn(){
        return upperBound > lowerBound;
    }

    public boolean canZoomOut(){
        return lowerBound > 0;
    }

    public boolean canGotoNext(){
        return upperBound < plotData.size();
    }

    public boolean canGotoPrev(){
        return lowerBound > 1;
    }
}
