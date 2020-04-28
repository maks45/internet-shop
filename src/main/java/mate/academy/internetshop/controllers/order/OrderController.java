package mate.academy.internetshop.controllers.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class OrderController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);
    private static ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private static UserService userService
            = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = (String) req.getParameter("order_id");
        Order order;
        if (orderId != null) {
            order = orderService.get(Long.parseLong(orderId));
        } else {
            order = orderService.completeOrder(
                 shoppingCartService.getAllProducts(shoppingCartService.get(USER_ID)),
                 userService.get(USER_ID));
        }
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/orders/complete.jsp").forward(req,resp);
    }
}
