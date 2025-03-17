package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Roles;
import util.SignUp;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Sign up Servlet is used for creating an account for the user.
 */
public class CustomerSignUpServlet extends HttpServlet {

    /**
     *The doPost method is used by Servlet to receive the form data from the Sign-Up form and store it in the database
     *after verifying the data that has come.
     * @param request used for receiving the Http request
     * @param response used for sending the Http response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(request.getReader(), User.class);

        user.setRole(Roles.ROLE_CUSTOMER);
        SignUp.signUp(response, user, objectMapper);
    }
}
