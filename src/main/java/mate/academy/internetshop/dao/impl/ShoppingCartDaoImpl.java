package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.model.ShoppingCart;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return Storage.addShoppingCard(shoppingCart);
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts.set(
                IntStream.range(0, Storage.shoppingCarts.size())
                .filter(i -> Storage.shoppingCarts.get(i).getId().equals(shoppingCart.getId()))
                .findFirst().orElse(-1),
                shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(shoppingCart -> shoppingCart.getId().equals(id));
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getUserId().equals(userId))
                .findFirst();
    }
}
