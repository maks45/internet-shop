package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {

    @Override
    public Item create(Item item) {
        Storage.itemIdCounter++;
        item.setId(Storage.itemIdCounter);
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        int index = IntStream.range(0,Storage.items.size())
                .filter(i -> Storage.items.get(i).getId().equals(item.getId()))
                .findFirst().orElse(-1);
        return Storage.items.set(index,item);
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.items.removeIf(item -> item.getId().equals(id));
    }

    @Override
    public boolean delete(Item item) {
        return Storage.items.remove(item);
    }
}
