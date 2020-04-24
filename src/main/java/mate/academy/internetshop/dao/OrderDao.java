package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

public interface OrderDao {
    Order create(List<Product> products, User user);

    Order update(Order order);

    List<Order> getUserOrders(User user);

    Optional<Order> get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
