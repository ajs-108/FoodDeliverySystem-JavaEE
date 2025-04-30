package com.app.controller._jpa.order;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.enums.OrderStatus;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.OrderValidator;
import com.app.controller.validation.QueryParameterValidator;
import com.app.dto.APIResponse;
import com.app.service.OrderServices;
import com.app.service.jpa.JPAOrderServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "update-order-status", value = "/update-order-status")
public class UpdateOrderStatusController extends HttpServlet {
    private JPAOrderServices orderServices = new JPAOrderServices();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            QueryParameterValidator.validate(request, "orderId", "orderStatus");
            String orderId = request.getParameter("orderId");
            String orderStatus = request.getParameter("orderStatus");
            OrderValidator.validateUpdateStatus(orderId, orderStatus);
            orderServices.updateOrderStatus(Integer.parseInt(orderId), OrderStatus.toEnum(orderStatus));
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