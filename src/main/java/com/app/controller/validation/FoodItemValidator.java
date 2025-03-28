package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.FoodItemDTO;
import com.app.service.FoodItemServices;

public class FoodItemValidator {
    private static final FoodItemServices foodItemServices = new FoodItemServices();
    private static final int FOOD_NAME_LENGTH = 30;
    private static final int FOOD_DESCRIPTION_LENGTH = 30;

    public static void validateFoodItem(FoodItemDTO foodItemDTO) throws ApplicationException, DBException {
        if (foodItemDTO.getFoodName() == null || foodItemDTO.getFoodName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (foodItemDTO.getFoodName().length() > FOOD_NAME_LENGTH) {
            throw new ApplicationException(Message.FoodItem.FOOD_NAME_LENGTH);
        }
        if (foodItemDTO.getFoodDescription() == null || foodItemDTO.getFoodDescription().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (foodItemDTO.getFoodName().length() > FOOD_DESCRIPTION_LENGTH) {
            throw new ApplicationException(Message.FoodItem.FOOD_DESCRIPTION_LENGTH);
        }
        if (foodItemDTO.getPrice() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (Double.isNaN(foodItemDTO.getPrice())) {
            throw new ApplicationException(Message.FoodItem.PRICE_DISCOUNT_DATATYPE);
        }
        if (Double.isNaN(foodItemDTO.getDiscount())) {
            throw new ApplicationException(Message.FoodItem.PRICE_DISCOUNT_DATATYPE);
        }
        if (foodItemDTO.getCategory() == null) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (foodItemDTO.getCategory().getCategoryId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        CategoryValidator.validateCategory(foodItemDTO.getCategory());
        if (foodItemDTO.getImagePath() == null || foodItemDTO.getImagePath().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (foodItemServices.isFoodItemExists(foodItemDTO)) {
            throw new ApplicationException(Message.FoodItem.FOOD_ITEM_EXISTS);
        }
    }
}
