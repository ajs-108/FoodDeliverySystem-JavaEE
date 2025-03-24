package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.dto.CategoryDTO;

public class CategoryValidator {
    private static final int CATEGORY_NAME_LENGTH = 40;

    public static void validateCategory(CategoryDTO categoryDTO) throws ApplicationException {
        if(categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if(categoryDTO.getCategoryName().length() < CATEGORY_NAME_LENGTH) {
            throw new ApplicationException(Message.FoodItem.CATEGORY_NAME_LENGTH);
        }
    }
}
