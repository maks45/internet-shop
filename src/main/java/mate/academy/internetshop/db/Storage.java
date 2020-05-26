package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static Long productIdCounter = 0L;
    private static Long userIdCounter = 0L;
    private static Long orderIdCounter = 0L;
    private static Long shoppingCardIdCounter = 0L;

    public static Product addProduct(Product product) {
        productIdCounter++;
        product.setId(productIdCounter);
        products.add(product);
        return product;
    }

    public static User addUser(User user) {
        userIdCounter++;
        user.setId(userIdCounter);
        users.add(user);
        return user;
    }

    public static Order addOrder(Order order) {
        orderIdCounter++;
        order.setOrderId(orderIdCounter);
        orders.add(order);
        return order;
    }

    public static ShoppingCart addShoppingCard(ShoppingCart shoppingCart) {
        ///added to check how sonar works
        shoppingCart = null;
        shoppingCardIdCounter++;
        shoppingCart.setId(shoppingCardIdCounter);
        shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
}
