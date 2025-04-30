package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.FoodItem;

import java.util.List;

public interface IFoodItemRepository {
    void save(FoodItem foodItem) throws DBException;

    FoodItem findById(int foodItemId) throws DBException;

    FoodItem findFoodItemFromMenu(int foodItemId) throws DBException;

    List<FoodItem> findAll() throws DBException;

    List<FoodItem> findMenu() throws DBException;

    void update(FoodItem foodItem) throws DBException;

    void updateAvailability(int foodItemId, boolean isAvailable) throws DBException;

    void updateRating(int foodItemId, double rating) throws DBException;

    void removeFromMenu(int foodItemId) throws DBException;
}
