package com.app.mapper;

import com.app.dto.OrderFoodItemsDTO;
import com.app.model.OrderFoodItems;

public class OrderFoodItemsMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public OrderFoodItemsDTO toDTO(OrderFoodItems orderFoodItems) {
        OrderFoodItemsDTO orderFoodItemsDTO = new OrderFoodItemsDTO();
        if (orderFoodItems != null) {
            orderFoodItemsDTO.setFoodItem(foodItemMapper.toDTO(orderFoodItems.getFoodItem()));
            orderFoodItemsDTO.setQuantity(orderFoodItems.getQuantity());
        } else {
            return null;
        }
        return orderFoodItemsDTO;
    }

    public OrderFoodItems toOrderFoodItems(OrderFoodItemsDTO orderFoodItemsDTO) {
        OrderFoodItems orderFoodItems = new OrderFoodItems();
        if (orderFoodItemsDTO != null) {
            orderFoodItems.setFoodItem(foodItemMapper.toFoodItem(orderFoodItemsDTO.getFoodItem()));
            orderFoodItems.setQuantity(orderFoodItemsDTO.getQuantity());
        } else {
            return null;
        }
        return orderFoodItems;
    }
}
