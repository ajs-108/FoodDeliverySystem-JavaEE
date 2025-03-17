package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Roles;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserServices;
import common.APIResponse;
import validation.Validation;
import exception.ValidationException;

import java.io.IOException;
import java.sql.SQLException;

public class UserController extends HttpServlet {
    private UserServices userServices;
    private ObjectMapper objectMapper;
    private APIResponse apiResponse;

    @Override
    public void init() throws ServletException {
        objectMapper = new ObjectMapper();
        apiResponse = new APIResponse();
        userServices = new UserServices();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        int roleId = 0;

        if (req.getServletPath().equals("/customer")) {
            roleId = Roles.ROLE_CUSTOMER.getId();
        } else if (req.getServletPath().equals("/deliveryPerson")) {
            roleId = Roles.ROLE_DELIVERY_PERSON.getId();
        } else if (req.getServletPath().equals("/admin")) {
            roleId = Roles.ROLE_SUPER_ADMIN.getId();
        }

        try {
            apiResponse.setData(userServices.getAllUsers(roleId));
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        }
        resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        User user = objectMapper.readValue(req.getReader(), User.class);
        int flag;

        if (req.getServletPath().equals("/customer")) {
            user.setRole(Roles.ROLE_CUSTOMER);
        } else if (req.getServletPath().equals("/deliveryPerson")) {
            user.setRole(Roles.ROLE_DELIVERY_PERSON);
        } else if (req.getServletPath().equals("/admin")) {
            user.setRole(Roles.ROLE_SUPER_ADMIN);
        }

        try {
            Validation.validateSignUp(user);
            userServices.modifyUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        } catch (ValidationException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        apiResponse.setMessage("Account Details Updated Successfully");
        resp.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
