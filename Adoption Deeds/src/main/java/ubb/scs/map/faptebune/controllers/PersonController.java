package ubb.scs.map.faptebune.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.faptebune.domain.City;
import ubb.scs.map.faptebune.domain.Need;
import ubb.scs.map.faptebune.domain.Person;
import ubb.scs.map.faptebune.service.Service;
import ubb.scs.map.faptebune.utils.Observer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PersonController implements Observer {
    @FXML
    private TableColumn<Need, String> needStatusColumn;
    @FXML
    private TableView<Need> needsTable;
    @FXML
    private TableColumn<Need, String> needTitleColumn;
    @FXML
    private TableColumn<Need, String> needDescriptionColumn;
    @FXML
    private TableColumn<Need, LocalDateTime> needDeadlineColumn;
    @FXML
    private TableView<Need> goodDeedsTable;
    @FXML
    private TableColumn<Need, String> deedTitleColumn;
    @FXML
    private TableColumn<Need, String> deedDescriptionColumn;
    @FXML
    private TableColumn<Need, LocalDateTime> deedDeadlineColumn;
    @FXML
    private TableColumn<Need, String > deedStatusColumn;


    private Service service;
    private Person loggedInPerson;

    private ObservableList<Need> needsObservableList = FXCollections.observableArrayList();
    private ObservableList<Need> goodDeedsObservableList = FXCollections.observableArrayList();

    public void setService(Service service, Person person) {
        this.service = service;
        service.addObserver(this);
        this.loggedInPerson = person;
        loadNeeds();
        loadGoodDeeds();
    }

    @FXML
    public void initialize() {
        needTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        needDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        needDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        needStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        deedTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        deedDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        deedDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        deedStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        needsTable.setItems(needsObservableList);
        goodDeedsTable.setItems(goodDeedsObservableList);

        needsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try{
                    handleResolveNeed();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadNeeds() {
        List<Need> needs = service.getAllNeeds().stream()
                .filter(need -> {
                    Long manInNeedId = need.getManInNeed();
                    City city = service.getCityById(manInNeedId);
                    return !Objects.equals(manInNeedId, loggedInPerson.getId()) && city != null && city.equals(loggedInPerson.getCity());
                })
                .collect(Collectors.toList());
        needsObservableList.setAll(needs);
    }

    private void loadGoodDeeds() {
        List<Need> goodDeeds = service.getAllNeeds().stream()
                .filter(need -> need.getSavior() != null && need.getSavior().equals(loggedInPerson.getId()))
                .collect(Collectors.toList());
        goodDeedsObservableList.setAll(goodDeeds);
    }

    @FXML
    private void handleResolveNeed() {
        Need selectedNeed = needsTable.getSelectionModel().getSelectedItem();
        if(selectedNeed.getStatus().equals("Erou gasit!") ){
            showErrorAlert("Erou gasit deja");
            return;
        }
        if (selectedNeed != null ) {
            selectedNeed.setSavior(loggedInPerson.getId());
            selectedNeed.setStatus("Erou gasit!");
            service.updateNeed(selectedNeed);
            loadNeeds();
            loadGoodDeeds();
            showConfirmationAlert("Nevoia a fost atribuită cu succes!");
        } else {
            showErrorAlert("Selectați o nevoie pentru a continua.");
        }
    }

    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private DatePicker deadlinePicker;

    public void handleAddNeed(ActionEvent actionEvent) {
        String title = titleField.getText();
        String description =  descriptionField.getText();
        LocalDateTime date = LocalDateTime.of(deadlinePicker.getValue(), LocalTime.of(23,59));

        Need need =  new Need(title, description, date, loggedInPerson.getId());
        System.out.println(loggedInPerson.getId());
        service.addNeed(need);
        needsObservableList.add(need);
        loadNeeds();

        showConfirmationAlert("Need added successfully");


    }

    @Override
    public void update() {
        loadNeeds();
        loadGoodDeeds();
    }
}
