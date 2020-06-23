package sample;

import fhir.MedicationData;
import fhir.ObservationData;
import fhir.PatientData;
import fhir.PatientDetailData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;

public class Details {
    private final Object prevController;
    private final PatientDetailData patientDetailData;
    private final PatientData patientData;
    private final SimpleDateFormat mainFormat = new SimpleDateFormat("dd-MM-yyyy");

    @FXML public BorderPane mainPane;
    @FXML public BorderPane topBorderPane;
    @FXML public Label detailTitle;
    @FXML public Label detailType;
    @FXML public Label detailDate;
    @FXML public VBox mainVBox;


    public Details(Object prevController, PatientDetailData patientDetailData, PatientData patientData) {
        this.prevController = prevController;
        this.patientDetailData = patientDetailData;
        this.patientData = patientData;
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
            Button edit = new Button("Edytuj");
            edit.getStyleClass().add("filtrCombo");
            edit.setOnAction(e -> editDetails());
            topBorderPane.setRight(edit);
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
        Main.changeScene("/fxml/detailsEdit.fxml", new DetailsEdit(this, (ObservationData) patientDetailData, patientData), mainPane);
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/patient.fxml", prevController, mainPane);
    }
}
