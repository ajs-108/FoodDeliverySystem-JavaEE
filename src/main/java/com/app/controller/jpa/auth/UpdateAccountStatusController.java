package com.app.controller.jpa.auth;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.enums.AccountStatus;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.JPAuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.common.validation.QueryParameterValidator;
import com.app.dto.common.APIResponse;
import com.app.service.jpa.JPAUserServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "update-account-status", value = "/update-account-status")
public class UpdateAccountStatusController extends HttpServlet {
    private JPAUserServices userServices = new JPAUserServices();

    public static void sendResponse(HttpServletResponse response, String techMessage, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            JPAuthUtils.checkAuthentication(request);
            if (!JPAuthUtils.isAdmin(request)) {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            QueryParameterValidator.validate(request, "userId", "accountStatus");
            String userId = request.getParameter("userId");
            String accountStatus = request.getParameter("accountStatus");
//            UserValidator.validateAccountStatusUpdate(userId, accountStatus);TODO:check this
            userServices.updateAccountStatus(Integer.parseInt(userId), AccountStatus.toEnum(accountStatus));
            sendResponse(response, null, Message.User.STATUS_UPDATED, null, HttpServletResponse.SC_OK);
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
}