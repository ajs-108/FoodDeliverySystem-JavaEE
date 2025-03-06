package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserServices;
import util.APIResponse;

import java.io.IOException;
import java.sql.SQLException;

public class UserControllerServlet extends HttpServlet {

    UserServices userServices;
    ObjectMapper objectMapper;
    APIResponse apiResponse;

    @Override
    public void init() throws ServletException {

        objectMapper = new ObjectMapper();
        apiResponse = new APIResponse();

        try {
            userServices = new UserServices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        int roleId = 0;

        if (req.getServletPath().equals("/customer")) {
            roleId = 1002;
        } else if (req.getServletPath().equals("/deliveryPerson")) {
            roleId = 1003;
        } else if (req.getServletPath().equals("/admin")) {
            roleId = 1001;
        }

        try {
            apiResponse.setData(userServices.fetchAllRecords(roleId));

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        System.out.println(apiResponse);
        resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));

    }
}
