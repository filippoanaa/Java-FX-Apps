package ubb.scs.map.anar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.anar.controller.RiversController;
import ubb.scs.map.anar.controller.WarningController;
import ubb.scs.map.anar.repository.CityRepository;
import ubb.scs.map.anar.repository.Repository;
import ubb.scs.map.anar.repository.RiverRepository;
import ubb.scs.map.anar.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final String url = "jdbc:postgresql://localhost:5432/ANAR";
        final String username = "postgres";
        final String password = "postgres";


        RiverRepository riverRepository = new RiverRepository(url, username, password);
        CityRepository cityRepository = new CityRepository(url, username, password);
        Service service = new Service(riverRepository, cityRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/riversView.fxml"));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Rivers");

        RiversController riversController = fxmlLoader.getController();
        riversController.setService(service);
        stage.show();

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/warningsView.fxml"));
        Parent root2 = fxmlLoader2.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root2));
        stage2.setTitle("Warnings of floating");

        WarningController warningController = fxmlLoader2.getController();
        warningController.setService(service);
        stage2.show();
    }

    public static void main(String[] args) {
        launch();
    }
}