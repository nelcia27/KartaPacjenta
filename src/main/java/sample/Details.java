package sample;

import fhir.ObservationData;
import fhir.PatientData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.text.SimpleDateFormat;

public class Details {
    private final Object prevController;
    private final ObservationData observationData;
    private final SimpleDateFormat mainFormat = new SimpleDateFormat("dd-MM-yyyy");

    @FXML public BorderPane mainPane;
    @FXML public Label detailTitle;
    @FXML public Label detailType;
    @FXML public Label detailDate;


    public Details(Object prevController, ObservationData observationData) {
        this.prevController = prevController;
        this.observationData = observationData;
    }

    @FXML
    public void initialize(){
        detailTitle.setText(observationData.getInfo());
        detailType.setText("Obserwacja");
        detailDate.setText(mainFormat.format(observationData.getAdataTime()));
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/patient.fxml", prevController, mainPane);
    }
}
