package com.app.controller;

import com.app.config.DBConnector;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(
        name = "DBinitializer",
        value = "/dbInitializer",
        loadOnStartup = 0)
public class DBInitializationController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            ServletContext context = getServletConfig().getServletContext();
            String url = context.getInitParameter("url");
            String driver = context.getInitParameter("driver");
            String username = context.getInitParameter("user");
            String password = context.getInitParameter("password");
            DBConnector.getInstance(url, driver, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
