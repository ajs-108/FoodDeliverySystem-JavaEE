package com.app.controller.jpa.order;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.JPAuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.dto.common.APIResponse;
import com.app.dto.jpa.JPAUserDTO;
import com.app.dto.jpa.order.CartAndOrderDTO;
import com.app.service.jpa.JPAOrderServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "place-order", value = "/place-order")
public class PlaceOrderController extends HttpServlet {
    private JPAOrderServices orderServices = new JPAOrderServices();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            JPAuthUtils.checkAuthentication(request);
            JPAUserDTO userDTO = JPAuthUtils.getCurrentUser(request);
            CartAndOrderDTO cartAndOrder = ObjectMapperUtil.toObject(request.getReader(), CartAndOrderDTO.class);
            cartAndOrder.getCart().setUser(userDTO);
            cartAndOrder.getOrder().setUser(userDTO);
//            OrderValidator.validatePlaceOrder(userDTO.getUserId(), cartAndOrder.getOrder());TODO:check this
            orderServices.save(cartAndOrder.getOrder(), cartAndOrder.getCart());
            sendResponse(response, null, Message.Order.PLACE_ORDER, null, HttpServletResponse.SC_OK);
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