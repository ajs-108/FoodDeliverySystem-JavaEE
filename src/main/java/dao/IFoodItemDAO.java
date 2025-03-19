package dao;

import common.exception.DBException;
import model.FoodItem;

import java.sql.SQLException;
import java.util.List;

public interface IFoodItemDAO {
    void saveFoodItem(FoodItem foodItem) throws DBException;

    FoodItem getFoodItem(int foodItemId) throws DBException;

    List<FoodItem> getAllFoodItems() throws DBException;

    void updateFoodItem(FoodItem foodItem, int foodItemId) throws DBException;
}
