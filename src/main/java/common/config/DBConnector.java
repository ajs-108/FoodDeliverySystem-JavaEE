package common.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DBConnector class is used for establishing the connection with MySQL database food_delivery_db
 */
public class DBConnector {
    private static DBConnector instance;
    private String url;
    private String driver;
    private String username;
    private String password;

    private DBConnector(String url, String driver, String username, String password) {
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    public static DBConnector getInstance(String url, String driver, String username, String password) {
        if (instance == null) {
            instance = new DBConnector(url, driver, username, password);
        }
        return instance;
    }

    public static DBConnector getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
