package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.model.Product;

public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        return Storage.addProduct(product);
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product update(Product product) {
        return Storage.products.set(
                IntStream.range(0, Storage.products.size())
                .filter(i -> Storage.products.get(i).getId().equals(product.getId()))
                .findFirst().getAsInt(),
                product);
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(item -> item.getId().equals(id));
    }

    @Override
    public boolean delete(Product product) {
        return Storage.products.remove(product);
    }
}
