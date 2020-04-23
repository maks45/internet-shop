package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Product;

public interface ItemDao {
    Product create(Product product);

    Optional<Product> get(Long id);

    Product update(Product product);

    List<Product> getAll();

    boolean delete(Long id);

    boolean delete(Product product);
}
