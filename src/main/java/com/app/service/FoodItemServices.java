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

    public void updateFoodItem(FoodItemDTO foodItemDTO, int foodItemId) throws DBException {
        foodItemDAO.updateFoodItem(foodItemMapper.toFoodItem(foodItemDTO), foodItemId);
    }
}
