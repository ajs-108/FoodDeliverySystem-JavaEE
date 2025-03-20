package controller;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.APIResponse;
import common.enums.Roles;
import common.util.ObjectMapperUtil;
import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserServices;

import java.io.IOException;

/**
 * Sign up Servlet is used for creating an account for the user.
 */
public class CustomerSignUpController extends HttpServlet {
    /**
     * The doPost method is used by Servlet to receive the form data from the Sign-Up form and store it in the database
     * after verifying the data that has come.
     *
     * @param request  used for receiving the Http request
     * @param response used for sending the Http response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        UserDTO userSignUpDTO = ObjectMapperUtil.toObject(request.getReader(), UserDTO.class);
        userSignUpDTO.setRole(Roles.ROLE_CUSTOMER);
        try {
            UserServices.signUp(userSignUpDTO);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        sendResponse(response, Message.User.USER_REGISTERED, null, HttpServletResponse.SC_CREATED);
    }

    public static void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}
