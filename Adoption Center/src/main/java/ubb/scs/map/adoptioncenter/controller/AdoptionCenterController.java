package ubb.scs.map.adoptioncenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ubb.scs.map.adoptioncenter.domain.AdoptionCenter;
import ubb.scs.map.adoptioncenter.domain.Animal;
import ubb.scs.map.adoptioncenter.service.Service;
import ubb.scs.map.adoptioncenter.utils.Observer;

import java.util.List;

public class AdoptionCenterController  implements Observer {
    @FXML
    private TableView<Animal> adoptionCenterTable;
    @FXML
    private TableColumn<Animal, String> nameColumn;
    @FXML
    private TableColumn<Animal, Integer> adoptionCenterIdColumn;
    @FXML
    private TableColumn<Animal.AnimalType, Animal.AnimalType> animalTypeColumn;
    @FXML
    private TableColumn<Animal, Void> actionColumn;

    @FXML
    private Label occupancy;
    @FXML
    private Label locationLabel;
    @FXML
    private ComboBox<String> animalTypeComboBox;

    ObservableList<Animal> animals = FXCollections.observableArrayList();
    ObservableList<String> animalTypes = FXCollections.observableArrayList();

    private Service service;
    private AdoptionCenter adoptionCenter;
    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadAnimals();
        loadComboBox();
    }
    public void setAdoptionCenter(AdoptionCenter adoptionCenter) {
        this.adoptionCenter = adoptionCenter;
    }
    public AdoptionCenter getAdoptionCenter() {
        return adoptionCenter;
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        adoptionCenterIdColumn.setCellValueFactory(new PropertyValueFactory<>("centerId"));
        animalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        adoptionCenterTable.setItems(animals);
        animalTypeComboBox.setItems(animalTypes);

        addButtonToTable();
    }

    private void loadAnimals(){
        List<Animal> animalsList = service.getAnimalsByCenterId(adoptionCenter.getId());
        animals.clear();
        animals.addAll(animalsList);

        int occ = (animalsList.size() * 100) / adoptionCenter.getCapacity();
        occupancy.setText(occ + "%");
        locationLabel.setText(adoptionCenter.getLocation());
    }

    private void loadComboBox(){
        for(Animal.AnimalType type : Animal.AnimalType.values()){
            animalTypeComboBox.getItems().add(type.toString());
        }
        animalTypeComboBox.getItems().add("ALL TYPES");
    }

    @FXML
    private void handleFilter(){
        String animalType = animalTypeComboBox.getSelectionModel().getSelectedItem();
        if(animalType == null){
            animals.setAll(service.getAnimalsByCenterId(adoptionCenter.getId()));
        }else if(animalType.equals("ALL TYPES")){
            animals.setAll(service.getAnimalsByCenterId(adoptionCenter.getId()));
        }
        else {
            animals.setAll(service.filterAnimals(adoptionCenter.getId(), Animal.AnimalType.valueOf(animalType)));
        }
    }

    private void addButtonToTable() {
        Callback<TableColumn<Animal, Void>, TableCell<Animal, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Animal, Void> call(final TableColumn<Animal, Void> param) {
                final TableCell<Animal, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Request Transfer");

                    {
                        btn.setOnAction((event) -> {
                            Animal animal = getTableView().getItems().get(getIndex());
                            requestTransfer(animal);
                            System.out.println("Transfer requested for animal: " + animal.getName());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void requestTransfer(Animal animal) {
        List<AdoptionCenter> centersInSameLocation = service.getCentersInSameLocation(adoptionCenter.getLocation());

        for (AdoptionCenter center : centersInSameLocation) {
            if (!center.getId().equals(adoptionCenter.getId())) {
                notifyCenter(center, animal);
            }
        }
    }

    private void notifyCenter(AdoptionCenter center, Animal animal) {
        for (Observer observer : service.getObservers()) {
            AdoptionCenterController controller = (AdoptionCenterController) observer;
            if (controller.getAdoptionCenter().getId().equals(center.getId())) {
                String message = "New transfer requested. Anima: " + animal.getName() + ". From: " + adoptionCenter.getName();
                controller.addNotification(message, animal, controller.getAdoptionCenter());
            }
        }
    }


    public void addNotification(String message, Animal animal, AdoptionCenter newCenter) {
        ButtonType acceptButton = new ButtonType("Accept", ButtonBar.ButtonData.YES);
        ButtonType ignoreButton = new ButtonType("Ignore", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, acceptButton, ignoreButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == acceptButton) {
                animal.setCenterId(newCenter.getId());
                service.updateAnimal(animal);
            } else {
                alert.close();
            }
        });
    }

    @Override
    public void update() {
        loadAnimals();
    }
}