package controller.validation;

import common.exception.ApplicationException;
import model.FoodItem;

public class FoodItemValidator {
    public static void validateFoodItem(FoodItem foodItem) throws ApplicationException {
        if(foodItem.getFoodName() == null || foodItem.getFoodName().isBlank()) {
            throw new ApplicationException("Food name is Mandatory");
        }
        if(foodItem.getFoodDescription() == null || foodItem.getFoodDescription().isBlank()) {
            throw new ApplicationException("Food description is Mandatory");
        }
        if(foodItem.getPrice() == 0) {
            throw new ApplicationException("Food Item Price is Mandatory");
        }
        if(Double.isNaN(foodItem.getPrice())) {
            throw new ApplicationException("Value should in Decimal number");
        }
    }
}
