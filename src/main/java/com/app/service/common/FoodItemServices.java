package com.app.service.common;

import com.app.common.exception.DBException;
import com.app.dao.jdbc.IFoodItemDAO;
import com.app.dao.jdbc.impl.FoodItemDAOImpl;
import com.app.dto.common.FoodItemDTO;
import com.app.mapper.common.FoodItemMapper;

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
        return foodItemDAO.getMenu()
                .stream()
                .map(foodItemMapper::toDTO)
                .toList();
    }

    public FoodItemDTO getFoodItemFromMenu(int foodItemId) throws DBException {
        return foodItemMapper.toDTO(foodItemDAO.getFoodItemFromMenu(foodItemId));
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

    public boolean isFoodItemExistsInMenu(int foodItemId) throws DBException {
        return getFoodItemFromMenu(foodItemId) != null;
    }

    public boolean isFoodItemExists(int foodItemId) throws DBException {
        return getFoodItem(foodItemId) != null;
    }

    public void updateRatings(int foodItemId, List<Double> ratings) throws DBException {
        double preRating = getFoodItem(foodItemId).getRating();
        if (preRating == 1) {
            foodItemDAO.updateFoodItemRating(foodItemId, Math.round(ratings.get(0)));
        }
        double newRating = ratings.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(1.0);
        foodItemDAO.updateFoodItemRating(foodItemId, Math.round(newRating));
    }

    public void removeFoodItem(int foodItemId) throws DBException {
        foodItemDAO.removeFoodItem(foodItemId);
    }
}
