package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.ShoppingCartDTO;
import com.app.service.FoodItemServices;
import com.app.service.UserServices;

public class ShoppingCartValidator {
    private static UserServices userServices = new UserServices();
    private static FoodItemServices foodItemServices = new FoodItemServices();

    public static void validateShoppingCart(ShoppingCartDTO shoppingCartDTO) throws ApplicationException, DBException {
        if (shoppingCartDTO.getUserId() == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(shoppingCartDTO.getUserId()) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (shoppingCartDTO.getFoodItemDTO().getFoodItemId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!foodItemServices.isFoodItemExists(shoppingCartDTO.getFoodItemDTO())) {
            throw new ApplicationException(Message.FoodItem.FOOD_ITEM_DOES_NOT_EXISTS);
        }
        if (shoppingCartDTO.getQuantity() == 0) {
            throw new ApplicationException(Message.ShoppingCart.QUANTITY);
        }
    }

    public static void validateUserId(int userId) throws ApplicationException, DBException {
        if (userId == 0 || userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
    }

    public static void validateRemoval(int userId, int foodItemId) throws ApplicationException, DBException {
        if (userId == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (foodItemId == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!foodItemServices.isFoodItemExists(foodItemId)) {
            throw new ApplicationException(Message.FoodItem.FOOD_ITEM_DOES_NOT_EXISTS);
        }
    }
}
