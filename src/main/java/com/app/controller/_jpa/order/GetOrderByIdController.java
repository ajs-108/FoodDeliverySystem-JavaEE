package com.app.controller._jpa.order;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.QueryParameterValidator;
import com.app.dto.APIResponse;
import com.app.dto.OrderDTO;
import com.app.dto.jpa.order.GetOrderDTO;
import com.app.dto.jpa.order.JPAOrderDTO;
import com.app.service.OrderServices;
import com.app.service.jpa.JPAOrderServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "get-order-by-id-controller", value = "/get-order-by-id-controller")
public class GetOrderByIdController extends HttpServlet {
    private JPAOrderServices orderServices = new JPAOrderServices();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            QueryParameterValidator.validate(request, "orderId");
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            GetOrderDTO orderDTO = orderServices.findById(orderId);
            sendResponse(response, null, null, orderDTO, HttpServletResponse.SC_OK);
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