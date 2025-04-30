package com.app.controller._jpa.order;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.enums.OrderStatus;
import com.app.common.exception.ApplicationException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.dto.APIResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "getAllOrderStatus", value = "/getAllOrderStatus")
public class GetAllOrderStatusController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            sendResponse(response, null, null, OrderStatus.getAllOrderStatus(), HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, null, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void sendResponse(HttpServletResponse response, String techMessage, String message, Object data, int status) throws IOException {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.setStatus(status);
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }

}