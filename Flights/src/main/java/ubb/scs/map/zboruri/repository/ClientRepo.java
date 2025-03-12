package ubb.scs.map.zboruri.repository;

import ubb.scs.map.zboruri.domain.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepo implements Repository<String, Client> {

    private final String url;
    private final String username;
    private final String password;

    public ClientRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "select * from clients";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");

                Client client = new Client(username, name);
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    public Client findOne(String s) {
        String sql = "SELECT * FROM clients WHERE username = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                return new Client(username, name);
            } else {
                return null; // Client not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
