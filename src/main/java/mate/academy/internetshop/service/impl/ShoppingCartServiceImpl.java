package mate.academy.internetshop.service.impl;

import java.util.List;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Inject
    ShoppingCartDao shoppingCartDao;

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartDao.getAllShoppingCarts();
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCartDao.addProduct(shoppingCart, product);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCartDao.deleteProduct(shoppingCart, product);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCartDao.clear(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getByUserId(userId);
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return shoppingCartDao.getAllProducts(shoppingCart);
    }
}
