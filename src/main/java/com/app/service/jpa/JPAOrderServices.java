package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IOrderFoodItemRepository;
import com.app.dao.jpa.IOrderRepository;
import com.app.dao.jpa.impl.OrderFoodItemsRepository;
import com.app.dao.jpa.impl.OrderRepository;
import com.app.dto.jpa.order.GetOrderDTO;
import com.app.mapper.jpa.JPAOrderMapper;

import java.util.List;

public class JPAOrderServices {
    private IOrderRepository orderRepository;
    private IOrderFoodItemRepository orderFoodItemRepo;

    public JPAOrderServices() {
        orderRepository = new OrderRepository();
        orderFoodItemRepo = new OrderFoodItemsRepository();
    }

    public List<GetOrderDTO> findAll() throws DBException {
        List<GetOrderDTO> orderList = orderRepository.findAll();
        for (GetOrderDTO order : orderList) {
            order.setOrderFoodItems(
                    orderFoodItemRepo.findByOrder(order.getOrderId()));
        }
        return orderList;
    }
}
