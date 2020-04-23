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
        Storage.ITEMS.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.ITEMS.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        int index = IntStream.range(0,Storage.ITEMS.size())
                .filter(i -> Storage.ITEMS.get(i).getId().equals(item.getId()))
                .findFirst().orElse(-1);
        return Storage.ITEMS.set(index,item);
    }

    @Override
    public List<Item> getAll() {
        return Storage.ITEMS;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.ITEMS.removeIf(item -> item.getId().equals(id));
    }

    @Override
    public boolean delete(Item item) {
        return Storage.ITEMS.remove(item);
    }
}
