package mate.academy.internetshop.controllers;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.UserService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/index.jsp").forward(req,resp);
    }
}
