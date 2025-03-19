package dao;

import common.exception.DBException;
import model.Category;

import java.util.List;

public interface ICategoryDAO {
    void saveCategory(Category category) throws DBException;

    Category getCategory(int category_id) throws DBException;

    List<Category> getAllCategories() throws DBException;
}
