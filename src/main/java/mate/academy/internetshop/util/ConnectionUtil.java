package mate.academy.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.internetshop.exceptions.DataProcessingException;
import org.apache.log4j.Logger;

public class ConnectionUtil {
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("can't find mysql driver");
            throw new DataProcessingException("can't find mysql driver", e);
        }
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "1111");
        String url = "jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC";
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            LOGGER.error("can't establish the connection to db");
            throw new DataProcessingException("can't establish the connection to db", e);
        }
    }
}
