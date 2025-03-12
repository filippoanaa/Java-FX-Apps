package ubb.scs.map.adoptioncenter.repository;

import ubb.scs.map.adoptioncenter.domain.Animal;
import ubb.scs.map.adoptioncenter.domain.Animal.AnimalType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository implements Repository<Integer, Animal> {

    private final String url;
    private final String username;
    private final String password;

    public AnimalRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Animal> findAll() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer centerId = resultSet.getInt("center_id");
                AnimalType type = AnimalType.valueOf(resultSet.getString("type"));

                Animal animal = new Animal(name, centerId, type);
                animal.setId(id);
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    @Override
    public Animal findOne(Integer id) {
        Animal animal = null;
        String sql = "SELECT * FROM animals WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer centerId = resultSet.getInt("center_id");
                AnimalType type = AnimalType.valueOf(resultSet.getString("type"));

                animal = new Animal(name, centerId, type);
                animal.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animal;
    }

    @Override
    public void update(Animal entity) {
        String sql = "UPDATE animals SET name = ?, center_id = ?, type = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getCenterId());
            preparedStatement.setString(3, entity.getType().name());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Animal> findByCenterId(Integer centerId) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE center_id = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, centerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                AnimalType type = AnimalType.valueOf(resultSet.getString("type"));

                Animal animal = new Animal(name, centerId, type);
                animal.setId(id);
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    public List<Animal> findByCenterIdAndType(Integer centerId, AnimalType animalType) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE center_id = ? AND type = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, centerId);
            preparedStatement.setString(2, animalType.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                AnimalType type = AnimalType.valueOf(resultSet.getString("type"));

                Animal animal = new Animal(name, centerId, type);
                animal.setId(id);
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }
}