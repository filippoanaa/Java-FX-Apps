package ubb.scs.map.anar.repository;

import ubb.scs.map.anar.domain.River;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RiverRepository implements Repository<Integer, River> {
    private final String url;
    private final String username;
    private final String password;

    public RiverRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<River> findAll() {
        List<River> all = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from rivers");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double averageLevel = resultSet.getDouble("average_level");

                River river = new River(name, averageLevel);
                river.setId(id);
                all.add(river);

            }
        } catch (SQLException e) {
            return null;
        }
        return all;
    }

    @Override
    public River findOne(Integer id) {
        String sql = "SELECT * FROM rivers WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("name");
            Double averageLevel = resultSet.getDouble("average_level");
            River river = new River(name, averageLevel);
            river.setId(id);
            return river;
        } catch (SQLException e) {
            return null;
        }
    }


    public void update(River river) {
        String sql = "UPDATE rivers SET average_level = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, river.getAverageLevel());
            preparedStatement.setInt(2, river.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


