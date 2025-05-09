package com.app.dao.jdbc.impl;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.jdbc.ICategoryDAO;
import com.app.model.common.Category;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryDAOImpl implements ICategoryDAO {
    protected static final String CATEGORY_ID = "category_id";
    protected static final String CATEGORY_NAME = "category_name";

    @Override
    public void saveCategory(Category category) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "insert into category(category_name) values (?)";
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, null, connection);
        }
    }

    @Override
    public Category getCategory(int categoryId) throws DBException {
        String sql = "Select * from category where category_id = ?";
        ResultSet resultSet = null;
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt(CATEGORY_ID));
                category.setCategoryName(resultSet.getString(CATEGORY_NAME));
                return category;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(null, resultSet, null);
        }
        return null;
    }

    @Override
    public Category getCategory(String categoryName) throws DBException {
        String sql = "Select * from category where category_name = ?";
        ResultSet resultSet = null;
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt(CATEGORY_ID));
                category.setCategoryName(resultSet.getString(CATEGORY_NAME));
                return category;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(null, resultSet, null);
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() throws DBException {
        String sql = "Select * from category";
        try (Connection connection = DBConnector.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Category> categoryList = new LinkedList<>();
            while (resultSet.next()) {
                Category category;
                category = new Category();
                category.setCategoryId(resultSet.getInt(CATEGORY_ID));
                category.setCategoryName(resultSet.getString(CATEGORY_NAME));
                categoryList.add(category);
            }
            return categoryList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
