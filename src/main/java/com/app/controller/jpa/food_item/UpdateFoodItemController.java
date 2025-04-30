package com.app.controller.jpa.food_item;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.FoodItemValidator;
import com.app.dto.APIResponse;
import com.app.dto.FoodItemDTO;
import com.app.service.jpa.JPAFoodItemServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "update-food-item", value = "/update-food-item")
public class UpdateFoodItemController extends HttpServlet {
    private JPAFoodItemServices jpaFoodItemServices = new JPAFoodItemServices();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            if (!AuthUtils.isAdmin(request)) {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            FoodItemDTO foodItemDTO = ObjectMapperUtil.toObject(request.getReader(), FoodItemDTO.class);
            FoodItemValidator.validateOnUpdate(foodItemDTO);
            jpaFoodItemServices.update(foodItemDTO);
            sendResponse(response, null, Message.Common.RESOURCE_ADDED, null, HttpServletResponse.SC_OK);
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