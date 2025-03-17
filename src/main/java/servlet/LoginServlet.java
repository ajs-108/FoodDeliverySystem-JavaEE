package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import common.APIResponse;
import validation.Validation;
import exception.ValidationException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Login Servlet ensures that a valid user is accessing the application.
 */

public class LoginServlet extends HttpServlet {
    /**
     * The doPost method is used to get the login credentials through request and check if provided login credentials are correct or not.
     * If correct it gives access to the application.
     *
     * @param request  is the http request sent by client.
     * @param response is the http response that is to be sent to client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        APIResponse apiResponse = new APIResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(request.getReader(), User.class);

        try {
            Validation.validateLogin(user);
        } catch (ValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user.getEmail());
        response.setStatus(HttpServletResponse.SC_OK);
        apiResponse.setMessage("You are logged in.");
        response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
