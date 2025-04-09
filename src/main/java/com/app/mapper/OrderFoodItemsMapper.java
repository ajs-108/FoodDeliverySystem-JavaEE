package com.app.mapper;

import com.app.dto.OrderFoodItemsDTO;
import com.app.model.OrderFoodItems;

public class OrderFoodItemsMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public OrderFoodItemsDTO toDTO(OrderFoodItems orderFoodItems) {
        OrderFoodItemsDTO orderFoodItemsDTO = new OrderFoodItemsDTO();
        if (orderFoodItems == null) {
            return null;
        }
        orderFoodItemsDTO.setFoodItemDTO(foodItemMapper.toDTO(orderFoodItems.getFoodItem()));
        orderFoodItemsDTO.setQuantity(orderFoodItems.getQuantity());
        orderFoodItemsDTO.setFoodItemsTotal(orderFoodItems.getFoodItemsTotal());
        return orderFoodItemsDTO;
    }

    public OrderFoodItems toOrderFoodItems(OrderFoodItemsDTO orderFoodItemsDTO) {
        OrderFoodItems orderFoodItems = new OrderFoodItems();
        if (orderFoodItemsDTO == null) {
            return null;
        }
        orderFoodItems.setFoodItem(foodItemMapper.toFoodItem(orderFoodItemsDTO.getFoodItemDTO()));
        orderFoodItems.setQuantity(orderFoodItemsDTO.getQuantity());
        orderFoodItems.setFoodItemsTotal(orderFoodItemsDTO.getFoodItemsTotal());
        return orderFoodItems;
    }
}
