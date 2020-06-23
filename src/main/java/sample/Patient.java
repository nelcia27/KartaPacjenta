package sample;

import fhir.MainResourceGetter;
import fhir.PatientData;
import fhir.PatientDetailData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Patient {
    private final Object prevController;
    private final PatientData patientInfo;
    private final SimpleDateFormat mainFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat months = new SimpleDateFormat("MM-yyyy");
    private final ArrayList<PatientDetailData> patientDetailData = new ArrayList<>();

    @FXML public BorderPane mainPane;
    @FXML public Label patientName;
    @FXML public Label patientGender;
    @FXML public Label patientBirthDate;
    @FXML public Label patientAddress;
    @FXML public Label patientPhoneNumber;
    @FXML public Label patientStatus;
    @FXML public VBox patientHistory;
    @FXML public ComboBox<Date> filtrComboBox;

    public Patient(Object prevController, PatientData patientInfo) {
        this.prevController = prevController;
        this.patientInfo = patientInfo;
        patientDetailData.addAll(patientInfo.getObservationData());
        patientDetailData.addAll(patientInfo.getMedicationData());
        Collections.sort(patientDetailData);
    }

    @FXML public void initialize(){
        patientName.setText(patientInfo.getName());
        patientGender.setText(patientInfo.getSex());
        patientBirthDate.setText(mainFormat.format(patientInfo.getDateBirth()));
        patientAddress.setText(patientInfo.getAddress());
        patientPhoneNumber.setText(patientInfo.getPhone());
        if(patientInfo.getStatus())
            patientStatus.setText("Tak");
        else
            patientStatus.setText("Nie");

        showPatientHistory(null);
        setComboOption();
    }

    private void showPatientHistory(Date monthYear) {
        patientHistory.getChildren().clear();
        Date currentDate = patientDetailData.get(0).getAdataTime();
        if(monthYear == null || months.format(currentDate).equals(months.format(monthYear)))
            createLabel(mainFormat.format(currentDate));
        for(PatientDetailData observationData : patientDetailData){
            if(!mainFormat.format(currentDate).equals(mainFormat.format(observationData.getAdataTime()))){
                currentDate = observationData.getAdataTime();
                if(monthYear == null || months.format(currentDate).equals(months.format(monthYear)))
                    createLabel(mainFormat.format(currentDate));
            }
            if(monthYear == null || months.format(currentDate).equals(months.format(monthYear)))
                createButton(observationData);
        }
    }

    private void createButton(PatientDetailData patientHistoryData){
        Button current = new Button();
        HBox buttonLayout = new HBox();
        Label time = new Label(timeFormat.format(patientHistoryData.getAdataTime()));
        VBox buttonName = new VBox();
        Label properName = new Label(patientHistoryData.getInfo());
        Label generalName = new Label(patientHistoryData.getDescription());
        generalName.getStyleClass().add("generalName");
        buttonName.getChildren().addAll(properName, generalName);
        buttonLayout.getChildren().addAll(time, buttonName);
        current.setGraphic(buttonLayout);
        current.getStyleClass().add("historyButton");
        current.setOnAction(event -> gotoDetails(patientHistoryData));
        patientHistory.getChildren().add(current);
    }

    private void createLabel(String date){
        Label dateLabel = new Label(date);
        dateLabel.getStyleClass().add("mainDate");
        patientHistory.getChildren().add(dateLabel);
    }

    private void gotoDetails(PatientDetailData observationData){
        Main.changeScene("/fxml/details.fxml", new Details(this, observationData), mainPane);
    }

    private void setComboOption(){
        Callback<ListView<Date>, ListCell<Date>> cellFactory = new Callback<ListView<Date>, ListCell<Date>>() {

            @Override
            public ListCell<Date> call(ListView<Date> l) {
                return new ListCell<Date>() {

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(months.format(item));
                        }
                    }
                } ;
            }
        };

// Just set the button cell here:
        filtrComboBox.setButtonCell(cellFactory.call(null));
        filtrComboBox.setCellFactory(cellFactory);
        filtrComboBox.getItems().add(null);
        Date currentDate = patientDetailData.get(0).getAdataTime();
        filtrComboBox.getItems().add(currentDate);
        for(PatientDetailData patientDetail : patientDetailData){
            if(!months.format(currentDate).equals(months.format(patientDetail.getAdataTime()))){
                currentDate = patientDetail.getAdataTime();
                filtrComboBox.getItems().add(currentDate);
            }
        }
    }

    @FXML
    public void editPatient(){
        Main.changeScene("/fxml/editPatient.fxml", new EditPatient(this, patientInfo), mainPane);
    }

    @FXML
    public void weightPlot(){
        Main.changeScene("/fxml/plots.fxml", new Plots(this, MainResourceGetter.getDataWeight(patientInfo)), mainPane);
    }

    @FXML
    public void heightPlot(){
        Main.changeScene("/fxml/plots.fxml", new Plots(this, MainResourceGetter.getDataHeight(patientInfo)), mainPane);
    }

    @FXML
    public void selectedCombo(){    // TODO: change view when combo chosen
        showPatientHistory(filtrComboBox.getValue());
    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/sample.fxml", prevController, mainPane);
    }
}
