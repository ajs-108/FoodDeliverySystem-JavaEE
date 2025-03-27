package com.app.controller;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.ShoppingCartValidator;
import com.app.dto.APIResponse;
import com.app.dto.ShoppingCartDTO;
import com.app.service.ShoppingCartServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "removeFromCart", value = "/removeFromCart")
public class RemoveFromCartController extends HttpServlet {
    private ShoppingCartServices shoppingCartServices = new ShoppingCartServices();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            int userId = Integer.parseInt(request.getParameter("userId"));
            int foodItemId = Integer.parseInt(request.getParameter("foodItemId"));
            ShoppingCartValidator.validateRemoval(userId, foodItemId);
            shoppingCartServices.removeFoodItem(userId, foodItemId);
            sendResponse(response, null, Message.ShoppingCart.FOOD_ITEM_REMOVED, null, HttpServletResponse.SC_OK);
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