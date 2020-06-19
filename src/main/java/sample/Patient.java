package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class Patient {
    private final Object prevController;
    @FXML public BorderPane mainPane;

    public Patient(Object controller){
        prevController = controller;
    }

    @FXML public void initialize(){

    }

    @FXML
    public void returnTo(){
        Main.changeScene("/fxml/sample.fxml", prevController, mainPane);
    }
}
