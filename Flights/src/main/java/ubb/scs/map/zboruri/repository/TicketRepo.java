package ubb.scs.map.zboruri.repository;

import ubb.scs.map.zboruri.domain.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo implements Repository<Long, Ticket> {

    private final String url;
    private final String username;
    private final String password;

    public TicketRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "select * from tickets";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Long flightId = resultSet.getLong("flight_id");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchase_time").toLocalDateTime();

                Ticket ticket =  new Ticket(username, flightId, purchaseTime);
                ticket.setId(id);
                tickets.add(ticket);


            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public void add(Ticket ticket) {
        String sql = "INSERT INTO tickets (username, flight_id, purchase_time) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ticket.getUsername());
            preparedStatement.setLong(2, ticket.getFlightId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(ticket.getPurchaseTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
