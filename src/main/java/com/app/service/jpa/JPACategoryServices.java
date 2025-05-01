package com.app.service.jpa;

import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.controller.common.validation.CategoryValidator;
import com.app.dao.jpa.ICategoryRepository;
import com.app.dao.jpa.impl.CategoryRepository;
import com.app.dto.common.CategoryDTO;
import com.app.mapper.common.CategoryMapper;

import java.util.List;

public class JPACategoryServices {
    private ICategoryRepository categoryRepo;
    private CategoryMapper categoryMapper;

    public JPACategoryServices() {
        categoryRepo = new CategoryRepository();
        categoryMapper = new CategoryMapper();
    }

    public void save(List<CategoryDTO> categoryDTOList) throws DBException, ApplicationException {
        for (CategoryDTO categoryDTO : categoryDTOList) {
//            CategoryValidator.validateCategory(categoryDTO);TODO:check this
            categoryRepo.save(categoryMapper.toCategory(categoryDTO));
        }
    }

    public List<CategoryDTO> findAll() throws DBException {
        return categoryRepo.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public CategoryDTO find(int categoryId) throws DBException {
        return categoryMapper.toDTO(categoryRepo.find(categoryId));
    }

    public CategoryDTO find(String categoryName) throws DBException {
        return categoryMapper.toDTO(categoryRepo.find(categoryName));
    }
}
