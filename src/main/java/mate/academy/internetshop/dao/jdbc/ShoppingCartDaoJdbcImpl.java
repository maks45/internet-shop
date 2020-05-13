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
        String query = "SELECT * FROM shopping_carts "
                + "LEFT JOIN shopping_cart_products scp "
                + "ON shopping_carts.shopping_cart_id = scp.shopping_cart_id "
                + "LEFT JOIN products ON scp.product_id = products.product_id "
                + "WHERE shopping_carts.shopping_cart_user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, userId);
            return getShoppingCartsFromResultSet(preparedStatement.executeQuery())
                    .stream().findFirst();
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
        String query = "SELECT * FROM shopping_carts "
                + "JOIN shopping_cart_products scp "
                + "ON shopping_carts.shopping_cart_id = scp.shopping_cart_id "
                + "JOIN products ON scp.product_id = products.product_id "
                + "WHERE shopping_carts.shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, id);
            return getShoppingCartsFromResultSet(preparedStatement.executeQuery())
                    .stream().findFirst();
        } catch (SQLException e) {
            throw new DataProcessingException("cant get shopping cart with id: " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts "
                + "JOIN shopping_cart_products scp "
                + "ON shopping_carts.shopping_cart_id = scp.shopping_cart_id "
                + "JOIN products ON scp.product_id = products.product_id "
                + "ORDER BY shopping_carts.shopping_cart_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            return getShoppingCartsFromResultSet(preparedStatement.executeQuery());
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

    private List<ShoppingCart> getShoppingCartsFromResultSet(ResultSet resultSet)
            throws SQLException {
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            if (shoppingCarts.isEmpty()
                    || !shoppingCarts.get(shoppingCarts.size() - 1).getId()
                    .equals(resultSet.getLong("shopping_cart_id"))) {
                products = new ArrayList<>();
                ShoppingCart shoppingCart = new ShoppingCart(
                        products,
                        resultSet.getLong("shopping_cart_user_id")
                );
                shoppingCart.setId(resultSet.getLong("shopping_cart_id"));
                shoppingCarts.add(shoppingCart);
            }
            if (resultSet.getString("product_name") != null) {
                Product product = new Product(
                        resultSet.getString("product_name"),
                        resultSet.getBigDecimal("price")
                );
                product.setId(resultSet.getLong("product_id"));
                products.add(product);
            }
        }
        return shoppingCarts;
    }

}
