package com.app.mapper;

import com.app.dto.CartFoodItemsDTO;
import com.app.model.CartFoodItems;

public class CartFoodItemsMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public CartFoodItemsDTO toDTO(CartFoodItems cartFoodItems) {
        CartFoodItemsDTO cartFoodItemsDTO = new CartFoodItemsDTO();
        if (cartFoodItems == null) {
            return null;
        }
        cartFoodItemsDTO.setFoodItemDTO(foodItemMapper.toDTO(cartFoodItems.getFoodItem()));
        cartFoodItemsDTO.setQuantity(cartFoodItems.getQuantity());
        return cartFoodItemsDTO;
    }

    public CartFoodItems toCartFoodItems(CartFoodItemsDTO cartFoodItemsDTO) {
        CartFoodItems cartFoodItems = new CartFoodItems();
        if (cartFoodItemsDTO == null) {
            return null;
        }
        cartFoodItems.setFoodItem(foodItemMapper.toFoodItem(cartFoodItemsDTO.getFoodItemDTO()));
        cartFoodItems.setQuantity(cartFoodItemsDTO.getQuantity());
        return cartFoodItems;
    }
}
