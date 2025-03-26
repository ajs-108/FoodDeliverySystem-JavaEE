package com.app.mapper;

import com.app.dto.ShoppingCartDTO;
import com.app.model.ShoppingCart;

public class ShoppingCartMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setUserId(shoppingCart.getUserId());
        shoppingCartDTO.setFoodItemDTO(foodItemMapper.toDTO(shoppingCart.getFoodItem()));
        shoppingCartDTO.setQuantity(shoppingCart.getQuantity());
        return shoppingCartDTO;
    }

    public ShoppingCart toShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(shoppingCartDTO.getUserId());
        shoppingCart.setFoodItem(foodItemMapper.toFoodItem(shoppingCartDTO.getFoodItemDTO()));
        shoppingCart.setQuantity(shoppingCartDTO.getQuantity());
        return shoppingCart;
    }
}
