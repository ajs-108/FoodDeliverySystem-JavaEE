package com.app.controller;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.ChangePasswordValidator;
import com.app.dto.APIResponse;
import com.app.dto.FoodItemDTO;
import com.app.dto.UserDTO;
import com.app.service.UserServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(
        name = "Changepassword",
        value = "/changePassword")
public class ChangePasswordController extends HttpServlet {
    private UserServices userServices = new UserServices();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            UserDTO userDTO = ObjectMapperUtil.toObject(request.getReader(), UserDTO.class);
            ChangePasswordValidator.validatePassword(userDTO);
            userServices.changePassword(userDTO);
            sendResponse(response, null, Message.User.PASSWORD_UPDATED, null, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, null, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static void sendResponse(HttpServletResponse response, String techMessage, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}