package ubb.scs.map.oferte;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ubb.scs.map.oferte.controller.ClientController;
import ubb.scs.map.oferte.controller.MainController;
import ubb.scs.map.oferte.domain.Client;
import ubb.scs.map.oferte.domain.HotelType;
import ubb.scs.map.oferte.repository.*;
import ubb.scs.map.oferte.service.Service;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final String url = "jdbc:postgresql://localhost:5432/Oferte";
        final String username = "postgres";
        final String password = "postgres";

        ClientRepository clientRepository = new ClientRepository(url, username, password);
        SpecialOfferRepository specialOfferRepository = new SpecialOfferRepository(url, username, password);
        ReservationRepository resRepository = new ReservationRepository(url, username, password);
        LocationRepository locationRepository = new LocationRepository(url, username, password);
        HotelRepository hotelRepository = new HotelRepository(url, username, password);

        Service service = new Service(clientRepository, hotelRepository, locationRepository, resRepository, specialOfferRepository);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        AnchorPane root = loader.load();

        MainController controller = loader.getController();
        controller.setService(service);

        stage.setScene(new Scene(root));
        stage.setTitle("Hotel Management");
        stage.show();

        List<String> params = getParameters().getRaw();
        for (String param : params) {
            try {
                Long id = Long.parseLong(param);
                 Client client = service.getClientById(id);

                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(HelloApplication.class.getResource("/clientView.fxml"));
                 AnchorPane pane = fxmlLoader.load();
                 Scene scene = new Scene(pane);

                 Stage clientWindow = new Stage();
                clientWindow.setScene(scene);
                clientWindow.setTitle(client.getName());

                 ClientController clientController = fxmlLoader.getController();
                clientController.setClient(client);

                clientController.setService(service);
                clientWindow.show();
            } catch (NumberFormatException e) {
                System.err.println("Invalid ID format: " + param);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}