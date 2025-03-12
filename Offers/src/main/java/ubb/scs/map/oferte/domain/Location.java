package ubb.scs.map.oferte.domain;

import java.util.Objects;

public class Location extends Entity<Double> {
    private Double id;
    private String locationName;

    // Constructor
    public Location( String locationName) {
        this.locationName = locationName;
    }

    // Getters
    @Override
    public Double getId() {
        return id;
    }

    public String getLocationName() {
        return locationName;
    }

    // Setters
    @Override
    public void setId(Double id) {
        this.id = id;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(locationName, location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationName);
    }

    // toString
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}