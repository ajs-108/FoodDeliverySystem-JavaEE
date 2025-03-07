package services;

import entity.FoodItem;
import util.DBConnector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FoodItemServices {

    /**
     * Insert Record method is used for inserting the FoodItem records in the Database
     *
     * @param foodItem used to take the User data.
     */
    public int saveFoodItem(FoodItem foodItem) throws SQLException {
        Connection connect = DBConnector.getConnection();
        PreparedStatement ps = null;
        String sql = """
                insert into food_item(food_item_id, food_name, food_description, price, discount, is_available, category_id, image_path, rating)
                values (?,?,?,?,?,?,?,?,?);
                """;
        int flag;
        try {
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
            flag = ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            DBConnector.closeConnection();
        }
        return flag;
    }

    public List<FoodItem> getAllFoodItem() throws SQLException {
        Connection connect = DBConnector.getConnection();
        List<FoodItem> foodItemList = new LinkedList<>();
        String sql = "select * from food_item";

        try (Statement s = connect.createStatement(); ResultSet rs = s.executeQuery(sql)) {
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
        } finally {
            DBConnector.closeConnection();
        }
        return foodItemList;
    }

    public FoodItem getFoodItem(int foodItemId) throws SQLException {
        Connection connect = DBConnector.getConnection();
        FoodItem foodItem = new FoodItem();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from food_item where food_item_id = ?";

        try {
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
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            DBConnector.closeConnection();
        }
        return foodItem;
    }
}
