package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthorizationFilter.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/edit", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/create", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/show", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/get-by-user", Set.of(Role.RoleName.ADMIN));

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestedUrl = req.getServletPath();
        Long userId = (Long) req.getSession().getAttribute("user_id");
        if (protectedUrls.get(requestedUrl) != null
                && !isAuthorized(userService.get(userId), protectedUrls.get(requestedUrl))) {
            logger.warn("user with id: " + userId + " try get access to "
                    + requestedUrl + " access denied");
            req.getRequestDispatcher("/WEB-INF/views/access_denied.jsp").forward(req, resp);
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        return authorizedRoles.stream().anyMatch(
                roleName -> user.getRoles().stream()
                        .map(Role::getRoleName)
                        .anyMatch(roleName::equals));
    }
}
