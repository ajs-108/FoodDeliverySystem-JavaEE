package com.app.controller.food_item;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.FileUtil;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.validation.FoodItemValidator;
import com.app.dto.APIResponse;
import com.app.dto.FoodItemDTO;
import com.app.service.FoodItemServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet(
        name = "Addfooditem",
        value = "/addFoodItem")
@MultipartConfig
public class AddFoodItemController extends HttpServlet {
    private FoodItemServices foodItemServices = new FoodItemServices();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            if (!AuthUtils.isAdmin(request)) {
                throw new ApplicationException(Message.Error.ACCESS_DENIED);
            }
            Part foodItemPart = request.getPart("foodItem");
            Part imagePart = request.getPart("image");
            FoodItemDTO foodItemDTO = ObjectMapperUtil.toObject(foodItemPart.getInputStream(), FoodItemDTO.class);
            foodItemDTO.setImagePath(FileUtil.getFilePath(AppConstant.FOOD_ITEM_IMAGE_FOLDER, imagePart));
            FoodItemValidator.validateFoodItem(foodItemDTO);
            foodItemServices.createFoodItem(foodItemDTO);
            sendResponse(response, null, Message.Common.RESOURCE_ADDED, null, HttpServletResponse.SC_CREATED);
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
