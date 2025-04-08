package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.CategoryDTO;
import com.app.service.CategoryServices;

public class CategoryValidator {
    private static final CategoryServices categoryServices = new CategoryServices();
    protected static final int CATEGORY_NAME_LENGTH = 40;

    public static void validateCategory(CategoryDTO categoryDTO) throws ApplicationException, DBException {
        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (categoryDTO.getCategoryName().length() > CATEGORY_NAME_LENGTH) {
            throw new ApplicationException(Message.Category.CATEGORY_NAME_LENGTH);
        }
        if (categoryServices.getCategory(categoryDTO.getCategoryName()) == null) {
            throw new ApplicationException(Message.Category.CATEGORY_EXISTS);
        }
    }
}
