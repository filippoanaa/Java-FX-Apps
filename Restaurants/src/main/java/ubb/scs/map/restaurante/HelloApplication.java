package ubb.scs.map.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.restaurante.controller.OrdersController;
import ubb.scs.map.restaurante.controller.TableController;
import ubb.scs.map.restaurante.domain.MenuItem;
import ubb.scs.map.restaurante.domain.Table;
import ubb.scs.map.restaurante.repository.MenuItemRepository;
import ubb.scs.map.restaurante.repository.OrderRepository;
import ubb.scs.map.restaurante.repository.TableRepository;
import ubb.scs.map.restaurante.service.Service;
import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            final String url = "jdbc:postgresql://localhost:5432/restaurant";
            final String username = "postgres";
            final String password = "postgres";

            TableRepository tableRepository = new TableRepository(url, username, password);
            OrderRepository orderRepository = new OrderRepository(url, username, password);
            MenuItemRepository menuItemRepository = new MenuItemRepository(url, username, password);

            Service service = new Service(tableRepository, menuItemRepository, orderRepository);

            initializeOrdersWindow(service);

            initializeTableWindows(service);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeOrdersWindow(Service service) throws IOException {
        Stage ordersStage = new Stage();
        FXMLLoader ordersLoader = new FXMLLoader(getClass().getResource("/views/ordersView.fxml"));
        Parent ordersRoot = ordersLoader.load();
        OrdersController ordersController = ordersLoader.getController();
        ordersController.setService(service);
        ordersStage.setTitle("Staff");
        ordersStage.setScene(new Scene(ordersRoot));
        ordersStage.show();
    }

    private void initializeTableWindows(Service service) throws IOException {
        List<Table> tables = service.getTables();
        List<MenuItem> menuItems = service.getMenuItems();

        for (Table table : tables) {
            Stage tableStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tableView.fxml"));
            Parent tableRoot = fxmlLoader.load();
            TableController tableController = fxmlLoader.getController();
            tableController.setService(service);
            tableController.setTable(table);
            tableController.setMenuItems(menuItems);
            tableStage.setTitle("Table " + table.getId());
            tableStage.setScene(new Scene(tableRoot));
            tableStage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}