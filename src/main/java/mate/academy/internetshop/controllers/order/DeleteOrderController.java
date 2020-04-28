package mate.academy.internetshop.controllers.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class DeleteOrderController extends HttpServlet {
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
        String orderId = req.getParameter("order_id");
        orderService.delete(Long.parseLong(orderId));
        resp.sendRedirect(req.getContextPath() + "/orders/user");
    }
}
