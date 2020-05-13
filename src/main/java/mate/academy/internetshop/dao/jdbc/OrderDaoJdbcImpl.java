package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.Role;
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
        String query = "SELECT * FROM orders "
                + "JOIN orders_products ON orders.order_id = orders_products.order_id "
                + "JOIN products ON orders_products.product_id = products.product_id "
                + "JOIN users ON orders.order_user_id = users.user_id "
                + "JOIN users_roles ON users.user_id = users_roles.user_id "
                + "JOIN roles ON users_roles.role_id = roles.role_id "
                + "WHERE users.user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, userId);
            return getOrdersFromResultSet(preparedStatement.executeQuery());
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
            preparedStatement.setLong(1, order.getUser().getId());
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
            preparedStatement.setLong(1, order.getUser().getId());
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
        String query = "SELECT * FROM orders "
                + "JOIN orders_products ON orders.order_id = orders_products.order_id "
                + "JOIN products ON orders_products.product_id = products.product_id "
                + "JOIN users ON orders.order_user_id = users.user_id "
                + "JOIN users_roles ON users.user_id = users_roles.user_id "
                + "JOIN roles ON users_roles.role_id = roles.role_id "
                + "WHERE orders.order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, id);
            return getOrdersFromResultSet(preparedStatement.executeQuery())
                    .stream().findFirst();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders with id: " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders "
                + "JOIN orders_products ON orders.order_id = orders_products.order_id "
                + "JOIN products ON orders_products.product_id = products.product_id "
                + "JOIN users ON orders.order_user_id = users.user_id "
                + "JOIN users_roles ON users.user_id = users_roles.user_id "
                + "JOIN roles ON users_roles.role_id = roles.role_id ORDER BY orders.order_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            return getOrdersFromResultSet(preparedStatement.executeQuery());
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

    private List<Order> getOrdersFromResultSet(ResultSet resultSet)
            throws SQLException {
        List<Order> orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        Set<Role> userRoles = new HashSet<>();
        while (resultSet.next()) {
            if (orders.isEmpty()
                    || !orders.get(orders.size() - 1).getOrderId()
                    .equals(resultSet.getLong("order_id"))) {
                userRoles = new HashSet<>();
                products = new ArrayList<>();
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        userRoles);
                user.setId(resultSet.getLong("user_id"));
                orders.add(new Order(products, user));
            } else {
                if (products.isEmpty()
                        || !products.get(products.size() - 1).getId()
                        .equals(resultSet.getLong("product_id"))) {
                    Product product = new Product(
                            resultSet.getString("product_name"),
                            resultSet.getBigDecimal("price")
                    );
                    product.setId(resultSet.getLong("product_id"));
                    products.add(product);
                }
                Role currentUserRole = Role.of(resultSet.getString("role_name"));
                if (userRoles.isEmpty() || !userRoles.contains(currentUserRole)) {
                    currentUserRole.setId(resultSet.getLong("role_id"));
                    userRoles.add(currentUserRole);
                }
            }
        }

        return orders;
    }

}
