package mate.academy.internetshop.dao.jdbc;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;
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

public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users INNER JOIN users_roles " +
                "ON users.user_id = users_roles.user_id " +
                "INNER JOIN roles ON users_roles.role_id = roles.role_id " +
                "WHERE users.login = ?  ORDER BY users.user_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getUsersFromResultSet(resultSet).stream().findFirst();
        } catch (SQLException e) {
            throw new DataProcessingException("cant get all orders: ", e);
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
            throw new DataProcessingException("Can't create product ", e);
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
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            setUserRoles(user, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create product ", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users INNER JOIN users_roles " +
                "ON users.user_id = users_roles.user_id " +
                "INNER JOIN roles ON users_roles.role_id = roles.role_id " +
                "WHERE users.user_id = ?  ORDER BY users.user_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getUsersFromResultSet(resultSet).stream().findFirst();
        } catch (SQLException e) {
            throw new DataProcessingException("cant get all orders: ", e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users INNER JOIN users_roles ON users.user_id = users_roles.user_id " +
                "INNER JOIN roles ON users_roles.role_id = roles.role_id ORDER BY users.user_id;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("cant get all orders: ", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id: " + id, e);
        }
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
            insertUserRolesPrepareStatement.setLong(2,
                    getRoleIdPrepareStatement.executeQuery().getLong("role_id"));
            insertUserRolesPrepareStatement.executeUpdate();
        }
    }

    private List<User> getUsersFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (resultSet.next()) {
            Long userId = resultSet.getLong("user_id");
            if (users.isEmpty() || !userId.equals(users.get(users.size() - 1).getId())) {
                Set<Role> roles = new HashSet<>();
                roles.add(Role.of(resultSet.getString("role_name")));
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        roles
                );
                user.setId(resultSet.getLong("user_id"));
                users.add(user);
            } else {
                Role role = Role.of(resultSet.getString("role_name"));
                role.setId(resultSet.getLong("role_id"));
                users.get(users.size() - 1).getRoles().add(role);
            }
        }
        return users;
    }
}
