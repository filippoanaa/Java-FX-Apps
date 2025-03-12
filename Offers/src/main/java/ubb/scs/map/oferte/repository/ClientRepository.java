package ubb.scs.map.oferte.repository;

import ubb.scs.map.oferte.domain.Client;
import ubb.scs.map.oferte.domain.Hobby;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements Repository<Long, Client> {
    private final String url;
    private final String username;
    private final String password;

    public ClientRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "select * from client";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer fidelityGrade = resultSet.getInt("fidelity_grade");
                Integer age = resultSet.getInt("age");
                Hobby hobby = Hobby.valueOf(resultSet.getString("hobby"));

                Client client = new Client(name,fidelityGrade,age,hobby);
                client.setId(id);
                clients.add(client);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

}
