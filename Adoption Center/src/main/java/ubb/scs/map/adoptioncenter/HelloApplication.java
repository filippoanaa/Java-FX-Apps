package ubb.scs.map.adoptioncenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ubb.scs.map.adoptioncenter.controller.AdoptionCenterController;
import ubb.scs.map.adoptioncenter.domain.AdoptionCenter;
import ubb.scs.map.adoptioncenter.repository.AdoptionCenterRepository;
import ubb.scs.map.adoptioncenter.repository.AnimalRepository;
import ubb.scs.map.adoptioncenter.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final String url = "jdbc:postgresql://localhost:5432/AdoptionCenter";
        final String username = "postgres";
        final String password = "postgres";

        AdoptionCenterRepository adoptionCenterRepository = new AdoptionCenterRepository(url, username, password);
        AnimalRepository animalRepository = new AnimalRepository(url, username, password);
        Service service = new Service(animalRepository, adoptionCenterRepository);

        for(AdoptionCenter adoptionCenter : service.getAdoptionCenters()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("/adoptionCenterView.fxml"));
            VBox gridPane = fxmlLoader.load();
            Scene scene = new Scene(gridPane);
            Stage centerStage = new Stage();
            centerStage.setTitle(adoptionCenter.getName());
            centerStage.setScene(scene);

            AdoptionCenterController controller = fxmlLoader.getController();
            controller.setAdoptionCenter(adoptionCenter);
            controller.setService(service);
            centerStage.show();
        }



    }

    public static void main(String[] args) {
        launch();
    }
}