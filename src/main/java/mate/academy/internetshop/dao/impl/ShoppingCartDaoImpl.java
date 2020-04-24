package mate.academy.internetshop.dao.impl;

import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
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
                .findFirst().getAsInt(),
                shoppingCart);
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts.remove(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getUser().getId().equals(userId))
                .findFirst();
    }
}
