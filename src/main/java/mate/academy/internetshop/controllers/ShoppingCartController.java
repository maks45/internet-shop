package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ShoppingCartService;

public class ShoppingCartController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private static final Long USER_ID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = null;
        try {
            shoppingCart = shoppingCartService.get(USER_ID);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        if (shoppingCart == null) {
            req.setAttribute("msg", "Register first!");
            req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req, resp);
        } else {
            req.setAttribute("products", shoppingCartService
                    .getAllProducts(shoppingCartService.get(USER_ID)));
            req.getRequestDispatcher("/WEB-INF/views/shopping_cart.jsp").forward(req, resp);
        }
    }
}
