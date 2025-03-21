package service;

import common.exception.DBException;
import dao.ICategoryDAO;
import dao.impl.CategoryDAOImpl;
import model.Category;

import java.util.List;

public class CategoryServices {
    private ICategoryDAO categoryDAO;

    public CategoryServices() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    public void saveCategory(List<Category> categoryList) throws DBException {
        for (Category category : categoryList) {
            categoryDAO.saveCategory(category);
        }
    }

    public List<Category> getAllCategories() throws DBException {
        return categoryDAO.getAllCategories();
    }

    public Category getCategory(int categoryId) throws DBException {
        return categoryDAO.getCategory(categoryId);
    }
}
