package mate.academy.internetshop.model;

import java.util.List;
import java.util.Objects;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private User user;

    public ShoppingCart(List<Product> products, User user) {
        this.products = products;
        this.user = user;
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
        if (!(o instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart shoppingCart = (ShoppingCart) o;
        return products.equals(shoppingCart.products)
                && id.equals(shoppingCart.id)
                && user.equals(shoppingCart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, id, user);
    }

    @Override
    public String toString() {
        return "Bucket{"
                + "items=" + products
                + ", id=" + id
                + ", user=" + user
                + '}';
    }
}
