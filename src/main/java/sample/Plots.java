package sample;

import fhir.PlotData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import plot.MyLineChart;

import java.util.ArrayList;

public class Plots {
    private final Object prevController;
    private final ArrayList<PlotData> plotData;
    private MyLineChart myLineChart;


    @FXML public BorderPane mainPane;
    @FXML public VBox lineChartVBox;
    @FXML public Button prevButton;
    @FXML public Button plusButton;
    @FXML public Button minusButton;
    @FXML public Button nextButton;

    public Plots(Object prevController, ArrayList<PlotData> plotData){
        this.prevController = prevController;
        this.plotData = plotData;
    }

    @FXML
    public void initialize(){
        myLineChart = new MyLineChart(plotData);
        lineChartVBox.getChildren().add(myLineChart);
        checkIfClickable();
    }

    @FXML
    public void plusClick(){
        if(myLineChart.canZoomIn())
            myLineChart.zoomIn();
        checkIfClickable();
    }

    @FXML
    public void minusClick(){
        if(myLineChart.canZoomOut())
            myLineChart.zoomOut();
        checkIfClickable();
    }

    @FXML
    public void prevClick(){
        if(myLineChart.canGotoPrev())
            myLineChart.gotoPrev();
        checkIfClickable();
    }

    @FXML
    public void nextClick(){
        if(myLineChart.canGotoNext())
            myLineChart.gotoNext();
        checkIfClickable();
    }

    private void checkIfClickable(){
        plusButton.setDisable(!myLineChart.canZoomIn());
        minusButton.setDisable(!myLineChart.canZoomOut());
        nextButton.setDisable(!myLineChart.canGotoNext());
        prevButton.setDisable(!myLineChart.canGotoPrev());
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/patient.fxml", prevController, mainPane);
    }

}
