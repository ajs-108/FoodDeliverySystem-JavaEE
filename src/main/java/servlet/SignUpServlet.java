package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbUtil.DBConnector;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {

    UserServices userServices;

    @Override
    public void init() throws ServletException {
        try {
            userServices = new UserServices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        User user = objectMapper.readValue(br, User.class);

        if (request.getServletPath().equals("/customerSignUp")) {
            user.setRoleId(1002);
        } else if (request.getServletPath().equals("/deliveryPersonSignUp")) {
            user.setRoleId(1003);
        }

        try {
            int flag = userServices.insertRecord(user);
            if (flag == 1) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"success\": \"User Created Successfully\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
