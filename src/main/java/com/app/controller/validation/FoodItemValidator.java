package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.dto.CategoryDTO;
import com.app.dto.FoodItemDTO;

public class FoodItemValidator {
    private static final int FOOD_NAME_LENGTH = 30;
    private static final int FOOD_DESCRIPTION_LENGTH = 30;

    public static void validateFoodItem(FoodItemDTO foodItemDTO) throws ApplicationException {
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
            throw new ApplicationException(Message.FoodItem.PRICE);
        }
        if (foodItemDTO.getCategory() == null) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (foodItemDTO.getImagePath() == null || foodItemDTO.getImagePath().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
    }
}
