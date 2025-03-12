package ubb.scs.map.oferte.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ubb.scs.map.oferte.domain.Hotel;
import ubb.scs.map.oferte.domain.HotelType;
import ubb.scs.map.oferte.domain.Location;
import ubb.scs.map.oferte.service.Service;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML
    private ComboBox<String> locationComboBox;
    @FXML
    private TableView<Hotel> hotelTableView;
    @FXML
    private TableColumn<Hotel, String> hotelNameColumn;
    @FXML
    private TableColumn<Hotel, HotelType> typeColumn;
    @FXML
    private TableColumn<Hotel, Double> priceColumn;
    @FXML
    private TableColumn<Hotel, Integer> noRoomsColumn;

    ObservableList<Hotel> hotelList= FXCollections.observableArrayList();
    ObservableList<String> locationsList= FXCollections.observableArrayList();

    private Service service;

    public void setService(Service service) {
        this.service = service;
        loadLocations();
    }

    @FXML
    private void initialize() {
        locationComboBox.setItems(locationsList);
        hotelTableView.setItems(hotelList);

        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        noRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("noRooms"));

    }

    private void loadLocations(){
        for(Location l : service.getAllLocations())
            locationsList.add(l.getLocationName());
    }


    @FXML
    private void handleComboBox(){
        if(locationComboBox.getSelectionModel().getSelectedItem() == null)
            return;
        String location = locationComboBox.getSelectionModel().getSelectedItem();
        Double id = service.getLocationIdByName(location);

        hotelList.clear();
        for(Hotel h : service.getAllHotels()){
            if(Objects.equals(h.getLocationId(), id))
                hotelList.add(h);
        }
    }

    @FXML
    private void handleTableView() throws IOException {
        if(hotelTableView.getSelectionModel().getSelectedItem() == null)
            return;

        Hotel hotel = hotelTableView.getSelectionModel().getSelectedItem();

        FXMLLoader offerLoader = new FXMLLoader(getClass().getResource("/offerView.fxml"));
        AnchorPane pane = offerLoader.load();
        Scene scene = new Scene(pane);

        Stage stage = new Stage();
        stage.setScene(scene);

        OfferController offerController = offerLoader.getController();
        offerController.setService(service);
        offerController.setHotel(hotel);
        stage.setTitle("Offers");
        stage.show();







    }



}
