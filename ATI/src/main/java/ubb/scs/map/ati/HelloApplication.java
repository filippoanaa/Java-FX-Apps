package ubb.scs.map.ati;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.ati.controller.BedController;
import ubb.scs.map.ati.controller.PatientController;
import ubb.scs.map.ati.repository.BedRepository;
import ubb.scs.map.ati.repository.PatientRepository;
import ubb.scs.map.ati.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final String url = "jdbc:postgresql://localhost:5432/ATI";
        final String username = "postgres";
        final String password = "postgres";

        PatientRepository pr = new PatientRepository(url, username, password);
        BedRepository bre = new BedRepository(url, username, password);

        Service service = new Service(bre, pr);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bedView.fxml"));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));

        BedController controller = fxmlLoader.getController();
        controller.setService(service);
        stage.setTitle("Beds");
        stage.show();

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/patientView.fxml"));
        Parent root2 = fxmlLoader2.load();
        Scene scene2 = new Scene(root2);
        Stage stage2 = new Stage();

        PatientController patientController = fxmlLoader2.getController();
        patientController.setService(service);
        stage2.setScene(scene2);
        stage2.setTitle("Waiting patients");
        stage2.show();





    }

    public static void main(String[] args) {
        launch();
    }
}