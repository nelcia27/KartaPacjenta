package sample;

import fhir.ObservationData;
import fhir.PatientData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient {
    private final Object prevController;
    private final PatientData patientInfo;
    private final SimpleDateFormat mainFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @FXML public BorderPane mainPane;
    @FXML public Label patientName;
    @FXML public Label patientGender;
    @FXML public Label patientBirthDate;
    @FXML public Label patientAddress;
    @FXML public Label patientPhoneNumber;
    @FXML public VBox patientHistory;
    @FXML public ComboBox<String> filtrComboBox;

    public Patient(Object prevController, PatientData patientInfo) {
        this.prevController = prevController;
        this.patientInfo = patientInfo;
    }

    @FXML public void initialize(){
        patientName.setText(patientInfo.getName());
        patientGender.setText(patientInfo.getSex());
        patientBirthDate.setText(mainFormat.format(patientInfo.getDateBirth()));
        patientAddress.setText(patientInfo.getAddress());
        patientPhoneNumber.setText(patientInfo.getPhone());

        Date currentDate = patientInfo.getObservationData().get(0).getAdataTime();
        createLabel(mainFormat.format(currentDate));
        for(ObservationData observationData : patientInfo.getObservationData()){
            if(!mainFormat.format(currentDate).equals(mainFormat.format(observationData.getAdataTime()))){
                createLabel(mainFormat.format(observationData.getAdataTime()));
                currentDate = observationData.getAdataTime();
            }
            createButton(observationData);
        }

        setComboOption("Styczeń 2019"); // TODO
        setComboOption("Luty 2019");
        setComboOption("Marzec 2019");
        setComboOption("Styczeń 2020");
    }

    private void createButton(ObservationData observationData){    // TODO: maybe polimorfism?
        Button current = new Button();
        HBox buttonLayout = new HBox();
        Label time = new Label(timeFormat.format(observationData.getAdataTime()));
        VBox buttonName = new VBox();
        Label properName = new Label(observationData.getInfo());
        Label generalName = new Label("Obserwacja");   //TODO: name (general)
        generalName.getStyleClass().add("generalName");
        buttonName.getChildren().addAll(properName, generalName);
        buttonLayout.getChildren().addAll(time, buttonName);
        current.setGraphic(buttonLayout);
        current.getStyleClass().add("historyButton");
        current.setOnAction(event -> gotoDetails());    // TODO: set which details
        patientHistory.getChildren().add(current);
    }

    private void createLabel(String date){
        Label dateLabel = new Label(date);
        dateLabel.getStyleClass().add("mainDate");
        patientHistory.getChildren().add(dateLabel);
    }

    private void gotoDetails(){
        Main.changeScene("/fxml/details.fxml", new Details(this), mainPane);
    }

    private void setComboOption(String time){  // TODO: Real time
        filtrComboBox.getItems().addAll(time);
    }

    @FXML
    public void selectedCombo(){    // TODO: change view when combo chosen
        System.out.println(filtrComboBox.getValue());
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/sample.fxml", prevController, mainPane);
    }
}
