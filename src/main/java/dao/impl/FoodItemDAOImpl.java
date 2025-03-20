package dao.impl;

import common.Message;
import common.exception.DBException;
import config.DBConnector;
import dao.IFoodItemDAO;
import model.Category;
import model.FoodItem;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FoodItemDAOImpl implements IFoodItemDAO {
    @Override
    public void saveFoodItem(FoodItem foodItem) throws DBException {
        String sql = """
                insert into food_item(food_name, food_description, price, discount, category_id, image_path)
                values (?,?,?,?,?,?);
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, foodItem.getFoodName());
            ps.setString(2, foodItem.getFoodDescription());
            ps.setDouble(3, foodItem.getPrice());
            ps.setDouble(4, foodItem.getDiscount());
            ps.setInt(5, foodItem.getCategory().getCategoryId());
            ps.setString(6, foodItem.getImagePath());
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        }
    }

    @Override
    public FoodItem getFoodItem(int foodItemId) throws DBException {
        String sql = """
                select * from food_item, category
                where food_item.category_id = category.category_id and food_item_id = ?;
                """;
        Connection connection = null;
        FoodItem foodItem;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, foodItemId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
            } else {
                throw new DBException("No such FoodItem Found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, resultSet, connection);
        }
        return foodItem;
    }

    @Override
    public List<FoodItem> getAllFoodItems() throws DBException {
        String sql = "select * from food_item, category where food_item.category_id = category.category_id;";
        Connection connection = null;
        List<FoodItem> foodItemList = new LinkedList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
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
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        } finally {
            DBConnector.resourceCloser(statement, resultSet, connection);
        }
        return foodItemList;
    }

    @Override
    public void updateFoodItem(FoodItem foodItem, int foodItemId) throws DBException {
        String sql1 = """
                update food_item set food_description = ?, discount = ?, price = ?, is_available = ?, image_path = ?
                where food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement ps = connect.prepareStatement(sql1)) {
            ps.setString(1, foodItem.getFoodDescription());
            ps.setDouble(2, foodItem.getDiscount());
            ps.setDouble(3, foodItem.getPrice());
            ps.setBoolean(4, foodItem.isAvailable());
            ps.setString(5, foodItem.getImagePath());
            ps.setInt(6, foodItemId);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        }
    }
}
