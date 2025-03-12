package ubb.scs.map.anar.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.anar.domain.River;
import ubb.scs.map.anar.service.Service;
import ubb.scs.map.anar.utils.Observer;


public class RiversController implements Observer {
    @FXML
    private TableView<River> riverTable;
    @FXML
    private TableColumn<River, String> riverNameColumn;
    @FXML
    private TableColumn<River, Double> riverLevelColumn;
    @FXML
    private TextField averageLevel;
    @FXML
    private Button updateButton;

    private ObservableList<River> riverObservableList = FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadRivers();
    }

    @FXML
    private void initialize() {
        riverNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        riverLevelColumn.setCellValueFactory(new PropertyValueFactory<>("averageLevel"));
        riverTable.setItems(riverObservableList);
    }

    private void loadRivers() {
        riverObservableList.setAll(service.getAllRivers());
    }


    @Override
    public void update() {
        loadRivers();
    }

    @FXML
    private void handleUpdateRiver() {
        River selectedRiver = riverTable.getSelectionModel().getSelectedItem();
        String averageLevelText = averageLevel.getText();
        if(selectedRiver == null) {
            Alerts.showErrorAlert("Select a river to update");
            return;
        }
        if(averageLevelText.isEmpty()) {
            Alerts.showErrorAlert("Please enter average level");
        }
        selectedRiver.setAverageLevel(Double.parseDouble(averageLevelText));
        service.updateRiver(selectedRiver);
        Alerts.showConfirmationAlert("Average level updated to " + selectedRiver.getAverageLevel());
        averageLevel.clear();


    }
}
