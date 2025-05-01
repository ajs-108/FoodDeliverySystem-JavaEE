package com.app.mapper.common;

import com.app.dto.common.FoodItemDTO;
import com.app.model.common.FoodItem;

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
        foodItemDTO.setAvailable(foodItem.getIsAvailable());
        foodItemDTO.setCategory(categoryMapper.toDTO(foodItem.getCategory()));
        foodItemDTO.setCreatedOn(foodItem.getCreatedOn());
        foodItemDTO.setUpdatedOn(foodItem.getUpdatedOn());
        foodItemDTO.setImagePath(foodItem.getImagePath());
        foodItemDTO.setRating(foodItem.getRating());
        return foodItemDTO;
    }

    public FoodItem toFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();
        if (foodItemDTO == null) {
            return null;
        }
        foodItem.setFoodItemId(foodItemDTO.getFoodItemId());
        foodItem.setFoodName(foodItemDTO.getFoodName());
        foodItem.setFoodDescription(foodItemDTO.getFoodDescription());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItem.setDiscount(foodItemDTO.getDiscount());
        foodItem.setIsAvailable(foodItemDTO.isAvailable());
        foodItem.setCategory(categoryMapper.toCategory(foodItemDTO.getCategory()));
        foodItem.setCreatedOn(foodItemDTO.getCreatedOn());
        foodItem.setUpdatedOn(foodItemDTO.getUpdatedOn());
        foodItem.setImagePath(foodItemDTO.getImagePath());
        foodItem.setRating(foodItemDTO.getRating());
        return foodItem;
    }
}
