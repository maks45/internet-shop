package mate.academy.internetshop.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private List<Item> items;
    private Long orderId;
    private User user;

    public Order(List<Item> items, Long orderId, User user) {
        this.items = items;
        this.orderId = orderId;
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return items.equals(order.items)
                && orderId.equals(order.orderId)
                && user.equals(order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, orderId, user);
    }

    @Override
    public String toString() {
        return "Order{"
                + "items=" + items
                + ", orderId=" + orderId
                + ", user=" + user
                + '}';
    }
}
