package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.APIResponse;
import entity.User;
import jakarta.servlet.http.HttpServletResponse;
import services.UserServices;
import validation.Validation;
import exception.ValidationException;

import java.io.IOException;
import java.sql.SQLException;

public class SignUp {

    public static void signUp(HttpServletResponse response, User user, ObjectMapper objectMapper) throws IOException {
        UserServices userServices = new UserServices();
        APIResponse apiResponse = new APIResponse();

        try {
            Validation.validateSignUp(user);
            apiResponse = userServices.saveUser(user);
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
        response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
