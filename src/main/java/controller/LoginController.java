package controller;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.util.ObjectMapperUtil;
import controller.validation.LoginValidator;
import dto.APIResponse;
import dto.UserDTO;
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
    private UserServices userServices = new UserServices();

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
        try {
            UserDTO userLogin = ObjectMapperUtil.toObject(request.getReader(), UserDTO.class);
            LoginValidator.validateLogin(userLogin);
            UserDTO userDTO = userServices.getUser(userLogin.getEmail());
            HttpSession session = request.getSession();
            session.setAttribute("user", userDTO);
            sendResponse(response, Message.User.LOGIN, null, HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}
