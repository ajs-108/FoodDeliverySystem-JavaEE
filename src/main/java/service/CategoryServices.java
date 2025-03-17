package services;

import dao.CategoryDAOImpl;
import model.Category;
import util.DBConnector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryServices {
    private CategoryDAOImpl categoryDAO;

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
