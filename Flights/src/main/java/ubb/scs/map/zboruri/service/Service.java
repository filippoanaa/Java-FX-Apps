package ubb.scs.map.zboruri.service;

import ubb.scs.map.zboruri.domain.Client;
import ubb.scs.map.zboruri.domain.Flight;
import ubb.scs.map.zboruri.domain.Ticket;
import ubb.scs.map.zboruri.repository.ClientRepo;
import ubb.scs.map.zboruri.repository.FlightRepo;
import ubb.scs.map.zboruri.repository.TicketRepo;
import ubb.scs.map.zboruri.utils.Observable;
import ubb.scs.map.zboruri.utils.Observer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Service extends Observable {
    private ClientRepo clientRepo;
    private FlightRepo flightRepo;
    private TicketRepo ticketRepo;

    public Service(ClientRepo clientRepo, FlightRepo flightRepo, TicketRepo ticketRepo) {
        this.clientRepo = clientRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    public Client findClientByUsername(String username) {
        return clientRepo.findOne(username);
    }

    public List<String> getFromList(){
        return flightRepo.getFromList();
    }

    public List<String> getToList(){
        return flightRepo.getToList();
    }


    public List<Flight> getAllFlights() {
        for(Flight flight : flightRepo.findAll())
            System.out.println(flight);
        return flightRepo.findAll();
    }

    public int getAvailableSeats(Long flightId) {
        Flight flight  = flightRepo.findOne(flightId);
        if(flight == null) {
            System.out.println("Flight not found with ID: " + flightId);
            return 0;
        }

        int totalSeats = flight.getSeats();
        int boughtSeats = 0;
        for(Ticket ticket : ticketRepo.findAll()) {
            if(Objects.equals(ticket.getFlightId(), flightId)) {
                boughtSeats++;
            }
        }
        return totalSeats - boughtSeats;
    }

    public void buyTicket(String username, Long flightId) {
        Ticket ticket = new Ticket(username, flightId, LocalDateTime.now());
        ticketRepo.add(ticket);
        notifyObservers();
    }
}
