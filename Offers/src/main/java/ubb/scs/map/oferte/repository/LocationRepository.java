package ubb.scs.map.oferte.repository;

import ubb.scs.map.oferte.domain.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository implements Repository<Double, Location>{
    private final String url;
    private final String username;
    private final String password;

    public LocationRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Location> getAll() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM location";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("id");
                String locationName = resultSet.getString("location_name");

                Location location = new Location(locationName);
                location.setId(id);
                locations.add(location);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locations;
    }


}