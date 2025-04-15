package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.FoodItemDTO;
import com.app.service.CategoryServices;
import com.app.service.FoodItemServices;

import static com.app.controller.validation.CategoryValidator.CATEGORY_NAME_LENGTH;

public class FoodItemValidator {
    private static final int FOOD_NAME_LENGTH = 30;
    private static final int FOOD_DESCRIPTION_LENGTH = 30;
    private static final FoodItemServices foodItemServices = new FoodItemServices();
    private static final CategoryServices categoryServices = new CategoryServices();
    private static final Validator validator = new Validator();

    private FoodItemValidator() {
    }

    public static void validateFoodItem(FoodItemDTO foodItemDTO) throws ApplicationException, DBException {
        commonValidations(foodItemDTO);
        if (foodItemServices.isFoodItemExistsInMenu(foodItemDTO)) {
            throw new ApplicationException(Message.FoodItem.FOOD_ITEM_EXISTS);
        }
    }

    public static void validateOnUpdate(FoodItemDTO foodItemDTO) throws ApplicationException, DBException {
        if (foodItemDTO.getFoodItemId() == 0) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        commonValidations(foodItemDTO);
        if (categoryServices.getCategory(foodItemDTO.getCategory().getCategoryId()) == null) {
            throw new ApplicationException(Message.Common.RESOURCE_NOT_AVAILABLE);
        }
    }

    public static void validateOnAvailabilityUpdate(String foodItemId, String isAvailable) throws ApplicationException, DBException {
        if (foodItemId == null || foodItemId.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isPositiveInteger(foodItemId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(Integer.parseInt(foodItemId))) {
            throw new ApplicationException(Message.Common.RESOURCE_NOT_AVAILABLE);
        }
        if (isAvailable == null || isAvailable.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isBoolean(isAvailable)) {
            throw new ApplicationException(Message.Common.NOT_A_BOOLEAN);
        }
    }

    private static void commonValidations(FoodItemDTO foodItemDTO) throws ApplicationException {
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
        if (foodItemDTO.getCategory().getCategoryName() == null || foodItemDTO.getCategory().getCategoryName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (foodItemDTO.getCategory().getCategoryName().length() > CATEGORY_NAME_LENGTH) {
            throw new ApplicationException(Message.Category.CATEGORY_NAME_LENGTH);
        }
        if (foodItemDTO.getImagePath() == null || foodItemDTO.getImagePath().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
    }

    public static void validateRemoval(String foodItemId) throws ApplicationException, DBException {
        if (foodItemId == null || foodItemId.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isPositiveInteger(foodItemId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (!foodItemServices.isFoodItemExistsInMenu(Integer.parseInt(foodItemId))) {
            throw new ApplicationException(Message.FoodItem.FOOD_ITEM_DOES_NOT_EXISTS);
        }
    }
}
