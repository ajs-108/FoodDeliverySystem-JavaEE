package com.app.controller;

import com.app.config.DBConnector;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class DBInitializationController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletConfig().getServletContext();
        String url = context.getInitParameter("url");
        String driver = context.getInitParameter("driver");
        String username = context.getInitParameter("user");
        String password = context.getInitParameter("password");
        DBConnector.getInstance(url, driver, username, password);
    }
}
