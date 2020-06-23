package sample;

import fhir.MainResourceGetter;
import fhir.PatientData;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class EditPatient {
    private final Object prevController;
    private final PatientData patient;

    @FXML public BorderPane mainPane;
    @FXML public TextField phoneNumberUpdate;
    @FXML public TextField countryUpdate;
    @FXML public TextField cityUpdate;
    @FXML public TextField districtUpdate;
    @FXML public TextField postCodeUpdate;
    @FXML public CheckBox statusUpdate;

    public EditPatient(Object prevController, PatientData patient){
        this.prevController = prevController;
        this.patient = patient;
    }

    @FXML
    public void initialize(){
        phoneNumberUpdate.setText(patient.getPhone());
        countryUpdate.setText(patient.getCountry());
        cityUpdate.setText(patient.getCity());
        districtUpdate.setText(patient.getDistrict());
        postCodeUpdate.setText(patient.getPostCode());
        statusUpdate.setSelected(patient.getStatus());
    }

    @FXML
    public void updatePatient(){
        patient.update(phoneNumberUpdate.getText(), countryUpdate.getText(), cityUpdate.getText(), districtUpdate.getText(), postCodeUpdate.getText(), statusUpdate.isSelected());
        returnTo();
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/patient.fxml", prevController, mainPane);
    }
}
