package com.app.mapper;

import com.app.dto.FoodItemDTO;
import com.app.model.FoodItem;

public class FoodItemMapper {
    public FoodItemDTO toDTO(FoodItem foodItem) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();
        foodItemDTO.setFoodItemId(foodItem.getFoodItemId());
        foodItemDTO.setFoodName(foodItem.getFoodName());
        foodItemDTO.setFoodDescription(foodItem.getFoodDescription());
        foodItemDTO.setPrice(foodItemDTO.getPrice());
        foodItemDTO.setDiscount(foodItemDTO.getDiscount());
        foodItemDTO.setAvailable(foodItem.isAvailable());
        foodItemDTO.setCategory(foodItem.getCategory());
        foodItemDTO.setImagePath(foodItem.getImagePath());
        foodItemDTO.setRating(foodItem.getRating());
        return foodItemDTO;
    }

    public FoodItem toFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodItemId(foodItemDTO.getFoodItemId());
        foodItem.setFoodName(foodItemDTO.getFoodName());
        foodItem.setFoodDescription(foodItemDTO.getFoodDescription());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItem.setDiscount(foodItemDTO.getDiscount());
        foodItem.setAvailable(foodItemDTO.isAvailable());
        foodItem.setCategory(foodItemDTO.getCategory());
        foodItem.setImagePath(foodItemDTO.getImagePath());
        foodItem.setRating(foodItemDTO.getRating());
        return foodItem;
    }
}
