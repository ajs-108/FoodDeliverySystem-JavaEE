package com.app.controller;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.enums.OrderStatus;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.common.util.QueryParameterUtil;
import com.app.controller.validation.OrderValidator;
import com.app.dto.APIResponse;
import com.app.dto.OrderDTO;
import com.app.dto.UserDTO;
import com.app.service.OrderServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "updateOrderStatus", value = "/updateOrderStatus")
public class UpdateOrderStatus extends HttpServlet {
    private OrderServices orderServices = new OrderServices();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            QueryParameterUtil.checkQueryParameters(request, "orderId", "orderStatus");
            String orderId = request.getParameter("orderId");
            OrderStatus orderStatus = OrderStatus.toEnum(request.getParameter("orderStatus"));
            OrderValidator.validateUpdateStatus(orderId, orderStatus);
            orderServices.updateStatus(Integer.parseInt(orderId), orderStatus);
            sendResponse(response, null, Message.Order.ORDER_STATUS, null, HttpServletResponse.SC_OK);
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

    public void sendResponse(HttpServletResponse response, String techMessage, String message, Object data, int status) throws IOException {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.setStatus(status);
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}