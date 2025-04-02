package com.app.controller;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.FoodItemValidator;
import com.app.controller.validation.QueryParameterValidator;
import com.app.dto.APIResponse;
import com.app.service.FoodItemServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "updateAvailability", value = "/updateAvailability")
public class UpdateFoodItemAvailability extends HttpServlet {
    private FoodItemServices foodItemServices = new FoodItemServices();

    public static void sendResponse(HttpServletResponse response, String techMessage, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.getWriter().println(ObjectMapperUtil.toString(apiResponse));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            AuthUtils.checkAuthentication(request);
            if (!AuthUtils.isAdmin(request)) {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            QueryParameterValidator.validateQueryParameters(request, "foodItemId", "available");
            String foodItemId = request.getParameter("foodItemId");
            String isAvailable = request.getParameter("available");
            FoodItemValidator.validateOnAvailabilityUpdate(foodItemId, isAvailable);
            foodItemServices.updateFoodItemAvailability(Integer.parseInt(foodItemId), Boolean.parseBoolean(isAvailable));
            sendResponse(response, null, Message.FoodItem.FOOD_ITEM_UPDATED, null, HttpServletResponse.SC_OK);
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
}