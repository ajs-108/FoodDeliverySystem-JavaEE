package com.app.mapper.jpa;

import com.app.dto.jpa.JPACartDTO;
import com.app.mapper.FoodItemMapper;
import com.app.model.jpa.JPACart;

public class JPACartMapper {
    private JPAUserMapper userMapper = new JPAUserMapper();
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public JPACartDTO toDTO(JPACart cart) {
        JPACartDTO cartDTO = new JPACartDTO();
        if (cart == null) {
            return null;
        }
        cartDTO.setUser(userMapper.toDTO(cart.getUser()));
        cartDTO.setFoodItem(foodItemMapper.toDTO(cart.getFoodItem()));
        cartDTO.setQuantity(cart.getQuantity());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        cartDTO.setBeforeDiscountPrice(cart.getBeforeDiscountPrice());
        cartDTO.setAfterDiscountPrice(cart.getAfterDiscountPrice());
        return cartDTO;
    }

    public JPACart toCart(JPACartDTO cartDTO) {
        JPACart cart = new JPACart();
        if (cartDTO == null) {
            return null;
        }
        cart.setUser(userMapper.toUser(cartDTO.getUser()));
        cart.setFoodItem(foodItemMapper.toFoodItem(cartDTO.getFoodItem()));
        cart.setQuantity(cartDTO.getQuantity());
        cart.setTotalPrice(cartDTO.getTotalPrice());
        cart.setBeforeDiscountPrice(cartDTO.getBeforeDiscountPrice());
        cart.setAfterDiscountPrice(cartDTO.getAfterDiscountPrice());
        return cart;
    }
}
