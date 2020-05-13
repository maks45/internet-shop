package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(ProductDaoJdbcImpl.class);

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create product ", e);
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE product_id= ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());
            if (preparedStatement.executeUpdate() == 0) {
                LOGGER.warn("Can't update product with id: " + product.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product ", e);
        }
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getProductFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product with id: " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(getProductFromResultSet(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all products: ", e);
        }
    }

    @Override
    public boolean delete(Product product) {
        return delete(product.getId());
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE product_id= ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete product id: " + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product(
                resultSet.getString("name"),
                resultSet.getBigDecimal("price")
        );
        product.setId(resultSet.getLong("product.id"));
        return product;
    }
}
