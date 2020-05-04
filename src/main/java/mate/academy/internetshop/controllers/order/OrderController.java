package mate.academy.internetshop.controllers.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;

public class OrderController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("order_id");
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        if (orderId == null
                && shoppingCartService.getByUserId(userId).getProducts().size() == 0) {
            req.setAttribute("msg", "Can't create order with empty shopping cart!");
            req.getRequestDispatcher("/WEB-INF/views/shoppingcarts/shopping_cart.jsp")
                    .forward(req,resp);
        }
        Order order;
        if (orderId != null) {
            order = orderService.get(Long.parseLong(orderId));
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
            order = orderService.completeOrder(
                 shoppingCartService.getAllProducts(shoppingCart),
                 shoppingCart.getUser());
        }
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/orders/complete.jsp").forward(req,resp);
    }
}
