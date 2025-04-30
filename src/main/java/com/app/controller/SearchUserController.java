package com.app.controller;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.enums.Roles;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.QueryParameterValidator;
import com.app.dto.APIResponse;
import com.app.dto.UserDTO;
import com.app.service.UserServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "searchUserController",
        value = "/searchUserController")
public class SearchUserController extends HttpServlet {
    private UserServices userServices = new UserServices();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);

        try {
            AuthUtils.checkAuthentication(request);
            String searchString = request.getParameter("searchStr");
            QueryParameterValidator.validate(request, "searchStr");
            List<UserDTO> users = userServices.getAllUsers(Roles.ROLE_CUSTOMER.getRoleId());
            List<UserDTO> usersAfterSearch = new ArrayList<>();
            for (UserDTO user : users) {
                if (findString(user, searchString)) {
                    usersAfterSearch.add(user);
                }
            }
            sendResponse(response, null, null, usersAfterSearch, HttpServletResponse.SC_OK);
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

    public void sendResponse(HttpServletResponse response, String techMessage, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }

    public boolean findString(UserDTO userDTO, String str) {
        List<String> userVariables = new ArrayList<>();
        userVariables.add(Integer.toString(userDTO.getUserId()));
        userVariables.add(userDTO.getFirstName());
        userVariables.add(userDTO.getLastName());
        userVariables.add(userDTO.getFirstName());
        userVariables.add(userDTO.getEmail());
        userVariables.add(userDTO.getPhoneNumber());
        userVariables.add(userDTO.getAddress());
        userVariables.add(userDTO.getCreatedOn().toString());
        if (userDTO.getUpdatedOn() != null) {
            userVariables.add(userDTO.getUpdatedOn().toString());
        }
        userVariables.add(userDTO.getRole().name());
        userVariables.add(userDTO.getAccountStatus().name());
        for (String userVariable : userVariables) {
            if (userVariable.contains(str)) {
                return true;
            }
        }
        return false;
    }
}
