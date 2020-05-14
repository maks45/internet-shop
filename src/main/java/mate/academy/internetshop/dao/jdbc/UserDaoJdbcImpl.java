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
        String query = "SELECT users.user_id, usersname, login, password, "
                + "role_name, roles.role_id FROM users "
                + "INNER JOIN users_roles "
                + "ON users.user_id = users_roles.user_id "
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id "
                + "WHERE users.login = ?;";
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
        String query = "SELECT users.user_id, usersname, login, password, "
                + "role_name, roles.role_id FROM users "
                + "INNER JOIN users_roles ON users.user_id = users_roles.user_id "
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id  "
                + " WHERE users.user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
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
        String query = "SELECT users.user_id, usersname, login, password, "
                + "role_name, roles.role_id FROM users "
                + "INNER JOIN users_roles ON users.user_id = users_roles.user_id "
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id "
                + "ORDER BY users.user_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
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
        Set<Role> roles = new HashSet<>();
        User user = new User(
                resultSet.getString("usersname"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                roles);
        user.setId(resultSet.getLong("user_id"));
        do {
            if (!user.getId().equals(resultSet.getLong("user_id"))) {
                resultSet.previous();
                break;
            }
            user.getRoles().add(getRoleFromResultSet(resultSet));
        } while (resultSet.next());
        return user;
    }

    private Role getRoleFromResultSet(ResultSet resultSet) throws SQLException {
        Role role = Role.of(resultSet.getString("role_name"));
        role.setId(resultSet.getLong("role_id"));
        return role;
    }
}
