package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.ShoppingCartDTO;
import com.app.service.FoodItemServices;
import com.app.service.ShoppingCartServices;
import com.app.service.UserServices;

public class ShoppingCartValidator {
    private static final UserServices userServices = new UserServices();
    private static final FoodItemServices foodItemServices = new FoodItemServices();
    private static final ShoppingCartServices shoppingCartServices = new ShoppingCartServices();
    private static final Validator validator = new Validator();

    private ShoppingCartValidator() {
    }

    public static void validateAddToCart(ShoppingCartDTO shoppingCartDTO) throws ApplicationException, DBException {
        if (shoppingCartDTO.getUserId() == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(shoppingCartDTO.getUserId()) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getFoodItemDTO().getFoodItemId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(shoppingCartDTO.getCartFoodItemsDTOList().get(0).getFoodItemDTO())) {
            throw new ApplicationException(Message.Common.RESOURCE_NOT_AVAILABLE);
        }
        if (shoppingCartServices.isFoodItemExists(shoppingCartDTO)
                && shoppingCartServices.isUserExists(shoppingCartDTO)) {
            throw new ApplicationException(Message.ShoppingCart.FOOD_ITEM_EXISTS);
        }
        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getQuantity() == 0) {
            throw new ApplicationException(Message.ShoppingCart.MIN_QUANTITY_VALUE);
        }
        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getQuantity() > 15) {
            throw new ApplicationException(Message.ShoppingCart.QUANTITY_VALUE);
        }
    }

    public static void validateUserId(int userId) throws ApplicationException, DBException {
        if (userId == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
    }

    public static void validateRemoval(int userId, String foodItemId) throws ApplicationException, DBException {
        if (userId == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (foodItemId == null || foodItemId.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isPositiveInteger(foodItemId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(Integer.parseInt(foodItemId))) {
            throw new ApplicationException(Message.Common.RESOURCE_NOT_AVAILABLE);
        }
    }

    public static void validateQuantityUpdate(ShoppingCartDTO shoppingCartDTO) throws ApplicationException,
            DBException {
        if (shoppingCartDTO.getUserId() == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(shoppingCartDTO.getUserId()) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getFoodItemDTO().getFoodItemId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(
                shoppingCartDTO.getCartFoodItemsDTOList().get(0).getFoodItemDTO().getFoodItemId())) {
            throw new ApplicationException(Message.Common.RESOURCE_NOT_AVAILABLE);
        }
        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getQuantity() > 15) {
            throw new ApplicationException(Message.ShoppingCart.QUANTITY_VALUE);
        }
    }
}
