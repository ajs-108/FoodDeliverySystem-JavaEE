package com.app.mapper;

import com.app.model.FoodItem;

public class FoodItemMapper {
    CategoryMapper categoryMapper = new CategoryMapper();

    public com.app.dto.FoodItemDTO toDTO(FoodItem foodItem) {
        com.app.dto.FoodItemDTO foodItemDTO = new com.app.dto.FoodItemDTO();
        if (foodItem != null) {
            foodItemDTO.setFoodItemId(foodItem.getFoodItemId());
            foodItemDTO.setFoodName(foodItem.getFoodName());
            foodItemDTO.setFoodDescription(foodItem.getFoodDescription());
            foodItemDTO.setPrice(foodItem.getPrice());
            foodItemDTO.setDiscount(foodItem.getDiscount());
            foodItemDTO.setAvailable(foodItem.isAvailable());
            foodItemDTO.setCategory(categoryMapper.toDTO(foodItem.getCategory()));
            foodItemDTO.setImagePath(foodItem.getImagePath());
            foodItemDTO.setRating(foodItem.getRating());
        } else {
            return null;
        }
        return foodItemDTO;
    }

    public FoodItem toFoodItem(com.app.dto.FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();
        if (foodItemDTO != null) {
            foodItem.setFoodItemId(foodItemDTO.getFoodItemId());
            foodItem.setFoodName(foodItemDTO.getFoodName());
            foodItem.setFoodDescription(foodItemDTO.getFoodDescription());
            foodItem.setPrice(foodItemDTO.getPrice());
            foodItem.setDiscount(foodItemDTO.getDiscount());
            foodItem.setAvailable(foodItemDTO.isAvailable());
            foodItem.setCategory(categoryMapper.toCategory(foodItemDTO.getCategory()));
            foodItem.setImagePath(foodItemDTO.getImagePath());
            foodItem.setRating(foodItemDTO.getRating());
        } else {
            return null;
        }
        return foodItem;
    }
}
