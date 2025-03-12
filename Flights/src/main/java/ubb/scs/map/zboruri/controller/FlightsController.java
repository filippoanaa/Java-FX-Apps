package ubb.scs.map.zboruri.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ubb.scs.map.zboruri.domain.Client;
import ubb.scs.map.zboruri.domain.Flight;
import ubb.scs.map.zboruri.service.Service;
import ubb.scs.map.zboruri.utils.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightsController implements Observer {
    @FXML
    private ComboBox<String> fromComboBox;
    @FXML
    private ComboBox<String> toComboBox;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private TableView<Flight> flightTable;
    @FXML
    private TableColumn<Flight, Long> idColumn;
    @FXML
    private TableColumn<Flight, String> fromColumn;
    @FXML
    private TableColumn<Flight, String> toColumn;
    @FXML
    private TableColumn<Flight, LocalDateTime> departureTimeColumn;
    @FXML
    private TableColumn<Flight, LocalDateTime> landingTimeColumn;
    @FXML
    private TableColumn<Flight, Integer> seatsColumn;
    @FXML
    private TableColumn<Flight, Integer> availableSeatsColumn;
    @FXML
    private Button searchButton;
    @FXML
    private Button buyButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label currentPageLabel;
    @FXML
    private Label totalPagesLabel;

    private ObservableList<Flight> flightList = FXCollections.observableArrayList();
    private ObservableList<String> fromList = FXCollections.observableArrayList();
    private ObservableList<String> toList = FXCollections.observableArrayList();
    private Service service;
    private Client client;

    private List<Flight> currentFlights;
    private int currentPage = 1;
    private int totalPages;
    private static final int PAGE_SIZE = 1;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadComboBoxData();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        landingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("landingTime"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        availableSeatsColumn.setCellValueFactory(flight ->
                new SimpleIntegerProperty(service.getAvailableSeats(flight.getValue().getId())).asObject()
        );

        // Restricționează DatePicker pentru a nu permite selectarea datelor mai vechi de azi
        departureDatePicker.setDayCellFactory(getDayCellFactory());

        flightTable.setItems(flightList);
        fromComboBox.setItems(fromList);
        toComboBox.setItems(toList);
        updatePageLabels();
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Dezactivează toate datele anterioare zilei curente
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
    }

    private void loadComboBoxData() {
        List<String> from = service.getFromList();
        fromList.setAll(from);

        List<String> to = service.getToList();
        toList.setAll(to);
    }

    @FXML
    private void handleSearch() {
        String from = fromComboBox.getValue();
        String to = toComboBox.getValue();
        LocalDate departureDate = departureDatePicker.getValue();

        if (from == null || to == null || departureDate == null) {
            Alerts.showErrorAlert("From, to and departure dates are required");
            return;
        }

        currentFlights = service.getAllFlights().stream()
                .filter(flight -> flight.getFrom().equals(from))
                .filter(flight -> flight.getTo().equals(to))
                .filter(flight -> flight.getDepartureTime().toLocalDate().equals(departureDate))
                .collect(Collectors.toList());

        currentPage = 1;
        totalPages = (int) Math.ceil((double) currentFlights.size() / PAGE_SIZE);
        updatePageLabels();
        updateFlightTable();
    }


    @FXML
    private void handleBuyTicket() {
        Flight flight = flightTable.getSelectionModel().getSelectedItem();
        if (flight == null) {
            Alerts.showErrorAlert("No flight selected");
            return;
        }
        if (service.getAvailableSeats(flight.getId()) <= 0) {
            Alerts.showErrorAlert("No available seats for this flight");
            return;
        }

        service.buyTicket(client.getUsername(), flight.getId());
        Alerts.showConfirmationAlert("Ticket bought successfully");

        handleSearch(); // Actualizează lista de zboruri pentru a reflecta noile locuri disponibile
    }

    @Override
    public void update() {
        handleSearch();
    }

    @FXML
    private void handleNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updatePageLabels();
            updateFlightTable();
        }
    }

    @FXML
    private void handlePreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePageLabels();
            updateFlightTable();
        }
    }

    private void updateFlightTable() {
        int fromIndex = (currentPage - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, currentFlights.size());
        flightList.setAll(currentFlights.subList(fromIndex, toIndex));
    }

    private void updatePageLabels() {
        currentPageLabel.setText("Page " + currentPage);
        totalPagesLabel.setText("Total Pages " + totalPages);
        previousButton.setDisable(currentPage == 1);
        nextButton.setDisable(currentPage == totalPages);
    }
}