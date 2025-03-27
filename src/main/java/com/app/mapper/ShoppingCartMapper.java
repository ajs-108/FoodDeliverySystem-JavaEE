package com.app.mapper;

import com.app.dto.ShoppingCartDTO;
import com.app.model.ShoppingCart;

public class ShoppingCartMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        if (shoppingCart != null) {
            shoppingCartDTO.setUserId(shoppingCart.getUserId());
            shoppingCartDTO.setFoodItemDTO(foodItemMapper.toDTO(shoppingCart.getFoodItem()));
            shoppingCartDTO.setQuantity(shoppingCart.getQuantity());
        } else {
            return null;
        }
        return shoppingCartDTO;
    }

    public ShoppingCart toShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        if (shoppingCartDTO != null) {
            shoppingCart.setUserId(shoppingCartDTO.getUserId());
            shoppingCart.setFoodItem(foodItemMapper.toFoodItem(shoppingCartDTO.getFoodItemDTO()));
            shoppingCart.setQuantity(shoppingCartDTO.getQuantity());
        } else {
            return null;
        }
        return shoppingCart;
    }
}
