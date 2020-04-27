package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static UserService userService = (UserService) injector.getInstance(UserService.class);
    private static ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("pwd");
        String confirmPassword = req.getParameter("pwd-confirm");
        if (password.equals(confirmPassword)) {
            User user = userService.create(new User(name,login,password));
            shoppingCartService.create(new ShoppingCart(new ArrayList<>(), user));
            resp.sendRedirect(req.getContextPath() + "/users/all");
        } else {
            req.setAttribute("msg", "Password and confirm password must be same!");
            req.setAttribute("login", login);
            req.setAttribute("name", name);
            req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
