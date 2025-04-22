package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IOrderRepository;
import com.app.dao.jpa.impl.OrderRepository;
import com.app.dto.jpa.JPAOrderDTO;
import com.app.mapper.jpa.JPAOrderMapper;

import java.util.List;

public class JPAOrderServices {
    private IOrderRepository orderRepository;
    private JPAOrderMapper orderMapper;

    public JPAOrderServices() {
        orderRepository = new OrderRepository();
        orderMapper = new JPAOrderMapper();
    }

    public List<JPAOrderDTO> findAll() throws DBException {
        return orderRepository.getAllOrders()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}
