package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import common.config.DBConnector;

import java.sql.SQLException;

public class DBConnectionController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        String url = getServletConfig().getInitParameter("url");
        String driver = getServletConfig().getInitParameter("driver");
        String username = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");
        DBConnector instance = DBConnector.getInstance(url, driver, username, password);
        try {
            instance.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
