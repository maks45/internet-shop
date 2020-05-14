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
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT users.user_id, usersname, login, password "
                + "FROM users WHERE users.login = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getUserFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("can't get user with login: " + login, e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (usersname, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong(1));
            setUserRoles(user, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user ", e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET usersname = ?, login = ?, password= ? WHERE user_id = ? ;";
        String deleteUserRolesQuery = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement deleteRolesPreparedStatement = connection
                    .prepareStatement(deleteUserRolesQuery);
            deleteRolesPreparedStatement.setLong(1, user.getId());
            deleteRolesPreparedStatement.executeUpdate();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            setUserRoles(user, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user ", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT users.user_id, usersname, login, password"
                + " FROM users WHERE users.user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getUserFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("can't get user with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT users.user_id, usersname, login, password "
                + "FROM users ORDER BY users.user_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("cant get all users: ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String[] query = new String[]{
                "DELETE FROM users_roles WHERE user_id = ?;",
                "DELETE FROM shopping_carts WHERE shopping_cart_user_id = ?;",
                "DELETE FROM orders WHERE order_user_id = ?;",
                "DELETE FROM users WHERE user_id = ?;"
        };
        try (Connection connection = ConnectionUtil.getConnection()) {
            return deleteUser(id, query, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id: " + id, e);
        }
    }

    static boolean deleteUser(Long id, String[] queries, Connection connection)
            throws SQLException {
        int deleteFieldsCounter = 0;
        for (String query : queries) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            deleteFieldsCounter += preparedStatement.executeUpdate();
        }
        return deleteFieldsCounter >= 1;
    }

    private void setUserRoles(User user, Connection connection)
            throws SQLException {
        String userRolesQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        String getRoleIdQuery = "SELECT * FROM roles WHERE role_name = ?";
        PreparedStatement insertUserRolesPrepareStatement = connection
                .prepareStatement(userRolesQuery);
        insertUserRolesPrepareStatement.setLong(1, user.getId());
        PreparedStatement getRoleIdPrepareStatement = connection
                .prepareStatement(getRoleIdQuery);
        for (Role role : user.getRoles()) {
            getRoleIdPrepareStatement.setString(1, role.getRoleName().name());
            ResultSet resultSet = getRoleIdPrepareStatement.executeQuery();
            resultSet.next();
            insertUserRolesPrepareStatement.setLong(2,
                    resultSet.getLong("role_id"));
            insertUserRolesPrepareStatement.executeUpdate();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        User user = new User(
                resultSet.getString("usersname"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                getUserRoles(userId));
        user.setId(userId);
        return user;
    }

    private Set<Role> getUserRoles(Long userId) {
        String query = "SELECT roles.role_id, roles.role_name FROM roles "
                + "INNER JOIN users_roles ON roles.role_id = users_roles.role_id "
                + "WHERE users_roles.user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            Set<Role> roles = new HashSet<>();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = Role.of(resultSet.getString("role_name"));
                role.setId(resultSet.getLong("role_id"));
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get roles for user with id: " + userId, e);
        }
    }
}
