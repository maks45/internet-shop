package mate.academy.internetshop.controllers.product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.ProductService;

public class DeleteProductController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productId = req.getParameter("product_id");
        productService.delete(Long.parseLong(productId));
        resp.sendRedirect(req.getContextPath() + "/products/edit");
    }
}
