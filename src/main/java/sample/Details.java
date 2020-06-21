package sample;

import fhir.PatientData;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class Details {
    private final Object prevController;

    @FXML public BorderPane mainPane;

    public Details(Object prevController) {     // TODO: add details info
        this.prevController = prevController;
    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/patient.fxml", prevController, mainPane);
    }
}
