package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    Optional<ShoppingCart> getByUserId(Long userId);
}
