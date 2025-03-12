package ubb.scs.map.faptebune;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ubb.scs.map.faptebune.controllers.RegistrationController;
import ubb.scs.map.faptebune.repository.NeedRepository;
import ubb.scs.map.faptebune.repository.PersonRepository;
import ubb.scs.map.faptebune.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            final String url = "jdbc:postgresql://localhost:5432/FapteBune";
            final String username = "postgres";
            final String password = "postgres";

            PersonRepository personRepository = new PersonRepository(url, username, password);
            NeedRepository needRepository = new NeedRepository(url, username, password);

            Service service = new Service(needRepository, personRepository);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/registrationView.fxml"));
            GridPane logInLayout = loader.load();
            stage.setScene(new Scene(logInLayout));

            RegistrationController controller = loader.getController();
            controller.setService(service);
            stage.show();

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}