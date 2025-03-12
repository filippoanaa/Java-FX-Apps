package ubb.scs.map.oferte.domain;

import java.time.LocalDate;
import java.util.Objects;

public class SpecialOffer extends Entity<Double> {
    private Double id;
    private Double hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer percent;

    // Constructor
    public SpecialOffer(Double hotelId, LocalDate startDate, LocalDate endDate, Integer percent) {
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
    }


    // Getters
    @Override
    public Double getId() {
        return id;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getPercent() {
        return percent;
    }

    // Setters
    @Override
    public void setId(Double id) {
        this.id = id;
    }

    public void setHotelId(Double hotelId) {
        this.hotelId = hotelId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialOffer)) return false;
        SpecialOffer that = (SpecialOffer) o;
        return Objects.equals(id, that.id) && Objects.equals(hotelId, that.hotelId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(percent, that.percent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotelId, startDate, endDate, percent);
    }

    // toString
    @Override
    public String toString() {
        return "SpecialOffer{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", percent=" + percent +
                '}';
    }
}