package com.app.mapper;

import com.app.dto.CategoryDTO;
import com.app.model.Category;

public class CategoryMapper {
    public CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        if (category != null) {
            categoryDTO.setCategoryId(category.getCategoryId());
            categoryDTO.setCategoryName(category.getCategoryName());
        } else {
            return null;
        }
        return categoryDTO;
    }

    public Category toCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        if (categoryDTO != null) {
            category.setCategoryId(categoryDTO.getCategoryId());
            category.setCategoryName(categoryDTO.getCategoryName());
        } else {
            return null;
        }
        return category;
    }
}
