package sample;

import fhir.MainResourceGetter;
import fhir.PatientData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML public TextField searchField;
    @FXML public BorderPane mainPane;
    @FXML public Button searchButton;
    @FXML public VBox patientsVBox;

    private final MainResourceGetter fhirResourceGetter;
    private final ArrayList<PatientData> patientList = new ArrayList<>();
    private final ArrayList<PatientData> shownList = new ArrayList<>();

    public Controller(MainResourceGetter fhirResourceGetter) {
        this.fhirResourceGetter = fhirResourceGetter;
    }

    @FXML public void initialize(){
        searchField.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.05));
        searchButton.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.05));
        patientsVBox.prefWidthProperty().bind(mainPane.widthProperty());
        initPatientsList();
        showPatients();
    }

    private void initPatientsList(){
        patientList.clear();
        patientList.addAll(fhirResourceGetter.getPatients());
        shownList.addAll(patientList);
    }

    private void showPatients(){
        patientsVBox.getChildren().clear();
        for(PatientData patients : shownList){
            addPatientButton(patients);
        }
    }

    private void addPatientButton(PatientData patient){
        Button patientButton = new Button(patient.getName());
        patientButton.getStyleClass().add("patient_button");
        patientButton.setOnMouseClicked(event -> changeTo(new Patient(this, patient)));
        patientsVBox.getChildren().add(patientButton);
    }

    private void changeTo(Patient patient){
        Main.changeScene("/fxml/patient.fxml", patient, mainPane);
    }

    @FXML
    public void search(){
        if("".equals(searchField.getText())){
            shownList.addAll(patientList);
            showPatients();
            return;
        }
        shownList.clear();
        for (PatientData patient : patientList) {
            if(patient.getName().toLowerCase().contains(searchField.getText().toLowerCase())){
                shownList.add(patient);
            }
        }
        showPatients();
    }

}
