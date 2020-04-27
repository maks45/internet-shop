package mate.academy.internetshop.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.model.User;

public class UserController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> allUsers = userService.getAll();
        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("/WEB-INF/views/users/all.jsp").forward(req, resp);
    }
}
