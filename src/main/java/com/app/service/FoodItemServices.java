package com.app.service;

import com.app.common.exception.DBException;
import com.app.dao.IFoodItemDAO;
import com.app.dao.impl.FoodItemDAOImpl;
import com.app.dto.FoodItemDTO;
import com.app.mapper.FoodItemMapper;

import java.util.List;

public class FoodItemServices {
    private IFoodItemDAO foodItemDAO;
    private FoodItemMapper foodItemMapper;

    public FoodItemServices() {
        this.foodItemDAO = new FoodItemDAOImpl();
        this.foodItemMapper = new FoodItemMapper();
    }

    public void createFoodItem(FoodItemDTO foodItemDTO) throws DBException {
        foodItemDAO.saveFoodItem(foodItemMapper.toFoodItem(foodItemDTO));
    }

    public List<FoodItemDTO> getAllFoodItems() throws DBException {
        return foodItemDAO.getAllFoodItems()
                .stream()
                .map(foodItemMapper::toDTO)
                .toList();
    }

    public FoodItemDTO getFoodItem(int foodItemId) throws DBException {
        return foodItemMapper.toDTO(foodItemDAO.getFoodItem(foodItemId));
    }

    public void updateFoodItem(FoodItemDTO foodItemDTO) throws DBException {
        foodItemDAO.updateFoodItem(foodItemMapper.toFoodItem(foodItemDTO));
    }

    public void updateFoodItemAvailability(int foodItemId, boolean isAvailable) throws DBException {
        foodItemDAO.updateFoodItemAvailability(foodItemId, isAvailable);
    }

    public boolean isFoodItemExists(int foodItemId) throws DBException {
        return getFoodItem(foodItemId) != null;
    }

    public boolean isFoodItemExists(FoodItemDTO foodItemDTO) throws DBException {
        return getFoodItem(foodItemDTO.getFoodItemId()) != null;
    }

    public void updateRatings(int foodItem, int rating) throws DBException {
        double preRating = getFoodItem(foodItem).getRating();
        if (preRating != 1) {
            double newRating = (preRating + rating) / 2;
            foodItemDAO.updateFoodItemRating(foodItem, newRating);
        }
        foodItemDAO.updateFoodItemRating(foodItem, rating);
    }

    public void removeFoodItem(int foodItemId) throws DBException {
        foodItemDAO.removeFoodItem(foodItemId);
    }
}
