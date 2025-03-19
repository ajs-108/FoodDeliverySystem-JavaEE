package controller;

import common.AppConstant;
import common.Roles;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.util.ObjectMapperUtil;
import controller.validation.SignUpValidator;
import dto.APIResponse;
import dto.user_dto.UserDTO;
import dto.user_dto.UserSignUpDTO;
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
        HttpSession session = req.getSession(false);

        UserDTO user;
        try {
            user = userMapper.toDTO(userServices.getUser((String) session.getAttribute("user")));
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(resp, e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        sendResponse(resp, "Your details are updated Successfully", user, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(AppConstant.APPLICATION_JSON);
        UserSignUpDTO userDTO = ObjectMapperUtil.toObject(req.getReader(), UserSignUpDTO.class);
        HttpSession session = req.getSession(false);

        if (req.getServletPath().equals("/customer")) {
            userDTO.setRole(Roles.ROLE_CUSTOMER);
        } else if (req.getServletPath().equals("/deliveryPerson")) {
            userDTO.setRole(Roles.ROLE_DELIVERY_PERSON);
        } else if (req.getServletPath().equals("/admin")) {
            userDTO.setRole(Roles.ROLE_SUPER_ADMIN);
        }
        try {
            SignUpValidator.validate(userDTO);
            userServices.updateUser(userDTO, (int) session.getAttribute("userId"));
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(resp, e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(resp, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        sendResponse(resp, "Your details are updated Successfully", null, HttpServletResponse.SC_OK);
    }

    public void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}
