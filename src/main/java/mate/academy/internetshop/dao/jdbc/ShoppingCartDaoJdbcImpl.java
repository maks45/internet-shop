package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT shopping_carts.shopping_cart_id, shopping_cart_user_id "
                + "FROM shopping_carts "
                + "WHERE shopping_carts.shopping_cart_user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getShoppingCartFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("cant get shopping cart by user id: " + userId, e);
        }
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts (shopping_cart_user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            setShoppingCartProducts(shoppingCart, connection);
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            shoppingCart.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create shopping cart ", e);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        String query = "UPDATE shopping_carts SET shopping_cart_user_id = ? "
                + "WHERE shopping_cart_id = ?;";
        String deleteSoppingCartProductsQuery = "DELETE FROM shopping_cart_products "
                + "WHERE shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement deleteOrderProductsPrepareStatement =
                    connection.prepareStatement(deleteSoppingCartProductsQuery);
            deleteOrderProductsPrepareStatement.setLong(1, shoppingCart.getId());
            deleteOrderProductsPrepareStatement.executeUpdate();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.setLong(2, shoppingCart.getId());
            preparedStatement.executeUpdate();
            setShoppingCartProducts(shoppingCart, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update shopping cart ", e);
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT sc.shopping_cart_id, shopping_cart_user_id "
                + "FROM shopping_carts as sc WHERE sc.shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getShoppingCartFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("cant get shopping cart with id: " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT shopping_cart_id, shopping_cart_user_id "
                + "FROM shopping_carts as sc ORDER BY sc.shopping_cart_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shoppingCarts.add(getShoppingCartFromResultSet(resultSet));
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("cant get all shopping carts: ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM shopping_carts WHERE shopping_cart_id = ?;";
        String deleteFromShoppingCartProductsQuery =
                "DELETE FROM shopping_cart_products WHERE shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement deleteFromShoppingCartProductsStatement =
                    connection.prepareStatement(deleteFromShoppingCartProductsQuery);
            deleteFromShoppingCartProductsStatement.setLong(1, id);
            deleteFromShoppingCartProductsStatement.executeUpdate();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete shopping cart with id: " + id, e);
        }
    }

    private void setShoppingCartProducts(ShoppingCart shoppingCart, Connection connection)
            throws SQLException {
        String setOrderProductsQuery =
                "INSERT INTO shopping_cart_products (shopping_cart_products.shopping_cart_id"
                        + ", shopping_cart_products.product_id) "
                        + "VALUES (?, ?)";
        PreparedStatement preparedStatement =
                connection.prepareStatement(setOrderProductsQuery);
        for (Product product : shoppingCart.getProducts()) {
            preparedStatement.setLong(1, shoppingCart.getId());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet)
            throws SQLException {
        List<Product> products = new ArrayList<>();
        Long shoppingCartId = resultSet.getLong("shopping_cart_user_id");
        ShoppingCart shoppingCart = new ShoppingCart(
                getShoppingCartProducts(resultSet.getLong("shopping_cart_id")),
                shoppingCartId
        );
        shoppingCart.setId(shoppingCartId);
        return shoppingCart;
    }

    private List<Product> getShoppingCartProducts(Long id) {
        String query = "SELECT products.product_id, product_name, price "
                + "FROM products "
                + "JOIN shopping_cart_products scp "
                + "ON scp.product_id = products.product_id "
                + "WHERE scp.shopping_cart_id = ?;";
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
            throw new DataProcessingException("cant get products for shopping cart id: " + id, e);
        }
    }
}
