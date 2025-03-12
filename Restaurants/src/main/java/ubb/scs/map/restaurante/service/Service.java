package ubb.scs.map.restaurante.service;

import ubb.scs.map.restaurante.domain.MenuItem;
import ubb.scs.map.restaurante.domain.Order;
import ubb.scs.map.restaurante.domain.OrderStatus;
import ubb.scs.map.restaurante.domain.Table;
import ubb.scs.map.restaurante.repository.MenuItemRepository;
import ubb.scs.map.restaurante.repository.OrderRepository;
import ubb.scs.map.restaurante.repository.TableRepository;
import ubb.scs.map.restaurante.utils.Observable;

import java.time.LocalDateTime;
import java.util.List;

public class Service extends Observable {
    TableRepository tableRepository;
    MenuItemRepository menuItemRepository;
    OrderRepository orderRepository;

    public Service(TableRepository tableRepository, MenuItemRepository menuItemRepository, OrderRepository orderRepository) {
        this.tableRepository = tableRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<Table> getTables() {
        return tableRepository.getAll();
    }

    public List<MenuItem> getMenuItems() {
        return menuItemRepository.getAll();
    }

    public List<Order> getOrders() {
        if(orderRepository.getAll().isEmpty()) {
            System.out.println("Nu se incarca bien orders");
        }
        orderRepository.getAll().forEach(System.out::println);
        return orderRepository.getAll();
    }


    public void placeOrder(Order order) {
        orderRepository.add(order);
        notifyObservers();
    }

    public String getMenuItemById(Integer id) {
        for (MenuItem menuItem : menuItemRepository.getAll()) {
            if (menuItem.getId().equals(id)) {
                return menuItem.getItem();
            }
        }
        return null;
    }

    public Integer getIdByName(String name) {
        for (MenuItem menuItem : menuItemRepository.getAll()) {
            if (menuItem.getItem().equals(name)) {
                return menuItem.getId();
            }
        }
        return null;
    }


}
