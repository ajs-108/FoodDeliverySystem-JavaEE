package com.app.controller;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.enums.OrderStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.common.util.QueryParameterUtil;
import com.app.controller.validation.OrderValidator;
import com.app.dto.APIResponse;
import com.app.service.OrderServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.management.relation.Role;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "assignDeliveryPerson", value = "/assignDeliveryPerson")
public class AssignDeliveryPersonController extends HttpServlet {
    private OrderServices orderServices = new OrderServices();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            if (!AuthUtils.isAdmin(request)) {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            QueryParameterUtil.checkQueryParameters(request, "orderId", "deliveryPersonId", "role");
            String orderId = request.getParameter("orderId");
            String deliveryPersonId = request.getParameter("deliveryPersonId");
            Roles roles = Roles.toEnum(request.getParameter("role"));
            OrderValidator.validateAssignDeliveryPerson(orderId, deliveryPersonId, roles);
            orderServices.assignDeliveryPerson(Integer.parseInt(orderId), Integer.parseInt(deliveryPersonId));
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