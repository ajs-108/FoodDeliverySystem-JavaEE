package controller;

import common.AppConstant;
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
import jakarta.servlet.http.HttpSession;
import mapper.UserMapper;
import service.UserServices;

import java.io.IOException;
import java.util.List;

public class ListAllUsersController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        UserMapper userMapper = new UserMapper();
        UserServices userServices = new UserServices();
        List<UserDTO> listOfUsers;
        HttpSession session;
        int roleId = 0;

        if (request.getServletPath().equals("/customers")) {
            roleId = Roles.ROLE_CUSTOMER.getId();
        } else if (request.getServletPath().equals("/deliveryPersons")) {
            roleId = Roles.ROLE_DELIVERY_PERSON.getId();
        }
        try {
            session = request.getSession(false);
            if (session == null) {
                throw new ApplicationException(Message.Error.UNAUTHORIZED_ACCESS);
            }
            if ((int) session.getAttribute(AppConstant.ROLE_ID) != Roles.ROLE_SUPER_ADMIN.getId()) {
                throw new ServletException(Message.Error.ACCESS_DENIED);
            }
            listOfUsers = userServices.getAllUsers(roleId)
                    .stream()
                    .map(userMapper::toDTO)
                    .toList();
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
        sendResponse(response, null, listOfUsers, HttpServletResponse.SC_OK);
    }

    public void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}
