package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.APIResponse;
import util.DBConnector;
import util.Validator;
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

/**
 * Sign up Servlet is used for creating an account for the user.
 *
 */

public class SignUpServlet extends HttpServlet {

    UserServices userServices;
    ObjectMapper objectMapper;
    Validator validate;
    APIResponse apiResponse;

    @Override
    public void init() throws ServletException {

        objectMapper = new ObjectMapper();
        validate = new Validator();
        apiResponse = new APIResponse();

        try {
            userServices = new UserServices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request used for receiving the Http request
     * @param response used for sending the Http response
     *
     * The doPost method is used by Servlet to receive the form data from the Sign-Up form and store it in the database
     * after verifying the data that has come.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        User user = objectMapper.readValue(br, User.class);

        if (request.getServletPath().equals("/customerSignUp")) {
            user.setRoleId(1002);
        } else if (request.getServletPath().equals("/deliveryPersonSignUp")) {
            user.setRoleId(1003);
        }

        try {
            if(validate.checkEmail(user.getEmail())) {
                if(validate.checkPassword(user.getPassword())){
                    if(validate.checkContactNo(user.getPhoneNumber())){
                        int flag = userServices.insertRecord(user);
                        if (flag == 1) {

                            response.setStatus(HttpServletResponse.SC_CREATED);
                            apiResponse.setMessage("User Account Created Successfully");
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        apiResponse.setMessage("Phone no. should be of length 10.");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    apiResponse.setMessage("Password should be between 8-40 characters and should be a " +
                            "combination of uppercase and lowercase letters, numbers, and special characters");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                apiResponse.setMessage("Invalid email address");
            }

            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(e.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
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
