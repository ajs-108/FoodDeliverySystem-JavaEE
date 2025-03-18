package service;

import dao.impl.CategoryDAOImpl;
import dao.ICategoryDAO;
import model.Category;

import java.sql.*;
import java.util.List;

public class CategoryServices {
    private ICategoryDAO categoryDAO;

    public CategoryServices() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    public void saveCategory(Category category) throws SQLException {
        categoryDAO.saveCategory(category);
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    public Category getCategory(int category_id) throws SQLException {
        return categoryDAO.getCategory(category_id);
    }
}
