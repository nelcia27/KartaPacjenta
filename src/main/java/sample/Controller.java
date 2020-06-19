package sample;

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

    private final ArrayList<String> patientList = new ArrayList<>();  // TODO: Patient zamiast string
    private ArrayList<String> shownList = new ArrayList<>();

    @FXML public void initialize(){
        searchField.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.05));
        searchButton.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.05));
        patientsVBox.prefWidthProperty().bind(mainPane.widthProperty());
        initPatientsList();
        showPatients();
    }

    private void initPatientsList(){
        patientList.clear();
        patientList.add("Krzysztof Sychla");
        patientList.add("Kornelia Staszewska");
        shownList = (ArrayList<String>) patientList.clone();
    }

    private void showPatients(){
        patientsVBox.getChildren().clear();
        for(String patients : shownList){
            addPatientButton(patients);
        }
    }

    private void addPatientButton(String name){    // TODO: Patient info
        Button patientButton = new Button(name);
        patientButton.getStyleClass().add("patient_button");
        patientButton.setOnMouseClicked(event -> changeTo(new Patient(this)));
        patientsVBox.getChildren().add(patientButton);
    }

    private void changeTo(Patient patient){
        Main.changeScene("/fxml/patient.fxml", patient, mainPane);
    }

    @FXML
    public void search(){
        if("".equals(searchField.getText())){
            shownList = (ArrayList<String>) patientList.clone();
            showPatients();
            return;
        }
        shownList.clear();
        for (String patient : patientList) {
            if(patient.toLowerCase().contains(searchField.getText().toLowerCase())){
                shownList.add(patient);
            }
        }
        showPatients();
    }

}
