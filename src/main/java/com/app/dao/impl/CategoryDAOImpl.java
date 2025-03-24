package com.app.dao.impl;

import com.app.common.Message;
import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.ICategoryDAO;
import com.app.model.Category;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryDAOImpl implements ICategoryDAO {
    @Override
    public void saveCategory(Category category) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "insert into category(category_name) values (?)";
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getCategoryName());
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, null, connection);
        }
    }

    @Override
    public Category getCategory(int categoryId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "Select * from category where category_id = ?";
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                return category;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, resultSet, connection);
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() throws DBException {
        Connection connection = null;
        List<Category> categoryList = new LinkedList<>();
        Category category;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "Select * from category";
        try {
            connection = DBConnector.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                categoryList.add(category);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(statement, resultSet, connection);
        }
        return categoryList;
    }
}
