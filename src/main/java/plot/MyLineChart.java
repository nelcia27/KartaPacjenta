package plot;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;

public class MyLineChart extends LineChart<String, Number> {
    private final XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

    public MyLineChart() {
        super(new CategoryAxis(), new NumberAxis());
    }

    public MyLineChart(ObservableList data) {
        super(new CategoryAxis(), new NumberAxis(), data);
    }

    public void initialize(){

    }

    public void zoomIn(){

    }

    public void zoomOut(){

    }

    public void gotoNext(){

    }

    public void gotoPrev(){

    }

}
