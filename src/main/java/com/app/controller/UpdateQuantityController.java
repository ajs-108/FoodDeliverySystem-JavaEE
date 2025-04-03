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
import com.app.dto.UserDTO;
import com.app.service.ShoppingCartServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Update Quantity", value = "/updateQuantity")
public class UpdateQuantityController extends HttpServlet {
    private ShoppingCartServices shoppingCartServices = new ShoppingCartServices();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            UserDTO userDTO = AuthUtils.getCurrentUser(request);
            ShoppingCartDTO shoppingCartDTO = ObjectMapperUtil.toObject(request.getReader(), ShoppingCartDTO.class);
            if(shoppingCartDTO.getCartFoodItemsDTOList().get(0).getQuantity() == 0) {
                shoppingCartServices.removeFoodItem(userDTO.getUserId(),
                        shoppingCartDTO.getCartFoodItemsDTOList().get(0).getFoodItemDTO().getFoodItemId());
                sendResponse(response, null, Message.ShoppingCart.FOOD_ITEM_REMOVED, null, HttpServletResponse.SC_OK);
                return;
            }
            shoppingCartDTO.setUserId(userDTO.getUserId());
            ShoppingCartValidator.validateQuantityUpdate(shoppingCartDTO);
            shoppingCartServices.updateQuantity(shoppingCartDTO);
            sendResponse(response, null, Message.ShoppingCart.QUANTITY_UPDATED, null, HttpServletResponse.SC_OK);
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