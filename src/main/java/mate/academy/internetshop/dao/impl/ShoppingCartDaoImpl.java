package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return Storage.shoppingCarts.set(
                IntStream.range(0, Storage.shoppingCarts.size())
                        .filter(i -> Storage.shoppingCarts.get(i).getId().equals(shoppingCart.getId()))
                        .findFirst().getAsInt(),
                shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart1 -> shoppingCart1.getId().equals(shoppingCart.getId()))
                .findFirst().get()
                .getProducts()
                .removeIf(product1 -> product1.getId().equals(product.getId()));
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.stream()
                .filter(shoppingCart1 -> shoppingCart1.getId().equals(shoppingCart.getId()))
                .findFirst().get().getProducts().clear();
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getUser().getId().equals(userId))
                .findFirst().get();
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart1 -> shoppingCart1.getId().equals(shoppingCart.getId()))
                .findFirst().get().getProducts();
    }
}
