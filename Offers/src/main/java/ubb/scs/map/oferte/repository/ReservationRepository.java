package ubb.scs.map.oferte.repository;

import ubb.scs.map.oferte.domain.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository implements Repository<Double, Reservation> {
    private final String url;
    private final String username;
    private final String password;

    public ReservationRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("id");
                Long userId = resultSet.getLong("user_id");
                Double hotelId = resultSet.getDouble("hotel_id");
                Timestamp startDate = resultSet.getTimestamp("start_date");
                int noNights = resultSet.getInt("no_nights");

                Reservation reservation = new Reservation(userId, hotelId, startDate.toLocalDateTime(), noNights);
                reservation.setId(id);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (client_id, hotel_id, start_date, no_nights) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, reservation.getClientId());
            preparedStatement.setDouble(2, reservation.getHotelId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(reservation.getStartDate()));
            preparedStatement.setInt(4, reservation.getNoNights());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating reservation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId(generatedKeys.getDouble(1));
                } else {
                    throw new SQLException("Creating reservation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }
}