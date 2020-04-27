package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T element);

    T update(T element);

    Optional<T> get(K id);

    List<T> getAll();

    boolean delete(K id);
}
