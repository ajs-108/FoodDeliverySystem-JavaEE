package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbUtil.DBConnector;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.UserServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    Connection connection;
    UserServices userServices;
    ResultSet rs;

    @Override
    public void init() throws ServletException {
        try {
            connection = DBConnector.getConnection();
            userServices = new UserServices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        User user = objectMapper.readValue(br, User.class);

        try {
            rs = userServices.fetchRecordByEmail(user.getEmail());

            if (rs.next() && rs.getString("email").equals(user.getEmail())) {
                if (rs.getString("password_").equals(user.getPassword())) {

                    HttpSession session = request.getSession();

                    session.setAttribute("user", user.getEmail());

                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"success\": \"Logged in\"}");
                } else {

                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Either email or password is incorrect\"}");
                }
            } else {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"No such user found\"}");
            }

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    public void destroy() {

        try {
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
