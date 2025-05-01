package com.app.mapper.jpa;

import com.app.dto.jpa.order.JPAOrderDTO;
import com.app.mapper.common.FoodItemMapper;
import com.app.model.jpa.JPAOrder;

public class JPAOrderMapper {
    private FoodItemMapper foodItemMapper = new FoodItemMapper();
    private JPAUserMapper userMapper = new JPAUserMapper();

    public JPAOrderDTO toDTO(JPAOrder order) {
        JPAOrderDTO orderDTO = new JPAOrderDTO();
        if (order == null) {
            return null;
        }
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setUser(userMapper.toDTO(order.getUser()));
        orderDTO.setDeliveryPerson(userMapper.toDTO(order.getDeliveryPerson()));
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setOrderDateTime(order.getOrderDateTime());
        orderDTO.setPaymentStatus(order.getPaymentStatus());
        if (order.getFoodItems() != null) {
            orderDTO.setFoodItems(
                    order.getFoodItems()
                            .stream()
                            .map(foodItemMapper::toDTO)
                            .toList());
        } else {
            orderDTO.setFoodItems(null);
        }
        return orderDTO;
    }

    public JPAOrder toOrder(JPAOrderDTO orderDTO) {
        JPAOrder order = new JPAOrder();
        if (orderDTO == null) {
            return null;
        }
        order.setOrderId(orderDTO.getOrderId());
        order.setUser(userMapper.toUser(orderDTO.getUser()));
        order.setDeliveryPerson(userMapper.toUser(orderDTO.getDeliveryPerson()));
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setOrderDateTime(orderDTO.getOrderDateTime());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        if (orderDTO.getFoodItems() != null) {
            order.setFoodItems(
                    orderDTO.getFoodItems()
                            .stream()
                            .map(foodItemMapper::toFoodItem)
                            .toList());
        } else {
            order.setFoodItems(null);
        }
        return order;
    }
}
