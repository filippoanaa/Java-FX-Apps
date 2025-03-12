package ubb.scs.map.faptebune.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ubb.scs.map.faptebune.domain.City;
import ubb.scs.map.faptebune.domain.Person;
import ubb.scs.map.faptebune.service.Service;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordFieldConfirm;


    @FXML
    private ComboBox<City> cityComboBox;

    @FXML
    private TextField streetField;

    @FXML
    private TextField streetNumberField;

    @FXML
    private TextField numberField;

    @FXML
    private Button registerButton;

    @FXML
    private ListView<Person> personsList = new ListView<>();
    private ObservableList<Person> personsObservableList = FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        cityComboBox.getItems().setAll(City.values());
        loadPersons();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personsList.setItems(personsObservableList);

        personsList.setCellFactory(person -> new ListCell<Person>() {
            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                if (empty || person == null) {
                    setText(null);
                }
                else {
                    setText(person.getUsername());
                }
            }
        });

        personsList.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                try {
                    handleViewProfile(new ActionEvent());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void handleViewProfile(ActionEvent actionEvent) throws IOException {
        Person person = personsList.getSelectionModel().getSelectedItem();
        System.out.println(person);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/personView.fxml"));
        Stage stage = new Stage();
        TabPane appLayout = fxmlLoader.load();
        Scene scene = new Scene(appLayout);
        stage.setScene(scene);

        PersonController personController = fxmlLoader.getController();
        personController.setService(service, person);
        stage.setTitle("Profile");
        stage.show();

    }

    public void handleRegister() throws IOException {
        if(!passwordField.getText().equals(passwordFieldConfirm.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match");
            alert.showAndWait();
            return;
        }else{
            Person person = new Person(firstNameField.getText(), lastNameField.getText(), usernameField.getText(),passwordField.getText(), numberField.getText(), streetField.getText(), streetNumberField.getText(),cityComboBox.getValue(), null);
            service.addPerson(person);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/personView.fxml"));
            Stage stage = new Stage();
            TabPane appLayout = loader.load();
            Scene scene = new Scene(appLayout, 650, 500);
            stage.setTitle("Account Settings");
            stage.setScene(scene);

            PersonController personController = loader.getController();
            personController.setService(service, person);
            stage.show();

        }

    }

    private void loadPersons() {
        personsObservableList.setAll(service.getAllPersons());
    }



}
