package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;

@Dao
public class ItemDaoImpl implements ItemDao {

    @Override
    public Product create(Product product) {
        product.setId(Storage.getItemId());
        Storage.PRODUCTS.add(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.PRODUCTS.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product update(Product product) {
        return Storage.PRODUCTS.set(
                IntStream.range(0, Storage.PRODUCTS.size())
                .filter(i -> Storage.PRODUCTS.get(i).getId().equals(product.getId()))
                .findFirst().orElse(-1),
                product);
    }

    @Override
    public List<Product> getAll() {
        return Storage.PRODUCTS;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.PRODUCTS.removeIf(item -> item.getId().equals(id));
    }

    @Override
    public boolean delete(Product product) {
        return Storage.PRODUCTS.remove(product);
    }
}
