package mate.academy.internetshop.web.filters;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.UserService;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getServletPath();
        if (url.equals("/login") || url.equals("/registration")) {
            filterChain.doFilter(request,response);
            return;
        }
        Long userId = (Long) request.getSession().getAttribute("user_id");
        if (userId == null || userService.get(userId) == null) {
           response.sendRedirect("/login");
           return;
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
