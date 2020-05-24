package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User admin = userService.create(new User("admin", "admin","1111",
                Set.of(Role.of("ADMIN"))));
        shoppingCartService.create(new ShoppingCart(new ArrayList<>(), admin.getId()));
        User user = userService.create(new User("user", "user","1111",
                Set.of(Role.of("USER"))));
        shoppingCartService.create(new ShoppingCart(new ArrayList<>(), admin.getId()));
        productService.create(new Product("product-1", new BigDecimal("10.0")));
        productService.create(new Product("product-2", new BigDecimal("11.0")));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
