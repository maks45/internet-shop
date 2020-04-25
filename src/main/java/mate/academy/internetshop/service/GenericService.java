package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, K> {
    T create(T item);

    T update(T item);

    boolean delete(K id);

    T get(K id);

    List<T> getAll();
}
