package dao;

import controller.DBConnectionController;
import model.FoodItem;
import util.DBConnector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FoodItemDAOImpl implements IFoodItemDAO {
    @Override
    public void saveFoodItem(FoodItem foodItem) throws SQLException {
        Connection connect = null;
        PreparedStatement ps = null;
        String sql = """
                insert into food_item(food_item_id, food_name, food_description, price, discount, is_available, category_id, image_path, rating)
                values (?,?,?,?,?,?,?,?,?);
                """;
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, foodItem.getFoodItemId());
            ps.setString(2, foodItem.getFoodName());
            ps.setString(3, foodItem.getFoodDescription());
            ps.setDouble(4, foodItem.getPrice());
            ps.setDouble(5, foodItem.getDiscount());
            ps.setBoolean(6, foodItem.isAvailable());
            ps.setInt(7, foodItem.getCategoryId());
            ps.setString(8, foodItem.getImagePath());
            ps.setDouble(9, foodItem.getRating());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
    }

    @Override
    public FoodItem getFoodItem(int foodItemId) throws SQLException {
        Connection connect = null;
        FoodItem foodItem = new FoodItem();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from food_item where food_item_id = ?";
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, foodItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                foodItem = new FoodItem();
                foodItem.setFoodItemId(rs.getInt("food_item_id"));
                foodItem.setFoodName(rs.getString("food_name"));
                foodItem.setFoodDescription(rs.getString("food_description"));
                foodItem.setPrice(rs.getDouble("price"));
                foodItem.setDiscount(rs.getDouble("discount"));
                foodItem.setAvailable(rs.getBoolean("is_available"));
                foodItem.setCategoryId(rs.getInt("category_id"));
                foodItem.setImagePath(rs.getString("image_path"));
                foodItem.setRating(rs.getDouble("rating"));
            } else {
                System.out.println("No such foodItem found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
        return foodItem;
    }

    @Override
    public List<FoodItem> getAllFoodItems() throws SQLException {
        Connection connect = null;
        List<FoodItem> foodItemList = new LinkedList<>();
        Statement s = null;
        ResultSet rs = null;
        String sql = "select * from food_item";
        try {
            connect = DBConnector.getInstance().getConnection();
            s = connect.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodItemId(rs.getInt("food_item_id"));
                foodItem.setFoodName(rs.getString("food_name"));
                foodItem.setFoodDescription(rs.getString("food_description"));
                foodItem.setPrice(rs.getDouble("price"));
                foodItem.setDiscount(rs.getDouble("discount"));
                foodItem.setAvailable(rs.getBoolean("is_available"));
                foodItem.setCategoryId(rs.getInt("category_id"));
                foodItem.setImagePath(rs.getString("image_path"));
                foodItem.setRating(rs.getDouble("rating"));
                foodItemList.add(foodItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
        return foodItemList;
    }

    @Override
    public void updateFoodItem(FoodItem foodItem) throws SQLException {

    }
}
