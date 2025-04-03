package com.app.mapper;

import com.app.dto.ShoppingCartDTO;
import com.app.model.ShoppingCart;

public class ShoppingCartMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();
    private CartFoodItemsMapper cartFoodItemsMapper = new CartFoodItemsMapper();

    public ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        if (shoppingCart != null) {
            shoppingCartDTO.setUserId(shoppingCart.getUserId());
            shoppingCartDTO.setCartFoodItemsDTOList(shoppingCart.getCartFoodItemsList()
                    .stream()
                    .map(cartFoodItemsMapper::toDTO)
                    .toList());
            shoppingCartDTO.setTotalPrice(shoppingCart.getTotalPrice());
        } else {
            return null;
        }
        return shoppingCartDTO;
    }

    public ShoppingCart toShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        if (shoppingCartDTO != null) {
            shoppingCart.setUserId(shoppingCartDTO.getUserId());
            shoppingCart.setCartFoodItemsList(shoppingCartDTO.getCartFoodItemsDTOList()
                    .stream()
                    .map(cartFoodItemsMapper::toCartFoodItems)
                    .toList());
            shoppingCart.setTotalPrice(shoppingCartDTO.getTotalPrice());
        } else {
            return null;
        }
        return shoppingCart;
    }
}
