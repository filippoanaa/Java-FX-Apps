package ubb.scs.map.zboruri.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ubb.scs.map.zboruri.domain.Client;
import ubb.scs.map.zboruri.service.Service;

import java.io.IOException;

public class LogInController {
    @FXML
    private TextField usernameText;
    @FXML
    private Button loginButton;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameText.getText();
        Client client = service.findClientByUsername(username);

        if(client != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/flightsView.fxml"));
            VBox logInLayout = loader.load();
            Stage stage = new Stage();
            stage.setTitle(client.getName());
            stage.setScene(new Scene(logInLayout));

            FlightsController controller = loader.getController();
            controller.setService(service);
            controller.setClient(client);
            stage.show();
            usernameText.clear();
        }else{
            Alerts.showConfirmationAlert("No client found with this username!");
            return;
        }
    }
}
