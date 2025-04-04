package com.app.mapper;

import com.app.dto.FoodItemDTO;
import com.app.model.FoodItem;

public class FoodItemMapper {
    private CategoryMapper categoryMapper = new CategoryMapper();

    public FoodItemDTO toDTO(FoodItem foodItem) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();
        if (foodItem == null) {
            return null;
        }
        foodItemDTO.setFoodItemId(foodItem.getFoodItemId());
        foodItemDTO.setFoodName(foodItem.getFoodName());
        foodItemDTO.setFoodDescription(foodItem.getFoodDescription());
        foodItemDTO.setPrice(foodItem.getPrice());
        foodItemDTO.setDiscount(foodItem.getDiscount());
        foodItemDTO.setAvailable(foodItem.isAvailable());
        foodItemDTO.setCategory(categoryMapper.toDTO(foodItem.getCategory()));
        foodItemDTO.setImagePath(foodItem.getImagePath());
        foodItemDTO.setRating(foodItem.getRating());
        return foodItemDTO;
    }

    public FoodItem toFoodItem(com.app.dto.FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();
        if (foodItemDTO == null) {
            return null;
        }
        foodItem.setFoodItemId(foodItemDTO.getFoodItemId());
        foodItem.setFoodName(foodItemDTO.getFoodName());
        foodItem.setFoodDescription(foodItemDTO.getFoodDescription());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItem.setDiscount(foodItemDTO.getDiscount());
        foodItem.setAvailable(foodItemDTO.isAvailable());
        foodItem.setCategory(categoryMapper.toCategory(foodItemDTO.getCategory()));
        foodItem.setImagePath(foodItemDTO.getImagePath());
        foodItem.setRating(foodItemDTO.getRating());
        return foodItem;
    }
}
