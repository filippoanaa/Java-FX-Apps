package ubb.scs.map.anar.repository;

import ubb.scs.map.anar.domain.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepository implements Repository<Integer, City> {
    private final String url;
    private final String username;
    private final String password;

    public CityRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<City> findAll() {
        List<City> all = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from cities"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer riverId = resultSet.getInt("river_id");
                Double cmdr = resultSet.getDouble("cmdr");
                Double cma = resultSet.getDouble("cma");

                City city = new City(riverId, name, cmdr, cma);
                city.setId(id);
                all.add(city);
            }
        } catch (SQLException e) {
            return null;
        }
        return all;
    }

    @Override
    public City findOne(Integer integer) {
        return null;
    }


}
