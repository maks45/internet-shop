package mate.academy.internetshop.controllers.order;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class UserOrdersController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);
    private static UserService userService
            = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> userOrders = orderService.getUserOrders(userService.get(USER_ID));
        req.setAttribute("orders",userOrders);
        req.setAttribute("userId",USER_ID);
        req.getRequestDispatcher("/WEB-INF/views/orders/user.jsp").forward(req,resp);
    }
}