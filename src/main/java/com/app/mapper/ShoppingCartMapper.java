package com.app.mapper;

import com.app.dao.IShoppingCartDAO;
import com.app.dto.CategoryDTO;
import com.app.dto.ShoppingCartDTO;
import com.app.model.Category;
import com.app.model.ShoppingCart;

public class ShoppingCartMapper {
    public ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setFoodItem(shoppingCart.getFoodItem());
        shoppingCartDTO.setUser(shoppingCart.getUser());
        shoppingCartDTO.setQuantity(shoppingCart.getQuantity());
        return shoppingCartDTO;
    }

    public ShoppingCart toShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setFoodItem(shoppingCartDTO.getFoodItem());
        shoppingCart.setUser(shoppingCartDTO.getUser());
        shoppingCart.setQuantity(shoppingCartDTO.getQuantity());
        return shoppingCart;
    }
}
