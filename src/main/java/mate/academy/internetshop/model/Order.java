package mate.academy.internetshop.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private List<Product> products;
    private Long orderId;
    private User user;

    public Order(List<Product> products, User user) {
        this.products = products;
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
        return products.equals(order.products)
                && orderId.equals(order.orderId)
                && user.equals(order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, orderId, user);
    }

    @Override
    public String toString() {
        return "Order{"
                + "items=" + products
                + ", orderId=" + orderId
                + ", user=" + user
                + '}';
    }
}
