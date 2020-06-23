package sample;

import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import fhir.MainResourceGetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainResourceGetter h = new MainResourceGetter();
        try {
            h.run();
        } catch (FhirClientConnectionException ignored){
            System.out.println("Serwer fhir jest nie odpalony, albo brak połączenia!");
            System.exit(0);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sample.fxml"));
        loader.setController(new Controller(h));
        Parent root = loader.load();
        primaryStage.setTitle("Karta Pacjenta");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setScene(new Scene(root, screenBounds.getWidth()*0.75, screenBounds.getHeight()*0.75));
        primaryStage.show();
    }

    public static void changeScene(String fxml, Object controller, BorderPane mainPane){
        Stage stage = (Stage) mainPane.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            loader.setController(controller);
            Parent root = loader.load();
            double width = mainPane.getWidth();
            double height = mainPane.getHeight();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
