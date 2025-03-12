package ubb.scs.map.zboruri.repository;

import ubb.scs.map.zboruri.domain.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightRepo implements Repository<Long, Flight> {
    private final String url;
    private final String username;
    private final String password;

    public FlightRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String from = resultSet.getString("from_f");
                String to = resultSet.getString("to_f");
                LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime arrivalTime = resultSet.getTimestamp("landing_time").toLocalDateTime();
                Integer seats = resultSet.getInt("seats");

                Flight flight = new Flight(from, to,departureTime,arrivalTime,seats);
                flight.setId(id);
                flights.add(flight);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    public List<String> getFromList(){
        List<String> all = new ArrayList<>();
        String sql = "SELECT DISTINCT from_f FROM flights";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String from = resultSet.getString("from_f");
                all.add(from);
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return all;

    }

    public List<String> getToList(){
        List<String> all = new ArrayList<>();
        String sql = "SELECT DISTINCT to_f FROM flights";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String to = resultSet.getString("to_f");
                all.add(to);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return all;

    }

    public Flight findOne(Long id) {
        String sql = "SELECT * FROM flights WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String from = resultSet.getString("from_f");
            String to = resultSet.getString("to_f");
            LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
            LocalDateTime arrivalTime = resultSet.getTimestamp("landing_time").toLocalDateTime();
            Integer seats = resultSet.getInt("seats");

            Flight flight = new Flight(from,to,departureTime,arrivalTime,seats);
            flight.setId(id);
            return flight;
        } catch (SQLException e) {
            return null;

        }

    }
}
