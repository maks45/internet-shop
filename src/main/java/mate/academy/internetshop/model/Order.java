package mate.academy.internetshop.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private List<Product> products;
    private Long orderId;
    private Long userId;

    public Order(List<Product> products, Long userId) {
        this.products = products;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                && userId.equals(order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, orderId, userId);
    }

    @Override
    public String toString() {
        return "Order{"
                + "items=" + products
                + ", orderId=" + orderId
                + ", user=" + userId
                + '}';
    }
}
