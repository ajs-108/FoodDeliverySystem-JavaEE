package controller;

import common.AppConstant;
import common.AuthUtils;
import common.Message;
import common.enums.Roles;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.util.ObjectMapperUtil;
import dto.APIResponse;
import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserServices;

import java.io.IOException;
import java.util.List;

public class ListAllUsersController extends HttpServlet {
    private UserServices userServices = new UserServices();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            int roleId = 0;
            if (request.getServletPath().equals("/customers")) {
                roleId = Roles.ROLE_CUSTOMER.getRoleId();
            } else if (request.getServletPath().equals("/deliveryPersons")) {
                roleId = Roles.ROLE_DELIVERY_PERSON.getRoleId();
            }
            AuthUtils.checkAuthentication(request);
            if (!AuthUtils.isAdmin(request)) {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            List<UserDTO> listOfUsers = userServices.getAllUsers(roleId);
            sendResponse(response, null, listOfUsers, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
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
