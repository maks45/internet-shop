package mate.academy.internetshop.controllers.user;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.UserService;

public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy.internetshop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("user_id"));
        userService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
