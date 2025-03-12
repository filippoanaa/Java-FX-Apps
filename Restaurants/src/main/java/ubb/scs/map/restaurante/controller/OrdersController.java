package ubb.scs.map.restaurante.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.restaurante.domain.Entity;
import ubb.scs.map.restaurante.domain.Order;
import ubb.scs.map.restaurante.service.Service;
import ubb.scs.map.restaurante.utils.Observer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrdersController implements Observer {
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, Integer> tableIdColumn;
    @FXML
    private TableColumn<Order, String> dateColumn;
    @FXML
    private TableColumn<Order, String> itemsColumn;

    private ObservableList<Order>  ordersObservableList = FXCollections.observableArrayList();
    private Service service;

    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);
        update();
    }

    @FXML
    private void initialize() {
        ordersTable.setItems(ordersObservableList);
        tableIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("tableId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        itemsColumn.setCellValueFactory(order -> {
            String items = order.getValue().getMenuItems().stream()
                    .map(id -> service.getMenuItemById(id))
                    .collect(Collectors.joining(", "));
                    return new SimpleStringProperty(items);
        });
    }

    @Override
    public void update() {
        ordersObservableList.clear();
        ordersObservableList.addAll(service.getOrders());
        ordersObservableList.sort(Comparator.comparing(Order::getDate));

    }
}
