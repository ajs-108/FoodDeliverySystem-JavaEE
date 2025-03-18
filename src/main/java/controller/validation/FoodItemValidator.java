package controller.validation;

import common.exception.ValidationException;
import model.FoodItem;

public class FoodItemValidator {
    public static void validateFoodItem(FoodItem foodItem) throws ValidationException {
        if(foodItem.getFoodName() == null || foodItem.getFoodName().isBlank()) {
            throw new ValidationException("Food name is Mandatory");
        }
        if(foodItem.getFoodDescription() == null || foodItem.getFoodDescription().isBlank()) {
            throw new ValidationException("Food description is Mandatory");
        }
        if(foodItem.getPrice() == 0) {
            throw new ValidationException("Food Item Price is Mandatory");
        }
        if(Double.isNaN(foodItem.getPrice())) {
            throw new ValidationException("Value should in Decimal number");
        }
    }
}
