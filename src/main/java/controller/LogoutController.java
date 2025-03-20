package controller;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.util.ObjectMapperUtil;
import dto.APIResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        HttpSession session;

        try {
            session = request.getSession(false);
            if (session.getAttribute("user") != null) {
                session.invalidate();
            } else {
                throw new ApplicationException(Message.Error.UNAUTHORIZED_ACCESS);
            }
        } catch(ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, HttpServletResponse.SC_OK);
        } catch(Exception e) {
            e.printStackTrace();
            sendResponse(response, Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        sendResponse(response, Message.User.LOGOUT, null, HttpServletResponse.SC_OK);
    }
    public void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }
}
