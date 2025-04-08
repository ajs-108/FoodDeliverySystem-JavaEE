package com.app.dao.impl;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IFoodItemDAO;
import com.app.model.Category;
import com.app.model.FoodItem;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FoodItemDAOImpl implements IFoodItemDAO {
    @Override
    public void saveFoodItem(FoodItem foodItem) throws DBException {
        String sql = """
                insert into food_item(food_name, food_description, price, discount,
                                        category_id, image_path, is_available)
                values (?,?,?,?,?,?,?);
                """;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setString(1, foodItem.getFoodName());
            preparedStatement.setString(2, foodItem.getFoodDescription());
            preparedStatement.setDouble(3, foodItem.getPrice());
            preparedStatement.setDouble(4, foodItem.getDiscount());
            preparedStatement.setInt(5, foodItem.getCategory().getCategoryId());
            preparedStatement.setString(6, foodItem.getImagePath());
            preparedStatement.setBoolean(7, foodItem.isAvailable());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public FoodItem getFoodItem(int foodItemId) throws DBException {
        String sql = """
                select * from menu, category
                where menu.category_id = category.category_id and food_item_id = ?;
                """;
        ResultSet resultSet = null;
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, foodItemId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                FoodItem foodItem;
                foodItem = new FoodItem();
                Category category = new Category();
                foodItem.setFoodItemId(resultSet.getInt("food_item_id"));
                foodItem.setFoodName(resultSet.getString("food_name"));
                foodItem.setFoodDescription(resultSet.getString("food_description"));
                foodItem.setPrice(resultSet.getDouble("price"));
                foodItem.setDiscount(resultSet.getDouble("discount"));
                foodItem.setAvailable(resultSet.getBoolean("is_available"));
                foodItem.setImagePath(resultSet.getString("image_path"));
                foodItem.setRating(resultSet.getDouble("rating"));
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                foodItem.setCategory(category);
                return foodItem;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(null, resultSet, null);
        }
        return null;
    }

    @Override
    public List<FoodItem> getAllFoodItems() throws DBException {
        String sql = "select * from menu, category where menu.category_id = category.category_id;";
        List<FoodItem> foodItemList = new LinkedList<>();
        try (Connection connection = DBConnector.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                FoodItem foodItem = new FoodItem();
                Category category = new Category();
                foodItem.setFoodItemId(resultSet.getInt("food_item_id"));
                foodItem.setFoodName(resultSet.getString("food_name"));
                foodItem.setFoodDescription(resultSet.getString("food_description"));
                foodItem.setPrice(resultSet.getDouble("price"));
                foodItem.setDiscount(resultSet.getDouble("discount"));
                foodItem.setAvailable(resultSet.getBoolean("is_available"));
                foodItem.setImagePath(resultSet.getString("image_path"));
                foodItem.setRating(resultSet.getDouble("rating"));
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                foodItem.setCategory(category);
                foodItemList.add(foodItem);
            }
            return foodItemList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateFoodItem(FoodItem foodItem) throws DBException {
        String sql = """
                update food_item set food_name = ?, food_description = ?, price = ?, discount = ?, image_path = ?
                where food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, foodItem.getFoodName());
            ps.setString(2, foodItem.getFoodDescription());
            ps.setDouble(3, foodItem.getPrice());
            ps.setDouble(4, foodItem.getDiscount());
            ps.setString(5, foodItem.getImagePath());
            ps.setInt(6, foodItem.getFoodItemId());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateFoodItemAvailability(int foodItemId, boolean isAvailable) throws DBException {
        String sql1 = """
                update food_item set is_available = ?
                where food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement ps = connect.prepareStatement(sql1)) {
            ps.setBoolean(1, isAvailable);
            ps.setInt(2, foodItemId);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateFoodItemRating(int foodItemId, double rating) throws DBException {
        String sql1 = """
                update food_item set rating = ?
                where food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement ps = connect.prepareStatement(sql1)) {
            ps.setDouble(1, rating);
            ps.setInt(2, foodItemId);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void removeFoodItem(int foodItemId) throws DBException {
        String sql1 = "delete from menu where food_item_id = ?";
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement ps = connect.prepareStatement(sql1)) {
            ps.setInt(1, foodItemId);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
