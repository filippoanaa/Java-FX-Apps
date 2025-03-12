package ubb.scs.map.zboruri;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ubb.scs.map.zboruri.controller.LogInController;
import ubb.scs.map.zboruri.domain.Ticket;
import ubb.scs.map.zboruri.repository.ClientRepo;
import ubb.scs.map.zboruri.repository.FlightRepo;
import ubb.scs.map.zboruri.repository.TicketRepo;
import ubb.scs.map.zboruri.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final String url = "jdbc:postgresql://localhost:5432/Zboruri";
        final String username = "postgres";
        final String password = "postgres";

        ClientRepo clientRepo=new ClientRepo(url, username, password);
        FlightRepo flightRepo=new FlightRepo(url, username, password);
        TicketRepo ticketRepo=new TicketRepo(url, username, password);

        Service service=new Service(clientRepo, flightRepo, ticketRepo);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
        GridPane logInLayout = loader.load();
        stage.setScene(new Scene(logInLayout));

        LogInController controller = loader.getController();
        controller.setService(service);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}