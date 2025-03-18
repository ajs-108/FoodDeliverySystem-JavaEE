package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.APIResponse;
import common.Mapper;
import dao.impl.UserDAOImpl;
import dto.user_dto.UserDTO;
import dto.user_dto.UserLoginDTO;
import common.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import controller.validation.LoginValidator;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Login Servlet ensures that a valid user is accessing the application.
 */
public class LoginController extends HttpServlet {
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
        Mapper mapper = new Mapper();
        UserLoginDTO userLogin = objectMapper.readValue(request.getReader(), UserLoginDTO.class);
        User user = mapper.toUser(userLogin);
        try {
            LoginValidator.validateLogin(user);
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
        UserDAOImpl userDAO = new UserDAOImpl();
        UserDTO userDTO = mapper.toDTO(user);
        try {
            userDTO = mapper.toDTO(userDAO.getUser(user.getEmail()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.setAttribute("user", user.getEmail());
        session.setAttribute("userId", userDTO.getUserId());
        response.setStatus(HttpServletResponse.SC_OK);
        apiResponse.setMessage("You are logged in.");
        response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }
}
