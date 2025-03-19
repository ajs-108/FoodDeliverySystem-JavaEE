package dao.impl;

import common.Message;
import common.exception.DBException;
import dao.ICategoryDAO;
import model.Category;
import config.DBConnector;

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
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, null, connection);
        }
    }

    @Override
    public Category getCategory(int category_id) throws DBException {
        Connection connection = null;
        Category category = new Category();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "Select * from category where category_id = ?";
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, category_id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, resultSet, connection);
        }
        return category;
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
            throw new DBException(Message.Error.INTERNAL_ERROR, e);
        } finally {
            DBConnector.resourceCloser(statement, resultSet, connection);
        }
        return categoryList;
    }
}
