package com.app.mapper;

import com.app.dto.ShoppingCartDTO;
import com.app.model.ShoppingCart;

public class ShoppingCartMapper {
    private CartFoodItemsMapper cartFoodItemsMapper = new CartFoodItemsMapper();

    public ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        if (shoppingCart == null) {
            return null;
        }
        shoppingCartDTO.setUserId(shoppingCart.getUserId());
        shoppingCartDTO.setTotalPrice(shoppingCart.getTotalPrice());
        if (shoppingCart.getCartFoodItemsList() != null) {
            shoppingCartDTO.setCartFoodItemsDTOList(shoppingCart.getCartFoodItemsList()
                    .stream()
                    .map(cartFoodItemsMapper::toDTO)
                    .toList());
        }
        shoppingCartDTO.setCartFoodItemsDTOList(null);
        return shoppingCartDTO;
    }

    public ShoppingCart toShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        if (shoppingCartDTO == null) {
            return null;
        }
        shoppingCart.setUserId(shoppingCartDTO.getUserId());
        shoppingCart.setTotalPrice(shoppingCartDTO.getTotalPrice());
        if (shoppingCartDTO.getCartFoodItemsDTOList() != null) {
            shoppingCart.setCartFoodItemsList(shoppingCartDTO.getCartFoodItemsDTOList()
                    .stream()
                    .map(cartFoodItemsMapper::toCartFoodItems)
                    .toList());
        }
        shoppingCart.setCartFoodItemsList(null);
        return shoppingCart;
    }
}
