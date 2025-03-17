package dao;

import model.FoodItem;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IFoodItemDAO {
    void saveFoodItem(FoodItem foodItem) throws SQLException;
    FoodItem getFoodItem(int foodItemId) throws SQLException;
    List<FoodItem> getAllFoodItems() throws SQLException;
    void updateFoodItem(FoodItem foodItem) throws SQLException;
}
