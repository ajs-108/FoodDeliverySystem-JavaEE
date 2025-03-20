package service;

import common.exception.DBException;
import dao.impl.CategoryDAOImpl;
import dao.ICategoryDAO;
import model.Category;

import java.util.List;

public class CategoryServices {
    private ICategoryDAO categoryDAO;

    public CategoryServices() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    public void saveCategory(Category category) throws DBException {
        categoryDAO.saveCategory(category);
    }

    public List<Category> getAllCategories() throws DBException {
        return categoryDAO.getAllCategories();
    }

    public Category getCategory(int categoryId) throws DBException {
        return categoryDAO.getCategory(categoryId);
    }
}
