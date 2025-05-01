package com.app.controller.jpa.cart;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.JPAuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.common.validation.QueryParameterValidator;
import com.app.controller.common.validation.ShoppingCartValidator;
import com.app.dto.common.APIResponse;
import com.app.dto.jdbc.UserDTO;
import com.app.dto.jpa.JPACartDTO;
import com.app.model.jpa.JPACart;
import com.app.service.jdbc.ShoppingCartServices;
import com.app.service.jpa.JPACartServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "remove-from-cart", value = "/remove-from-cart")
public class RemoveFromCartController extends HttpServlet {
    private JPACartServices cartServices = new JPACartServices();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            JPAuthUtils.checkAuthentication(request);
            JPACartDTO cartDTO = ObjectMapperUtil.toObject(request.getReader(), JPACartDTO.class);
            //ToDo: make a validation for this
            cartServices.removeFoodItem(cartDTO);
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