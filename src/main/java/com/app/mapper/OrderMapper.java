package com.app.mapper;

import com.app.dto.OrderDTO;
import com.app.model.Order;

public class OrderMapper {
    private UserMapper userMapper = new UserMapper();
    private OrderFoodItemsMapper orderFoodItemsMapper = new OrderFoodItemsMapper();

    public OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        if (order != null) {
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setUser(userMapper.toDTO(order.getUser()));
            orderDTO.setDeliveryPersonId(order.getDeliveryPersonId());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setOrderDateTime(order.getOrderDateTime());
            orderDTO.setPaymentStatus(order.getPaymentStatus());
            orderDTO.setOrderFoodItems(
                    order.getOrderFoodItems()
                            .stream()
                            .map(orderFoodItemsMapper::toDTO)
                            .toList());
        } else {
            return null;
        }
        return orderDTO;
    }

    public Order toOrder(OrderDTO orderDTO) {
        Order order = new Order();
        if (orderDTO == null) {
            return null;
        }
        order.setOrderId(orderDTO.getOrderId());
        order.setUser(userMapper.toUser(orderDTO.getUser()));
        order.setDeliveryPersonId(order.getDeliveryPersonId());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setOrderDateTime(orderDTO.getOrderDateTime());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        if (orderDTO.getOrderFoodItems() != null) {
            order.setOrderFoodItems(
                    orderDTO.getOrderFoodItems()
                            .stream()
                            .map(orderFoodItemsMapper::toOrderFoodItems)
                            .toList());
        }
        order.setOrderFoodItems(null);
        return order;
    }
}
