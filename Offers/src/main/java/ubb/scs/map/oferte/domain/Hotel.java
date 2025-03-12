package ubb.scs.map.oferte.domain;

import java.util.Objects;

public class Hotel extends Entity<Double> {
    private Double id;
    private Double locationId;
    private String name;
    private Integer noRooms;
    private Double pricePerNight;
    private HotelType type;


    public Hotel(Double locationId, String name, Integer noRooms, Double pricePerNight, HotelType type) {
        this.locationId = locationId;
        this.name = name;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    @Override
    public Double getId() {
        return id;
    }

    public Double getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public Integer getNoRooms() {
        return noRooms;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public HotelType getType() {
        return type;
    }

    // Setters
    @Override
    public void setId(Double id) {
        this.id = id;
    }

    public void setLocationId(Double locationId) {
        this.locationId = locationId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoRooms(Integer noRooms) {
        this.noRooms = noRooms;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setType(HotelType type) {
        this.type = type;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id) && Objects.equals(locationId, hotel.locationId) && Objects.equals(name, hotel.name) && Objects.equals(noRooms, hotel.noRooms) && Objects.equals(pricePerNight, hotel.pricePerNight) && type == hotel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationId, name, noRooms, pricePerNight, type);
    }

    // toString
    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", name='" + name + '\'' +
                ", noRooms=" + noRooms +
                ", pricePerNight=" + pricePerNight +
                ", type=" + type +
                '}';
    }
}