package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IFoodItemRepository;
import com.app.dao.jpa.impl.FoodItemRepository;
import com.app.dto.FoodItemDTO;
import com.app.mapper.FoodItemMapper;

import java.util.List;

public class JPAFoodItemServices {
    private IFoodItemRepository foodItemRepo;
    private FoodItemMapper foodItemMapper;

    public JPAFoodItemServices() {
        foodItemRepo = new FoodItemRepository();
        foodItemMapper = new FoodItemMapper();
    }

    public void save(FoodItemDTO foodItemDTO) throws DBException {
        foodItemRepo.save(foodItemMapper.toFoodItem(foodItemDTO));
    }

    public FoodItemDTO findById(int foodItemId) throws DBException {
        return foodItemMapper.toDTO(foodItemRepo.findById(foodItemId));
    }

    public FoodItemDTO findFoodItemFromMenu(int foodItemId) throws DBException {
        return foodItemMapper.toDTO(foodItemRepo.findFoodItemFromMenu(foodItemId));
    }

    public List<FoodItemDTO> findAll() throws DBException {
        return foodItemRepo.findAll()
                .stream()
                .map(foodItemMapper::toDTO)
                .toList();
    }

    public List<FoodItemDTO> findMenu() throws DBException {
        return foodItemRepo.findMenu()
                .stream()
                .map(foodItemMapper::toDTO)
                .toList();
    }

    public void update(FoodItemDTO foodItemDTO) throws DBException {
        foodItemRepo.update(foodItemMapper.toFoodItem(foodItemDTO));
    }

    public void updateAvailability(int foodItemId, boolean isAvailable) throws DBException {
        foodItemRepo.updateAvailability(foodItemId, isAvailable);
    }

    public void updateRating(int foodItemId, double rating) throws DBException {
        foodItemRepo.updateRating(foodItemId, rating);
    }

    public void removeFromMenu(int foodItemId) throws DBException {
        foodItemRepo.removeFromMenu(foodItemId);
    }
}
