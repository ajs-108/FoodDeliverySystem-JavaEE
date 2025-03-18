package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.validation.LoginValidator;
import controller.validation.SignUpValidator;
import dto.APIResponse;
import common.exception.ValidationException;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class SignUp {
    public static void signUp(HttpServletResponse response, User user, ObjectMapper objectMapper) throws IOException {
        UserServices userServices = new UserServices();
        APIResponse apiResponse = new APIResponse();
        try {
            SignUpValidator.validate(user);
            userServices.saveUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        } catch (ValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        apiResponse.setMessage("User created successfully");
        response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
