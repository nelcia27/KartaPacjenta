package sample;

import fhir.MedicationData;
import fhir.ObservationData;
import fhir.PatientDetailData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;

public class Details {
    private final Object prevController;
    private final PatientDetailData patientDetailData;
    private final SimpleDateFormat mainFormat = new SimpleDateFormat("dd-MM-yyyy");

    @FXML public BorderPane mainPane;
    @FXML public Label detailTitle;
    @FXML public Label detailType;
    @FXML public Label detailDate;
    @FXML public VBox mainVBox;


    public Details(Object prevController, PatientDetailData patientDetailData) {
        this.prevController = prevController;
        this.patientDetailData = patientDetailData;
    }

    @FXML
    public void initialize(){
        detailTitle.setText(patientDetailData.getInfo());
        detailType.setText(patientDetailData.getDescription());
        detailDate.setText(mainFormat.format(patientDetailData.getAdataTime()));
        if(patientDetailData.getClass() == ObservationData.class){
            ObservationData data = (ObservationData) patientDetailData;
            String result = String.format("%.2f", data.getValue());
            createLabel("Wartość: "+result+" "+data.getUnit());
            createLabel("Kategoria: "+data.getCategory());
//            createLabel(data.getUnit());
        } else if(patientDetailData.getClass() == MedicationData.class){
            MedicationData data = (MedicationData) patientDetailData;
            createLabel("Lekarz prowadzący: "+data.getMedicianName());
        }
    }

    private void createLabel(String text){
        Label label = new Label(text);
        mainVBox.getChildren().add(label);
    }

    @FXML
    public void editDetails(){

    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/patient.fxml", prevController, mainPane);
    }
}
