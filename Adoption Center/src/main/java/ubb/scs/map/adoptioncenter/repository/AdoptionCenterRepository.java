package ubb.scs.map.adoptioncenter.repository;

import ubb.scs.map.adoptioncenter.domain.AdoptionCenter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptionCenterRepository implements Repository<Integer, AdoptionCenter> {

    private final String url;
    private final String username;
    private final String password;

    public AdoptionCenterRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<AdoptionCenter> findAll() {
        List<AdoptionCenter> centers = new ArrayList<>();
        String sql = "SELECT * FROM adoption_centers";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                Integer capacity = resultSet.getInt("capacity");

                AdoptionCenter center = new AdoptionCenter(name, location, capacity);
                center.setId(id);
                centers.add(center);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centers;
    }

    @Override
    public AdoptionCenter findOne(Integer id) {
        String sql = "SELECT * FROM adoption_centers WHERE id = ?";
        AdoptionCenter center = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                Integer capacity = resultSet.getInt("capacity");

                center = new AdoptionCenter(name, location, capacity);
                center.setId(id);
            } else {
                return null; // Adoption Center not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return center;
    }

    @Override
    public void update(AdoptionCenter entity) {
        String sql = "UPDATE adoption_centers SET name = ?, location = ?, capacity = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getLocation());
            preparedStatement.setInt(3, entity.getCapacity());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AdoptionCenter> findByLocation(String location) {
        List<AdoptionCenter> centers = new ArrayList<>();
        String sql = "SELECT * FROM adoption_centers WHERE location = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, location);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer capacity = resultSet.getInt("capacity");

                AdoptionCenter center = new AdoptionCenter(name, location, capacity);
                center.setId(id);
                centers.add(center);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centers;
    }
}