package com.app.service;

import com.app.common.exception.DBException;
import com.app.dao.IOrderDAO;
import com.app.dao.impl.OrderDAOImpl;
import com.app.dto.OrderDTO;
import com.app.mapper.OrderMapper;

import java.util.List;

public class OrderServices {
    private IOrderDAO orderDAO;
    private OrderMapper orderMapper;

    public OrderServices() {
        orderDAO = new OrderDAOImpl();
        orderMapper = new OrderMapper();
    }

    public void placeOrder(int userId, OrderDTO orderDTO) throws DBException {
        orderDAO.placeOrder(userId, orderMapper.toOrder(orderDTO));
    }

    public List<OrderDTO> getAllOrder() throws DBException {
        return orderDAO.getAllOrder()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}
