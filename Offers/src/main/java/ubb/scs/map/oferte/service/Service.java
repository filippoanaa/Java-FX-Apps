package ubb.scs.map.oferte.service;

import ubb.scs.map.oferte.domain.Client;
import ubb.scs.map.oferte.domain.Hotel;
import ubb.scs.map.oferte.domain.Location;
import ubb.scs.map.oferte.domain.Reservation;
import ubb.scs.map.oferte.domain.SpecialOffer;
import ubb.scs.map.oferte.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service {
    private ClientRepository clientRepository;
    private HotelRepository hotelRepository;
    private LocationRepository locationRepository;
    private ReservationRepository reservationRepository;
    private SpecialOfferRepository specialOfferRepository;

    public Service(ClientRepository clientRepository, HotelRepository hotelRepository, LocationRepository locationRepository, ReservationRepository reservationRepository, SpecialOfferRepository specialOfferRepository) {
        this.clientRepository = clientRepository;
        this.hotelRepository = hotelRepository;
        this.locationRepository = locationRepository;
        this.reservationRepository = reservationRepository;
        this.specialOfferRepository = specialOfferRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.getAll();
    }

    public List<Location> getAllLocations() {
        return locationRepository.getAll();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAll();
    }

    public List<SpecialOffer> getAllSpecialOffers() {
        return specialOfferRepository.getAll();
    }

    public Client getClientById(Long id) {
       for(Client client : clientRepository.getAll()) {
           if(Objects.equals(client.getId(), id)) {
               return client;
           }
       }
       return null;
    }

    public Hotel getHotelById(Double id) {
        for(Hotel hotel : hotelRepository.getAll()) {
            if(Objects.equals(hotel.getId(), id))
                return hotel;
        }
        return null;
    }

    public Location getLocationById(Double id) {
        for(Location location : locationRepository.getAll()) {
            if(Objects.equals(location.getId(), id))
                return location;
        }
        return null;
    }

    public List<SpecialOffer> getSpecialOffersForClient(Client client){
        return specialOfferRepository.getAll()
                .stream().filter(offer ->{
                    Integer percent = offer.getPercent();
                    return percent < client.getFidelityGrade() && offer.getStartDate().isAfter(LocalDate.now());
                })
                .toList();
    }

    public Double getLocationIdByName(String location) {
        for(Location loc :locationRepository.getAll()){
            if(loc.getLocationName().equals(location))
                return loc.getId();
        }
        return null;
    }

    public List<SpecialOffer> searchOffers(Double hotelId, LocalDate start, LocalDate end) {
        List<SpecialOffer> specialOffers = new ArrayList<>();
        for (SpecialOffer offer : specialOfferRepository.getAll()) {
            if (offer.getHotelId().equals(hotelId) &&
                    offer.getStartDate().compareTo(start) < 0 &&
                    offer.getEndDate().compareTo(end) > 0) {
                specialOffers.add(offer);
            }
        }
        return specialOffers;
    }

    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}