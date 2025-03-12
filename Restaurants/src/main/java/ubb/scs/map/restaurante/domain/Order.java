package ubb.scs.map.restaurante.domain;

import org.controlsfx.control.PropertySheet;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends Entity<Integer>{
    private Integer id;
    private Integer tableId;
    private List<Integer> menuItems;
    private LocalDateTime date;
    private OrderStatus status;

    public Order(Integer tableId, List<Integer> menuItems, LocalDateTime date, OrderStatus status) {
        this.tableId = tableId;
        this.menuItems = menuItems;
        this.date = date;
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public List<Integer> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Integer> menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", menuItems=" + menuItems +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}