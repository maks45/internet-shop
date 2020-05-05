package mate.academy.internetshop.model;

import java.util.List;
import java.util.Objects;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(List<Product> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart shoppingCart = (ShoppingCart) o;
        return products.equals(shoppingCart.products)
                && id.equals(shoppingCart.id)
                && userId.equals(shoppingCart.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, id, userId);
    }

    @Override
    public String toString() {
        return "Bucket{"
                + "items=" + products
                + ", id=" + id
                + ", user=" + userId
                + '}';
    }
}
