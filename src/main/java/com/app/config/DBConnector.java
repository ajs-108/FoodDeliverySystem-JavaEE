package com.app.config;

import com.app.common.Message;
import com.app.common.exception.DBException;

import java.sql.*;

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

    public static void resourceCloser(Statement statement, ResultSet resultSet, Connection connection) throws DBException {
        try {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
