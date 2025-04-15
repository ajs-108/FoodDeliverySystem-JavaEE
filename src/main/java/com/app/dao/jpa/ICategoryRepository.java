package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.Category;

import java.util.List;

public interface ICategoryRepository {
    List<Category> findAll() throws DBException;
}
