package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.ICategoryRepository;
import com.app.dao.jpa.impl.CategoryRepository;
import com.app.dto.CategoryDTO;
import com.app.mapper.CategoryMapper;

import java.util.List;

public class JPACategoryServices {
    private ICategoryRepository categoryRepo;
    private CategoryMapper categoryMapper;

    public JPACategoryServices() {
        categoryRepo = new CategoryRepository();
        categoryMapper = new CategoryMapper();
    }

    public List<CategoryDTO> findAll() throws DBException {
        return categoryRepo.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }
}
