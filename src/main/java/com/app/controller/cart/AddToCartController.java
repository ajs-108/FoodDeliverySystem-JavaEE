package com.app.controller.cart;

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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "addToCart",
        value = "/addToCart")
public class AddToCartController extends HttpServlet {
    private ShoppingCartServices shoppingCartServices = new ShoppingCartServices();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            ShoppingCartDTO shoppingCartDTO = ObjectMapperUtil.toObject(request.getReader(), ShoppingCartDTO.class);
            UserDTO userDTO = AuthUtils.getCurrentUser(request);
            shoppingCartDTO.setUserId(userDTO.getUserId());
            ShoppingCartValidator.validateAddToCart(shoppingCartDTO);
            shoppingCartServices.addFoodItem(shoppingCartDTO);
            sendResponse(response, null, Message.ShoppingCart.FOOD_ITEM_ADDED, null, HttpServletResponse.SC_OK);
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