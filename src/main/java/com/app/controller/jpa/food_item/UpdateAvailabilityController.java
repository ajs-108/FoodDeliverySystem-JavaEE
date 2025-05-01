package com.app.controller.jpa.food_item;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.JPAuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.common.validation.FoodItemValidator;
import com.app.controller.common.validation.QueryParameterValidator;
import com.app.dto.common.APIResponse;
import com.app.service.jpa.JPAFoodItemServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "update-availability", value = "/update-availability")
public class UpdateAvailabilityController extends HttpServlet {
    private JPAFoodItemServices jpaFoodItemServices = new JPAFoodItemServices();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            JPAuthUtils.checkAuthentication(request);
            if (!JPAuthUtils.isAdmin(request)) {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            String foodItemId = request.getParameter("foodItemId");
            String available = request.getParameter("available");
            QueryParameterValidator.validate(request, "foodItemId", "available");
            FoodItemValidator.validateOnAvailabilityUpdate(foodItemId, available);
            jpaFoodItemServices.updateAvailability(Integer.parseInt(foodItemId), Boolean.parseBoolean(available));
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