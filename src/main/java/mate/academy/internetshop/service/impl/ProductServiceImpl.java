package mate.academy.internetshop.service.impl;

import java.util.List;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;

@Service
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductDao productDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(Long id) {
        return productDao.get(id).get();
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(Long id) {
        shoppingCartService.getAllShoppingCarts()
                .forEach(shoppingCart -> shoppingCartService
                        .deleteProduct(shoppingCart, productDao.get(id).get()));
        return productDao.delete(id);
    }
}
