package mate.academy.internetshop;

import java.math.BigDecimal;
import mate.academy.internetshop.lib.Injector;
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
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        ShoppingCartService shoppingCartService = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);
        productService.create(new Product("item_1", new BigDecimal("19.0")));
        productService.create(new Product("item_2", new BigDecimal("28.0")));
        productService.create(new Product("item_3", new BigDecimal("46.0")));
        productService.create(new Product("item_4", new BigDecimal("56.0")));
        productService.create(new Product("item_5", new BigDecimal("0.1")));
        System.out.println("after adding");
        productService.getAll().forEach(System.out::println);
        System.out.println("delete item with id == 2");
        System.out.println(productService.delete(2L));
        System.out.println("after deleting");
        productService.getAll().forEach(System.out::println);
        System.out.println("get item with id == 1");
        System.out.println(productService.get(1L));
        System.out.println("update item with id == 1");
    }
}
