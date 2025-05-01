package com.app.controller.common.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.jdbc.ShoppingCartDTO;
import com.app.dto.jpa.JPACartDTO;
import com.app.service.common.FoodItemServices;
import com.app.service.jdbc.ShoppingCartServices;
import com.app.service.jdbc.UserServices;

public class ShoppingCartValidator {
    private static final UserServices userServices = new UserServices();
    private static final FoodItemServices foodItemServices = new FoodItemServices();
    private static final ShoppingCartServices shoppingCartServices = new ShoppingCartServices();
    private static final Validator validator = new Validator();

    private ShoppingCartValidator() {
    }

    private static void validateUserId(int userId) throws ApplicationException, DBException {
        if (userId == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
    }

    public static void validateAddToCart(ShoppingCartDTO shoppingCartDTO) throws ApplicationException, DBException {
        validateUserId(shoppingCartDTO.getUserId());
        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getFoodItemDTO().getFoodItemId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(shoppingCartDTO.getCartFoodItemsDTOList().get(0)
                .getFoodItemDTO().getFoodItemId())) {
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

    public static void validateAddToCart(JPACartDTO shoppingCartDTO) throws ApplicationException, DBException {
        validateUserId(shoppingCartDTO.getUser().getUserId());
        if (shoppingCartDTO.getFoodItem().getFoodItemId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(shoppingCartDTO.getFoodItem().getFoodItemId())) {
            throw new ApplicationException(Message.Common.RESOURCE_NOT_AVAILABLE);
        }
//        TODO:Check this out and change it
//        if (shoppingCartServices.isFoodItemExists(shoppingCartDTO)
//                && shoppingCartServices.isUserExists(shoppingCartDTO)) {
//            throw new ApplicationException(Message.ShoppingCart.FOOD_ITEM_EXISTS);
//        }
//        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getQuantity() == 0) {
//            throw new ApplicationException(Message.ShoppingCart.MIN_QUANTITY_VALUE);
//        }
//        if (shoppingCartDTO.getCartFoodItemsDTOList().get(0).getQuantity() > 15) {
//            throw new ApplicationException(Message.ShoppingCart.QUANTITY_VALUE);
//        }
    }

    public static void validateShowCart(int userId) throws ApplicationException, DBException {
        validateUserId(userId);
        if (shoppingCartServices.isCartEmpty(userId)) {
            throw new ApplicationException(Message.ShoppingCart.CART_IS_EMPTY);
        }
    }

    public static void validateRemoval(int userId, String foodItemId) throws ApplicationException, DBException {
        validateUserId(userId);
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

    public static void validateQuantityUpdate(JPACartDTO cartDTO) throws ApplicationException,
            DBException {
        if (cartDTO.getUser().getUserId() == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(cartDTO.getUser().getUserId()) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (cartDTO.getFoodItem().getFoodItemId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(
                cartDTO.getFoodItem().getFoodItemId())) {
            throw new ApplicationException(Message.Common.RESOURCE_NOT_AVAILABLE);
        }
        if (cartDTO.getQuantity() > 15) {
            throw new ApplicationException(Message.ShoppingCart.QUANTITY_VALUE);
        }
    }
}
