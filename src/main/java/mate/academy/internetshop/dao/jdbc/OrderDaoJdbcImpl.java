package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(User user) {
        return getUserOrdersByUserId(user.getId());
    }

    @Override
    public List<Order> getUserOrdersByUserId(Long userId) {
        String query = "SELECT o.order_id, order_user_id "
                + "FROM orders as o WHERE o.order_user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, userId);
            List<Order> orders = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("cant get orders for user id: " + userId, e);
        }
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (order_user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            order.setOrderId(resultSet.getLong(1));
            setOrderProducts(order, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order ", e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET order_user_id = ? WHERE order_id = ?;";
        String deleteOrderProductsQuery = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement deleteOrderProductsPrepareStatement =
                    connection.prepareStatement(deleteOrderProductsQuery);
            deleteOrderProductsPrepareStatement.setLong(1, order.getOrderId());
            deleteOrderProductsPrepareStatement.executeUpdate();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getOrderId());
            preparedStatement.executeUpdate();
            setOrderProducts(order, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order ", e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT o.order_id, order_user_id "
                + "FROM orders as o WHERE o.order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getOrderFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders with id: " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT o.order_id, order_user_id "
                + "FROM orders as o ORDER BY o.order_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            List<Order> orders = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                getOrderFromResultSet(resultSet);
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("can't get all orders: ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM orders WHERE order_id = ?;";
        String deleteFromOrdersUsersQuery = "DELETE FROM orders_products "
                + "WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement deleteFromOrdersUsersStatement =
                    connection.prepareStatement(deleteFromOrdersUsersQuery);
            deleteFromOrdersUsersStatement.setLong(1, id);
            deleteFromOrdersUsersStatement.executeUpdate();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id: " + id, e);
        }
    }

    private void setOrderProducts(Order order, Connection connection)
            throws SQLException {
        String setOrderProductsQuery =
                "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)";
        PreparedStatement preparedStatement =
                connection.prepareStatement(setOrderProductsQuery);
        for (Product product : order.getProducts()) {
            preparedStatement.setLong(1, order.getOrderId());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet)
            throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Order order = new Order(
                getOrderProducts(orderId),
                resultSet.getLong("order_user_id"));
        order.setOrderId(orderId);
        return order;
    }

    private List<Product> getOrderProducts(Long id) {
        String query = "SELECT p.product_id, product_name, price "
                + "FROM products as p "
                + "JOIN orders_products op ON p.product_id = op.product_id "
                + "WHERE op.order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, id);
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("product_name"),
                        resultSet.getBigDecimal("price")
                );
                product.setId(resultSet.getLong("product_id"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("cant get products for order id: " + id, e);
        }
    }
}
