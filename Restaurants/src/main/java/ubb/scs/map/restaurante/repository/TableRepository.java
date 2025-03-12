package ubb.scs.map.restaurante.repository;

import ubb.scs.map.restaurante.domain.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableRepository implements Repository<Table> {

    private final String url;
    private final String username;
    private final String password;

    public TableRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public List<Table> getAll() {
        List<Table> tables = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from tables");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("table_id");
                Table table = new Table(id);
                tables.add(table);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }
}
