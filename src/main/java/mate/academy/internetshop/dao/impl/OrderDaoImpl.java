package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(List<Product> products, User user) {
        Order order = new Order(List.copyOf(products), user);
        return Storage.addOrder(order);
    }

    @Override
    public Order update(Order order) {
        return Storage.orders.set(
                IntStream.range(0, Storage.orders.size())
                .filter(i -> Storage.orders.get(i).getOrderId().equals(order.getOrderId()))
                .findFirst().getAsInt(),
                order
        );
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return Storage.orders.stream()
                .filter(order -> order.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                .filter(order -> order.getOrderId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(order -> order.getOrderId().equals(id));
    }
}
