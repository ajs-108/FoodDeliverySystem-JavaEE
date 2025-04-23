package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.Category;

import java.util.List;

public interface ICategoryRepository {
    void save(Category category) throws DBException;

    List<Category> findAll() throws DBException;

    Category find(int categoryId) throws DBException;

    Category find(String categoryName) throws DBException;
}
