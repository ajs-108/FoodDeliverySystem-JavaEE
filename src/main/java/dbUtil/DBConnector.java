package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {

    public static final String url = "jdbc:mysql://localhost:3306/food_delivery_db";
    public static Connection connection;

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Class Loaded Successfully");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        connection = DriverManager.getConnection(url, "root", "password123#");
        System.out.println("Connection Established...");
        return connection;
    }

    public static void closeConnection() throws SQLException {

        if (connection != null) {
            connection.close();
            System.out.println("Connection closed Successfully...");
        }

    }

    public static void main(String[] args) throws SQLException {


        Connection connection = getConnection();
        ResultSet rs = connection.prepareStatement("Select * from user_role").executeQuery();
        while (rs.next()) {
            System.out.println("role id: " + rs.getInt("role_id"));
            System.out.println("role: " + rs.getString("user_role"));
        }

        closeConnection();
    }
}
