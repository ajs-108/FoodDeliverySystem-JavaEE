package dao;

import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryDAO {
    void saveCategory(Category category) throws SQLException;
    Category getCategory(int category_id) throws SQLException;
    List<Category> getAllCategories() throws SQLException;
}
