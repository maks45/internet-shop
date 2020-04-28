package mate.academy.internetshop.controllers.shoppingcart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.ShoppingCartService;

public class ShoppingCartController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private static final Long USER_ID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", shoppingCartService
                .getAllProducts(shoppingCartService.get(USER_ID)));
        req.getRequestDispatcher("/WEB-INF/views/shoppingcarts/shopping_cart.jsp")
                .forward(req, resp);
    }
}
