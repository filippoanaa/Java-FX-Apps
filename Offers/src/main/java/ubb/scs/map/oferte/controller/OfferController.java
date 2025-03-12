package ubb.scs.map.oferte.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import ubb.scs.map.oferte.domain.Hotel;
import ubb.scs.map.oferte.domain.Reservation;
import ubb.scs.map.oferte.domain.SpecialOffer;
import ubb.scs.map.oferte.service.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class OfferController {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ListView<SpecialOffer> specialOfferList;
    @FXML
    private Button searchButton;

    private ObservableList<SpecialOffer> offersList = FXCollections.observableArrayList();
    private Service service;
    private Hotel hotel;

    public void setService(Service service) {
        this.service = service;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @FXML
    public void initialize() {
        specialOfferList.setItems(offersList);
    }

    @FXML
    private void handleSearch() {
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        System.out.println("Start Date: " + start);
        System.out.println("End Date: " + end);
        System.out.println("Hotel ID: " + hotel.getId());

        offersList.clear();
        List<SpecialOffer> results = service.searchOffers(hotel.getId(), start, end);
        System.out.println("Number of offers found: " + results.size());
        offersList.addAll(results);
    }

    @FXML
    private void handleAddReservation() {
        SpecialOffer specialOffer = specialOfferList.getSelectionModel().getSelectedItem();
        if (specialOffer == null) {
            System.out.println("No special offer selected!");
            return;
        }

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        if (start == null || end == null) {
            System.out.println("Please select start and end dates!");
            return;
        }

        long noNights = ChronoUnit.DAYS.between(start, end);

        Reservation reservation = new Reservation(1L, specialOffer.getHotelId(), LocalDateTime.now(), (int) noNights);
        service.addReservation(reservation);
        /*
            Eu practic am facut doar sa rezervi oferta, trb sa fac sa se poata orice
            si nu e ok asa cu click

         */

        System.out.println("Reservation added: " + reservation);
    }
}