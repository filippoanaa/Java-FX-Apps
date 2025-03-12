package ubb.scs.map.restaurante.repository;

import ubb.scs.map.restaurante.domain.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRepository implements Repository<MenuItem> {
    private final String url;
    private final String username;
    private final String password;

    public MenuItemRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<MenuItem> getAll() {
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from menu_items");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String category = resultSet.getString("category");
                String item = resultSet.getString("item");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");

                MenuItem mi=new MenuItem(category,item,price,currency);
                mi.setId(id);
                menuItems.add(mi);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return menuItems;
    }

}
