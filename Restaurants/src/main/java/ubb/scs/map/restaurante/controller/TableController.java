package ubb.scs.map.restaurante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ubb.scs.map.restaurante.domain.MenuItem;
import ubb.scs.map.restaurante.domain.Order;
import ubb.scs.map.restaurante.domain.OrderStatus;
import ubb.scs.map.restaurante.domain.Table;
import ubb.scs.map.restaurante.service.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableController {

    private Service service;
    private Table table;

    @FXML
    private ListView<VBox> listView;

    @FXML
    private Button placeOrder;

    public void setService(Service service) {
        this.service = service;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        Map<String, List<MenuItem>> groupedMenuItems = menuItems.stream()
                .collect(Collectors.groupingBy(MenuItem::getCategory));

        listView.getItems().clear();

        groupedMenuItems.forEach((category, items) -> {
            VBox categoryBox = new VBox();
            categoryBox.getChildren().add(new Label(category));

            items.forEach(menuItem -> {
                CheckBox checkBox = new CheckBox(menuItem.getItem());
                categoryBox.getChildren().add(checkBox);
            });

            listView.getItems().add(categoryBox);
        });
    }

    @FXML
    public void handlePlaceOrder() {
        List<Integer> items = new ArrayList<>();

        listView.getItems().forEach(categoryBox ->
                categoryBox.getChildren().forEach(node -> {
                    if (node instanceof CheckBox checkBox && checkBox.isSelected()) {
                        items.add(service.getIdByName(checkBox.getText()));
                    }
                })
        );

        if (items.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "No items selected. Please select at least one item.").showAndWait();
        } else {
            Order order = new Order(table.getId(), items, LocalDateTime.now(), OrderStatus.PLACED);
            service.placeOrder(order);

            listView.getItems().forEach(categoryBox ->
                    categoryBox.getChildren().forEach(node -> {
                        if (node instanceof CheckBox checkBox) {
                            checkBox.setSelected(false);
                        }
                    })
            );
        }
    }
}