package mate.academy.internetshop;

import java.math.BigDecimal;
import java.util.ArrayList;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user = userService.create(new User("Maks", "maks23", "777"));
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        System.out.println(orderService.getAll());
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product product1 = productService.create(new Product("item_1", new BigDecimal("19.0")));
        Product product2 = productService.create(new Product("item_2", new BigDecimal("28.0")));
        ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart = shoppingCartService
                .addProduct(new ShoppingCart(new ArrayList<>(), user), product1);
        shoppingCartService.deleteProduct(shoppingCart, product1);
        shoppingCartService.addProduct(shoppingCart, product2);
        productService.delete(product2.getId());
        Product product3 = productService.create(new Product("item_3", new BigDecimal("46.0")));
        shoppingCartService.addProduct(shoppingCart, product3);
        System.out.println(shoppingCart);
        Order order = orderService.completeOrder(shoppingCart.getProducts(), user);
        System.out.println(order);
        System.out.println(shoppingCartService.get(user.getId()));
        userService.delete(user.getId());
        System.out.println(userService.getAll());
        System.out.println(orderService.getAll());
    }
}
