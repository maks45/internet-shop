package mate.academy.internetshop.controllers.product;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class EditProductsController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private static ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> allProducts = productService.getAll();
        req.setAttribute("products",allProducts);
        req.getRequestDispatcher("/WEB-INF/views/products/edit.jsp").forward(req,resp);
    }
}
