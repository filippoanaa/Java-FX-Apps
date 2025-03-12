package ubb.scs.map.oferte.repository;

import ubb.scs.map.oferte.domain.Hotel;
import ubb.scs.map.oferte.domain.HotelType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRepository implements Repository<Double, Hotel> {
    private final String url;
    private final String username;
    private final String password;

    public HotelRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Hotel> getAll() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotel";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Double id = resultSet.getDouble("id");
                Double locationId = resultSet.getDouble("location_id");
                String name = resultSet.getString("name");
                Integer noRooms = resultSet.getInt("no_rooms");
                Double pricePerNight = resultSet.getDouble("price_per_night");
                HotelType type = HotelType.valueOf(resultSet.getString("type"));

                Hotel hotel = new Hotel(locationId, name, noRooms, pricePerNight, type);
                hotel.setId(id);
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }
}