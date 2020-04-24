package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartDao {

    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> update(ShoppingCart shoppingCart);

    void clear(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);

}
