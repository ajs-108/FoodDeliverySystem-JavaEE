package com.app.mapper.jpa;

import com.app.dto.jpa.JPAOrderFoodItemsDTO;
import com.app.mapper.FoodItemMapper;
import com.app.model.jpa.JPAOrderFoodItems;

public class JPAOrderFoodItemMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();
    private JPAOrderMapper orderMapper = new JPAOrderMapper();

    public JPAOrderFoodItemsDTO toDTO(JPAOrderFoodItems orderFoodItems) {
        JPAOrderFoodItemsDTO orderFoodItemsDTO = new JPAOrderFoodItemsDTO();
        if (orderFoodItems == null) {
            return null;
        }
        orderFoodItemsDTO.setOrder(orderMapper.toDTO(orderFoodItems.getOrder()));
        orderFoodItemsDTO.setFoodItem(foodItemMapper.toDTO(orderFoodItems.getFoodItem()));
        orderFoodItemsDTO.setQuantity(orderFoodItems.getQuantity());
        orderFoodItemsDTO.setFoodItemsTotal(orderFoodItems.getFoodItemsTotal());
        return orderFoodItemsDTO;
    }

    public JPAOrderFoodItems toOrderFoodItems(JPAOrderFoodItemsDTO orderFoodItemsDTO) {
        JPAOrderFoodItems orderFoodItems = new JPAOrderFoodItems();
        if (orderFoodItemsDTO == null) {
            return null;
        }
        orderFoodItems.setOrder(orderMapper.toOrder(orderFoodItemsDTO.getOrder()));
        orderFoodItems.setFoodItem(foodItemMapper.toFoodItem(orderFoodItemsDTO.getFoodItem()));
        orderFoodItems.setQuantity(orderFoodItemsDTO.getQuantity());
        orderFoodItems.setFoodItemsTotal(orderFoodItemsDTO.getFoodItemsTotal());
        return orderFoodItems;
    }
}
