package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.ICategoryRepository;
import com.app.dao.jpa.impl.CategoryRepository;
import com.app.model.Category;

import java.util.List;

public class JPACategoryServices {
    private ICategoryRepository categoryRepo;

    public JPACategoryServices() {
        categoryRepo = new CategoryRepository();
    }

    public List<Category> findAll() throws DBException {
        return categoryRepo.findAll();
    }
}
