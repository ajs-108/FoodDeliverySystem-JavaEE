package controller;

import common.AppConstant;
import mapper.UserMapper;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.util.ObjectMapperUtil;
import controller.validation.LoginValidator;
import dto.APIResponse;
import dto.user_dto.UserDTO;
import dto.user_dto.UserLoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.UserServices;

import java.io.IOException;

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
        response.setContentType(AppConstant.APPLICATION_JSON);
        UserMapper userMapper = new UserMapper();
        UserLoginDTO userLogin = ObjectMapperUtil.toObject(request.getReader(), UserLoginDTO.class);
        try {
            LoginValidator.validateLogin(userLogin);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        HttpSession session = request.getSession();
        UserServices userServices = new UserServices();
        UserDTO userDTO = null;
        try {
            userDTO = userMapper.toDTO(userServices.getUser(userLogin.getEmail()));
        } catch (DBException e) {
            e.printStackTrace();
        }
        session.setAttribute("user", userLogin.getEmail());
        assert userDTO != null;
        session.setAttribute("userId", userDTO.getUserId());
        sendResponse(response, "You are logged in", null, HttpServletResponse.SC_OK);
    }

    public void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}
