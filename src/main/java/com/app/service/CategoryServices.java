package com.app.service;

import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.controller.validation.CategoryValidator;
import com.app.dao.ICategoryDAO;
import com.app.dao.impl.CategoryDAOImpl;
import com.app.dto.CategoryDTO;
import com.app.mapper.CategoryMapper;

import java.util.List;

public class CategoryServices {
    private ICategoryDAO categoryDAO;
    private CategoryMapper categoryMapper;

    public CategoryServices() {
        this.categoryDAO = new CategoryDAOImpl();
        this.categoryMapper = new CategoryMapper();
    }

    public void saveCategory(List<CategoryDTO> categoryList) throws DBException, ApplicationException {
        for (CategoryDTO categoryDTO : categoryList) {
            CategoryValidator.validateCategory(categoryDTO);
            categoryDAO.saveCategory(categoryMapper.toCategory(categoryDTO));
        }
    }

    public List<CategoryDTO> getAllCategories() throws DBException {
        return categoryDAO.getAllCategories()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public CategoryDTO getCategory(int categoryId) throws DBException {
        return categoryMapper.toDTO(categoryDAO.getCategory(categoryId));
    }

    public CategoryDTO getCategory(String categoryName) throws DBException {
        return categoryMapper.toDTO(categoryDAO.getCategory(categoryName));
    }
}
