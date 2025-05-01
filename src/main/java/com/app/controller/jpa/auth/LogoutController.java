package com.app.controller.jpa.auth;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.util.AuthUtils;
import com.app.common.util.JPAuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.dto.common.APIResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            HttpSession session = JPAuthUtils.getCurrentUserSession(request);
            if (session.getAttribute("user") == null) {
                throw new ApplicationException(Message.Error.UNAUTHORIZED_ACCESS);
            }
            session.invalidate();
            sendResponse(response, null, Message.User.LOGOUT, null, HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, null, e.getMessage(), null, HttpServletResponse.SC_OK);
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
}