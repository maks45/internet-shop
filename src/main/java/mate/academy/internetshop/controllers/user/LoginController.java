package mate.academy.internetshop.controllers.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.security.AuthenticationService;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Logger logger = Logger.getLogger(LoginController.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final AuthenticationService authenticationService =
            (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user;
        try {
            user = authenticationService.login(login, password);
            req.getSession().setAttribute(USER_ID, user.getId());
        } catch (AuthenticationException e) {
            logger.warn("Authentication error : " + e);
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
            return;
        }
        req.getSession().setAttribute("user_id", user.getId());
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
