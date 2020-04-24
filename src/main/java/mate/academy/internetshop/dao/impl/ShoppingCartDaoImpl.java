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
    public Optional<ShoppingCart> update(ShoppingCart shoppingCart) {
        int index = IntStream.range(0, Storage.shoppingCarts.size())
                .filter(i -> Storage.shoppingCarts.get(i).getId().equals(shoppingCart.getId()))
                .findFirst().orElse(-1);
        if (index == -1) {
            return Optional.empty();
        }
        return Optional.of(Storage.shoppingCarts.set(index, shoppingCart));
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.stream()
                .filter(shoppingCart1 -> shoppingCart1.getId().equals(shoppingCart.getId()))
                .findFirst().get().getProducts().clear();
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getUser().getId().equals(userId))
                .findFirst();
    }
}
