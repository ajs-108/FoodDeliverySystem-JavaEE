package com.app.controller.common.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.common.CategoryDTO;
import com.app.service.common.CategoryServices;

public class CategoryValidator {
    protected static final int CATEGORY_NAME_LENGTH = 40;
    private static CategoryServices categoryServices = new CategoryServices();

    private CategoryValidator () {
    }

    public static void validateCategory(CategoryDTO categoryDTO) throws ApplicationException, DBException {
        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (categoryDTO.getCategoryName().length() > CATEGORY_NAME_LENGTH) {
            throw new ApplicationException(Message.Category.CATEGORY_NAME_LENGTH);
        }
        if (categoryServices.getCategory(categoryDTO.getCategoryName()) != null) {
            throw new ApplicationException(Message.Category.CATEGORY_EXISTS);
        }
    }
}
