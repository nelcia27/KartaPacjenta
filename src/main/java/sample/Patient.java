package sample;

import fhir.PatientData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.text.SimpleDateFormat;

public class Patient {
    private final Object prevController;
    private final PatientData patientInfo;
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    @FXML public BorderPane mainPane;
    @FXML public Label patientName;
    @FXML public Label patientGender;
    @FXML public Label patientBirthDate;
    @FXML public Label patientAddress;
    @FXML public Label patientPhoneNumber;

    public Patient(Object prevController, PatientData patientInfo) {
        this.prevController = prevController;
        this.patientInfo = patientInfo;
    }

    @FXML public void initialize(){
        patientName.setText(patientInfo.getName());
        patientGender.setText(patientInfo.getSex());
        patientBirthDate.setText(format.format(patientInfo.getDateBirth()));
        patientAddress.setText(patientInfo.getAddress());
        patientPhoneNumber.setText(patientInfo.getPhone());
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/sample.fxml", prevController, mainPane);
    }
}
