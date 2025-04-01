package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.FoodItem;

import java.util.List;

public interface IFoodItemDAO {
    void saveFoodItem(FoodItem foodItem) throws DBException;
    FoodItem getFoodItem(int foodItemId) throws DBException;
    List<FoodItem> getAllFoodItems() throws DBException;
    void updateFoodItem(FoodItem foodItem) throws DBException;
    void updateFoodItemAvailability(int FoodItem, boolean isAvailable) throws DBException;
    void updateFoodItemRating(FoodItem foodItem) throws DBException;
}
