package ubb.scs.map.ati.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import ubb.scs.map.ati.domain.Bed;
import ubb.scs.map.ati.domain.Patient;
import ubb.scs.map.ati.service.Service;
import ubb.scs.map.ati.utils.Observer;

import java.util.List;
import java.util.stream.Collectors;

public class BedController implements Observer {
    @FXML
    TableView<Bed> bedTable;
    @FXML
    TableColumn<Bed, Bed.BedType> bedTypeColumn;
    @FXML
    TableColumn<Bed, Bed.Ventilation> ventilationColumn;
    @FXML
    TableColumn<Bed, String> personColumn;
    @FXML
    Label freeBedsLabel;
    @FXML
    Label tiipBedsLabel;
    @FXML
    Label timBedsLabel;
    @FXML
    Label ticBedsLabel;
    @FXML
    TextField personCnp;
    @FXML
    Button freeBedButton;

    ObservableList<Bed> bedsList = FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadBeds();
    }

    @FXML
    public void initialize() {
        bedTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bedType"));
        ventilationColumn.setCellValueFactory(new PropertyValueFactory<>("ventilation"));
        personColumn.setCellValueFactory(new PropertyValueFactory<>("personCnp"));
        bedTable.setItems(bedsList);
    }

    private void loadBeds() {
        List<Bed> beds = service.getAllBeds();
        List<Bed> occupiedBeds = beds.stream().filter(bed -> bed.getPersonCnp() != null).collect(Collectors.toList());
        bedsList.setAll(occupiedBeds);

        long totalBeds = beds.size();
        long tiipBedsFree = beds.stream().filter(bed -> bed.getBedType() == Bed.BedType.TIIP && bed.getPersonCnp() == null).count();
        long timBedsFree = beds.stream().filter(bed -> bed.getBedType() == Bed.BedType.TIM && bed.getPersonCnp() == null).count();
        long ticBedsFree = beds.stream().filter(bed -> bed.getBedType() == Bed.BedType.TIC && bed.getPersonCnp() == null).count();

        updateLabel(tiipBedsLabel, "TIIP", tiipBedsFree);
        updateLabel(timBedsLabel, "TIM", timBedsFree);
        updateLabel(ticBedsLabel, "TIC", ticBedsFree);

        freeBedsLabel.setText(String.valueOf(totalBeds - occupiedBeds.size()));
    }

    private void updateLabel(Label label, String bedType, long freeBeds) {
        label.setText(String.format("%s %d paturi libere", bedType, freeBeds));
        if (freeBeds == 0) {
            label.setTextFill(Color.RED);
        } else {
            label.setTextFill(Color.GREEN);
        }
    }

    @FXML
    private void handleDeletePatient() {
        String cnp = personCnp.getText();
        if (cnp.isEmpty()) {
            Alerts.showErrorAlert("Please enter a CNP.");
            return;
        }

        Patient patient = service.getPatientByCnp(cnp);
        if (patient == null) {
            Alerts.showErrorAlert("No patient found with this CNP.");
            return;
        }

        Bed bed = service.getAllBeds().stream()
                .filter(b -> cnp.equals(b.getPersonCnp()))
                .findFirst().orElse(null);

        if (bed == null) {
            Alerts.showErrorAlert("No patient with this CNP is currently occupying a bed.");
            return;
        }

        bed.setPersonCnp(null);
        service.updateBed(bed);
        Alerts.showConfirmationAlert("Bed is now free.");
        loadBeds();
        personCnp.clear();
    }

    @Override
    public void update() {
        loadBeds();
    }
}