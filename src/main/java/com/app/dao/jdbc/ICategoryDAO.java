package com.app.dao.jdbc;

import com.app.common.exception.DBException;
import com.app.model.common.Category;

import java.util.List;

public interface ICategoryDAO {
    void saveCategory(Category category) throws DBException;

    Category getCategory(int categoryId) throws DBException;

    Category getCategory(String categoryName) throws DBException;

    List<Category> getAllCategories() throws DBException;
}
