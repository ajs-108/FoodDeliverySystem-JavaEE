package com.app.service;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.DBException;
import com.app.dao.IOrderDAO;
import com.app.dao.impl.OrderDAOImpl;
import com.app.dto.OrderDTO;
import com.app.mapper.OrderMapper;

import java.util.List;

public class OrderServices {
    private IOrderDAO orderDAO;
    private OrderMapper orderMapper;
    private ShoppingCartServices shoppingCartServices;

    public OrderServices() {
        orderDAO = new OrderDAOImpl();
        orderMapper = new OrderMapper();
        shoppingCartServices = new ShoppingCartServices();
    }

    public void placeOrder(int userId, OrderDTO orderDTO) throws DBException {
        int i = orderDAO.placeOrder(userId, orderMapper.toOrder(orderDTO));
        if (i > 1) {
            shoppingCartServices.deleteCartDataOfUser(userId);
        }
    }

    public List<OrderDTO> getAllOrder() throws DBException {
        return orderDAO.getAllOrder()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> getAllOrder(int userid, Roles roles) throws DBException {
        return orderDAO.getAllOrder(userid, roles)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> getAllOrder(OrderStatus orderStatus) throws DBException {
        return orderDAO.getAllOrder(orderStatus)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public OrderDTO getOrder(int orderId, int userId) throws DBException {
        return orderMapper.toDTO(orderDAO.getOrder(orderId, userId));
    }

    public OrderDTO getOrder(int orderId) throws DBException {
        return orderMapper.toDTO(orderDAO.getOrder(orderId));
    }

    public void updateStatus(int orderId, OrderStatus orderStatus) throws DBException {
        orderDAO.changeOrderStatus(orderId, orderStatus);
    }

    public void assignDeliveryPerson(int orderId, int deliveryPersonId) throws DBException {
        orderDAO.assignDeliveryPerson(orderId, deliveryPersonId);
    }
}
