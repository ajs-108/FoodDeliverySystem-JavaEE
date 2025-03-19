package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import config.DBConnector;

public class DBInitializationController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        String url = getServletConfig().getInitParameter("url");
        String driver = getServletConfig().getInitParameter("driver");
        String username = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");
        DBConnector.getInstance(url, driver, username, password);
    }
}
