package com.app.service;

import com.app.common.exception.DBException;
import com.app.dao.IFoodItemDAO;
import com.app.dao.impl.FoodItemDAOImpl;
import com.app.dto.FoodItemDTO;
import com.app.mapper.FoodItemMapper;
import com.app.model.FoodItem;

import java.util.List;
import java.util.Objects;

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

    public boolean isFoodItemExists(int foodItemId) throws DBException {
        return getFoodItem(foodItemId) != null;
    }

    public boolean isFoodItemExists(FoodItemDTO foodItemDTO) throws DBException {
        List<FoodItemDTO> foodItemsList = getAllFoodItems();
        for (FoodItemDTO foodItem : foodItemsList) {
            if (Objects.equals(foodItem.getFoodItemId(), foodItemDTO.getFoodItemId())) {
                return true;
            }
        }
        return false;
    }
}
