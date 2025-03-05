package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.APIResponse;
import util.DBConnector;
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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Login Servlet ensures that a valid user is accessing the application.
 */

public class LoginServlet extends HttpServlet {

    Connection connection;
    UserServices userServices;
    ResultSet rs;
    APIResponse apiResponse;

    /**
     * The init method is used for initializing the servlet and instances that are to be used in this servlet.
     */
    @Override
    public void init() throws ServletException {

        apiResponse = new APIResponse();

        try {
            connection = DBConnector.getConnection();
            userServices = new UserServices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request is the http request sent by client.
     * @param response is the http response that is to be sent to client
     * The doPost method is used to get the login credentials through request and check if provided login credentials are correct or not.
     * If correct it gives access to the application.
     */

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
                    apiResponse.setMessage("You are logged in.");

                } else {

                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    apiResponse.setMessage("Login credentials are incorrect");

                }
            } else {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                apiResponse.setMessage("User account not found");

            }

            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    /**
     * The destroy method is called by server container to free up/close the resources as well as destroy the servlet.
     */

    @Override
    public void destroy() {

        try {
            DBConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
