package ubb.scs.map.ati.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.ati.domain.Bed;
import ubb.scs.map.ati.domain.Patient;
import ubb.scs.map.ati.service.Service;
import ubb.scs.map.ati.utils.Observer;

import java.util.Comparator;
import java.util.List;

public class PatientController implements Observer {
    @FXML
    TableView<Patient> patientTable;
    @FXML
    TableColumn<Patient, String> cnpColumn;
    @FXML
    TableColumn<Patient, Integer> monthsColumn;
    @FXML
    TableColumn<Patient, Patient.PreBorn> preBornColumn;
    @FXML
    TableColumn<Patient, String> diagnosisColumn;
    @FXML
    TableColumn<Patient, Integer> gravityColumn;
    @FXML
    ComboBox<Bed.BedType> bedTypeComboBox;
    @FXML
    CheckBox ventilationCheckBox;
    @FXML
    Button addPatientButton;

    ObservableList<Patient> patientsList = FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadPatients();
        loadComboBox();
    }

    @FXML
    public void initialize() {
        cnpColumn.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        monthsColumn.setCellValueFactory(new PropertyValueFactory<>("months"));
        preBornColumn.setCellValueFactory(new PropertyValueFactory<>("preBorn"));
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        gravityColumn.setCellValueFactory(new PropertyValueFactory<>("gravity"));
        patientTable.setItems(patientsList);
    }

    private void loadPatients() {
        List<Patient> patients = service.getUnassignedPatients();
        patients.sort(Comparator.comparingInt(Patient::getGravity).reversed());
        patientsList.setAll(patients);
    }

    private void loadComboBox() {
        bedTypeComboBox.setItems(FXCollections.observableArrayList(Bed.BedType.values()));
    }

    @FXML
    private void handleAddPatientToBed() {
        Patient patient = patientTable.getSelectionModel().getSelectedItem();
        Bed.BedType bedType = bedTypeComboBox.getSelectionModel().getSelectedItem();
        boolean requiresVentilation = ventilationCheckBox.isSelected();

        if (patient == null) {
            Alerts.showErrorAlert("Select a patient first");
            return;
        }
        if(bedType == null){
            Alerts.showErrorAlert("Select a bed type first");
            return;
        }

        Bed availableBed = service.getAllBeds().stream()
                .filter(bed -> bed.getPersonCnp() == null && bed.getBedType() == bedType)
                .filter(bed -> !requiresVentilation || bed.getVentilation() == Bed.Ventilation.YES)
                .findFirst().orElse(null);

        if(availableBed == null){
            if (requiresVentilation) {
                Alerts.showErrorAlert("No available bed with ventilation found");
            } else {
                Alerts.showErrorAlert("No available bed found");
            }
            return;
        }

        availableBed.setPersonCnp(patient.getCnp());
        service.updateBed(availableBed);

        loadPatients();
        Alerts.showConfirmationAlert("Patient added");
        patientTable.getSelectionModel().clearSelection();
        bedTypeComboBox.getSelectionModel().clearSelection();
        ventilationCheckBox.setSelected(false);
    }




    @Override
    public void update() {
        loadPatients();
    }
}