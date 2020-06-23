package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Date;

public class Plots {
    private final Object prevController;
    private XYChart.Series<Number, Number> series;
    private int temp = 13;
    private int upperBound = 0;
    private int lowerBound = 13;


    @FXML public BorderPane mainPane;
    @FXML public VBox lineChartVBox;

    public Plots(Object prevController){
        this.prevController = prevController;
    }

    @FXML
    public void initialize(){
        createPlot();
    }

    private void createPlot(){  // TODO: data
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Data");
        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        series = new XYChart.Series<Number, Number>();
        XYChart.Data<Number, Number> dat1 = new XYChart.Data<Number, Number>(1, 23);

        StackPane node = new StackPane();
        node.getStyleClass().add("stackPanePlot");
        Label b = new Label();
        node.getChildren().add(b);
        dat1.setNode(node);
        series.getData().add(dat1);

        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        lineChart.getData().add(series);
        lineChart.setAnimated(false);

        NumberAxis ax = (NumberAxis) lineChart.getXAxis();
        ax.setAutoRanging(false);


        lineChartVBox.getChildren().add(lineChart);
    }

    @FXML
    public void plusClick(){
        series.getData().add(new XYChart.Data(temp++, 25));
        series.getData().remove(0);
        NumberAxis ax = (NumberAxis) series.getChart().getXAxis();
        ax.setLowerBound(++lowerBound);
        ax.setUpperBound(++upperBound);

    }

    @FXML
    public void minusClick(){

    }

    @FXML
    public void prevClick(){

    }

    @FXML
    public void nextClick(){

    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/patient.fxml", prevController, mainPane);
    }

}
