package ubb.scs.map.restaurante.repository;

import ubb.scs.map.restaurante.domain.Order;
import ubb.scs.map.restaurante.domain.OrderStatus;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements Repository <Order> {
    private final String url;
    private final String username;
    private final String password;

    public OrderRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();

        String query = "SELECT o.order_id, o.table_id, o.date, o.status, " +
                "oi.menu_item_id " +
                "FROM orders o " +
                "LEFT JOIN order_items oi ON o.order_id = oi.order_id " +
                "LEFT JOIN tables t ON o.table_id = t.table_id";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer orderId = resultSet.getInt("order_id");
                Order order = findOrderById(orders, orderId);

                if (order == null) {
                    Integer tableId = resultSet.getInt("table_id");
                    LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                    order = new Order(tableId, new ArrayList<>(), date, status);
                    order.setId(orderId);
                    orders.add(order);
                }

                Integer menuItemId = resultSet.getInt("menu_item_id");
                if (menuItemId != 0) {  // Verifică dacă menuItemId nu este nul
                    order.getMenuItems().add(menuItemId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order findOrderById(List<Order> orders, Integer orderId) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public void add(Order order) {
        String insertOrderQuery = "INSERT INTO orders (table_id, date, status) VALUES (?, ?, ?)";
        String insertOrderItemQuery = "INSERT INTO order_items (order_id, menu_item_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statementOrder = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statementOrderItem = connection.prepareStatement(insertOrderItemQuery)) {

            // Insert the order
            statementOrder.setInt(1, order.getTableId());
            statementOrder.setTimestamp(2, Timestamp.valueOf(order.getDate()));
            statementOrder.setString(3, order.getStatus().toString());
            statementOrder.executeUpdate();

            // Get the generated order ID
            ResultSet generatedKeys = statementOrder.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer orderId = generatedKeys.getInt(1);
                order.setId(orderId); // Update the order object with the generated ID

                // Insert the order items
                for (Integer menuItemId : order.getMenuItems()) {
                    statementOrderItem.setInt(1, orderId);
                    statementOrderItem.setInt(2, menuItemId);
                    statementOrderItem.addBatch();
                }
                statementOrderItem.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}