package ubb.scs.map.anar.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.anar.domain.City;
import ubb.scs.map.anar.domain.River;
import ubb.scs.map.anar.service.Service;
import ubb.scs.map.anar.utils.Observer;

public class WarningController implements Observer {
    @FXML
    private TableView<City> warningTable;
    @FXML
    private TableColumn<City, String> cityNameColumn;
    @FXML
    private TableColumn<City, String> riskLevelColumn;
    @FXML
    private Label detailsLabel;

    private ObservableList<City> warningsList = FXCollections.observableArrayList();

    private Service service;
    public void setService(Service service) {
        this.service = service;
        loadCities();
        service.addObserver(this);

    }

    @FXML
    private void initialize() {
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        riskLevelColumn.setCellValueFactory(celData->{
            City city = celData.getValue();
            Double avg = service.findRiverById(city.getRiverId()).getAverageLevel();
            if(city.getCma() > avg)
                return new SimpleStringProperty("High risk of flooding ");
            else if (city.getCmdr() > avg)
                return new SimpleStringProperty("Medium risk of flooding ");
            else{
                return new SimpleStringProperty("Low risk of flooding ");
            }
        });
        warningTable.setItems(warningsList);

        warningTable.setRowFactory(tv -> new TableRow<City>() {
            @Override
            protected void updateItem(City item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else {
                    Double avg = service.findRiverById(item.getRiverId()).getAverageLevel();
                    if (avg < item.getCma()) {
                        setStyle("-fx-background-color: red;");
                    } else if (avg < item.getCmdr()) {
                        setStyle("-fx-background-color: orange;");
                    } else {
                        setStyle("-fx-background-color: green;");
                    }
                }
            }
        });

        warningTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                River river = service.findRiverById(newSelection.getRiverId());
                detailsLabel.setText("River: " + river.getName() + "\nAverage Level: " + river.getAverageLevel() +
                        "\nCmdr: " + newSelection.getCmdr() + "\nCma: " + newSelection.getCma());
            }
        });
    }

    private void loadCities(){
        warningsList.setAll(service.getAllCitites());
    }


    @Override
    public void update() {
        loadCities();
    }
}
