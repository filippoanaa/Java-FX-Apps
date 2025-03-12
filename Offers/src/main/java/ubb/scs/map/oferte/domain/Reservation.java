package ubb.scs.map.oferte.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation extends Entity<Double> {
    private Double id;
    private Long clientId;
    private Double hotelId;
    private LocalDateTime startDate;
    private Integer noNights;

    // Constructor
    public Reservation(Long clientId, Double hotelId, LocalDateTime startDate, Integer noNights) {
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.noNights = noNights;
    }


    // Getters
    @Override
    public Double getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Integer getNoNights() {
        return noNights;
    }

    // Setters
    @Override
    public void setId(Double id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setNoNights(Integer noNights) {
        this.noNights = noNights;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(hotelId, that.hotelId) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(noNights, that.noNights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, hotelId, startDate, noNights);
    }

    // toString
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", noNights=" + noNights +
                '}';
    }
}