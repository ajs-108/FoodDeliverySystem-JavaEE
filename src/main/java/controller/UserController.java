package controller;

import common.AppConstant;
import common.Message;
import common.enums.Roles;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.util.ObjectMapperUtil;
import controller.validation.UserUpdateValidator;
import controller.validation.Validator;
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

public class UserController extends HttpServlet {
    private UserServices userServices;
    private UserMapper userMapper;

    @Override
    public void init() throws ServletException {
        userServices = new UserServices();
        userMapper = new UserMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(AppConstant.APPLICATION_JSON);
        HttpSession session;
        UserDTO user;
        try {
            session = req.getSession(false);
            if (session == null) {
                throw new ApplicationException(Message.Error.UNAUTHORIZED_ACCESS);
            }
            user = userMapper.toDTO(userServices.getUser((String) session.getAttribute("user")));
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(resp, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(resp, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(resp, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        sendResponse(resp, null, user, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(AppConstant.APPLICATION_JSON);
        UserDTO userDTO = ObjectMapperUtil.toObject(req.getReader(), UserDTO.class);
        HttpSession session;

        try {
            session = req.getSession(false);
            if (session == null) {
                throw new ApplicationException(Message.Error.UNAUTHORIZED_ACCESS);
            }
            if (req.getServletPath().equals("/customer") &&
                    (int) session.getAttribute(AppConstant.ROLE_ID) == Roles.ROLE_CUSTOMER.getId()) {
                userDTO.setRole(Roles.ROLE_CUSTOMER);
            } else if (req.getServletPath().equals("/deliveryPerson") &&
                    (int) session.getAttribute(AppConstant.ROLE_ID) == Roles.ROLE_DELIVERY_PERSON.getId()) {
                userDTO.setRole(Roles.ROLE_DELIVERY_PERSON);
            } else if (req.getServletPath().equals("/admin") &&
                    (int) session.getAttribute(AppConstant.ROLE_ID) == Roles.ROLE_SUPER_ADMIN.getId()) {
                userDTO.setRole(Roles.ROLE_SUPER_ADMIN);
            } else {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            UserUpdateValidator.validate(userDTO);
            userServices.updateUser(userDTO, (int) session.getAttribute("userId"));
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(resp, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(resp, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(resp, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        sendResponse(resp, Message.User.USER_INFO_UPDATES, null, HttpServletResponse.SC_OK);
    }

    public void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}
