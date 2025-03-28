package com.app.mapper;

import com.app.dto.OrderDTO;
import com.app.dto.OrderFoodItemsDTO;
import com.app.dto.UserDTO;
import com.app.model.Order;
import com.app.model.OrderFoodItems;

public class OrderMapper {
    private UserMapper userMapper = new UserMapper();
    private OrderFoodItemsMapper orderFoodItemsMapper = new OrderFoodItemsMapper();

    public OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        if (order != null) {
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setUser(userMapper.toDTO(order.getUser()));
            orderDTO.setDeliveryPersonId(order.getUser().getUserId());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setOrderDateTime(order.getOrderDateTime());
            orderDTO.setPaymentStatus(order.getPaymentStatus());
        } else {
            return null;
        }
        return orderDTO;
    }

    public Order toOrder(OrderDTO orderDTO) {
        Order order = new Order();
        if (orderDTO != null) {
            order.setOrderId(orderDTO.getOrderId());
            order.setUser(userMapper.toUser(orderDTO.getUser()));
            order.setDeliveryPersonId(orderDTO.getUser().getUserId());
            order.setTotalPrice(orderDTO.getTotalPrice());
            order.setOrderStatus(orderDTO.getOrderStatus());
            order.setOrderDateTime(orderDTO.getOrderDateTime());
            order.setPaymentStatus(orderDTO.getPaymentStatus());
        } else {
            return null;
        }
        return order;
    }
}
