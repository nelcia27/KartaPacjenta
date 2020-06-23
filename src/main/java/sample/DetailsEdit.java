package sample;

import fhir.ObservationData;
import fhir.PatientData;
import fhir.PatientDetailData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class DetailsEdit {
    private final Object prevController;
    private final ObservationData observationData;
    private final PatientData patientData;

    @FXML public BorderPane mainPane;
    @FXML public TextField valueUpdate;
    @FXML public TextField unitUpdate;


    public DetailsEdit(Object prevController, ObservationData observationData, PatientData patientData) {
        this.prevController = prevController;
        this.observationData = observationData;
        this.patientData = patientData;
    }

    @FXML
    public void initialize(){
        valueUpdate.setText(observationData.getValue().toString());
        unitUpdate.setText(observationData.getUnit());
    }

    @FXML
    public void updateObservation(){
        observationData.update(Double.parseDouble(valueUpdate.getText()), unitUpdate.getText());
        returnTo();
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/details.fxml", prevController, mainPane);
    }
}
