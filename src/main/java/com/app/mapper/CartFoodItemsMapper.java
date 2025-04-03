package com.app.mapper;

import com.app.dto.CartFoodItemsDTO;
import com.app.model.CartFoodItems;

public class CartFoodItemsMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public CartFoodItemsDTO toDTO(CartFoodItems cartFoodItems) {
        CartFoodItemsDTO cartFoodItemsDTO = new CartFoodItemsDTO();
        if (cartFoodItems != null) {
            cartFoodItemsDTO.setFoodItemDTO(foodItemMapper.toDTO(cartFoodItems.getFoodItem()));
            cartFoodItemsDTO.setQuantity(cartFoodItems.getQuantity());
        } else {
            return null;
        }
        return cartFoodItemsDTO;
    }

    public CartFoodItems toCartFoodItems(CartFoodItemsDTO cartFoodItemsDTO) {
        CartFoodItems cartFoodItems = new CartFoodItems();
        if (cartFoodItemsDTO != null) {
            cartFoodItems.setFoodItem(foodItemMapper.toFoodItem(cartFoodItemsDTO.getFoodItemDTO()));
            cartFoodItems.setQuantity(cartFoodItemsDTO.getQuantity());
        } else {
            return null;
        }
        return cartFoodItems;
    }
}
