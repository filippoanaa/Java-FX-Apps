package ubb.scs.map.oferte.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.oferte.domain.Client;
import ubb.scs.map.oferte.domain.Hotel;
import ubb.scs.map.oferte.domain.SpecialOffer;
import ubb.scs.map.oferte.service.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ClientController {
    @FXML
    private TableView<SpecialOffer> offersTable;
    @FXML
    private TableColumn<SpecialOffer, String> hotelNameColumn;
    @FXML
    private TableColumn<SpecialOffer, String> locationColumn;
    @FXML
    private TableColumn<SpecialOffer, LocalDate> startDateColumn;
    @FXML
    private TableColumn<SpecialOffer, LocalDate> endDateColumn;

    private ObservableList<SpecialOffer> offers = FXCollections.observableArrayList();
    private Client client;
    private Service service;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setService(Service service) {
        this.service = service;
        loadOffers();
    }

    @FXML
    private void initialize() {
        hotelNameColumn.setCellValueFactory(cellData ->{
            SpecialOffer offer = cellData.getValue();
            String hotelName = service.getHotelById(offer.getHotelId()).getName();
            return new SimpleStringProperty(hotelName);
        });
        locationColumn.setCellValueFactory(cellData->{
            SpecialOffer offer = cellData.getValue();
            Hotel hotel = service.getHotelById(offer.getHotelId());
            String location = service.getLocationById(hotel.getLocationId()).getLocationName();
            return new SimpleStringProperty(location);

        });
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        offersTable.setItems(offers);

    }

    private void loadOffers(){
        List<SpecialOffer> specialOfferList = service.getSpecialOffersForClient(client);
        offers.setAll(specialOfferList);
    }





}
