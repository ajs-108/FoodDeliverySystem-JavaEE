package com.app.service.jdbc;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.DBException;
import com.app.dao.jdbc.IOrderDAO;
import com.app.dao.jdbc.impl.OrderDAOImpl;
import com.app.dto.jdbc.OrderDTO;
import com.app.mapper.jdbc.OrderMapper;
import com.app.mapper.jdbc.ShoppingCartMapper;
import com.app.model.jdbc.ShoppingCart;

import java.util.List;

public class OrderServices {
    private IOrderDAO orderDAO;
    private OrderMapper orderMapper;
    private ShoppingCartServices shoppingCartServices;
    private ShoppingCartMapper shoppingCartMapper;

    public OrderServices() {
        orderDAO = new OrderDAOImpl();
        orderMapper = new OrderMapper();
        shoppingCartServices = new ShoppingCartServices();
        shoppingCartMapper = new ShoppingCartMapper();
    }

    public void placeOrder(int userId, OrderDTO orderDTO) throws DBException {
        ShoppingCart shoppingCart =
                shoppingCartMapper.toShoppingCart(shoppingCartServices.showShoppingCart(userId));
        int i = orderDAO.placeOrder(shoppingCart, orderMapper.toOrder(orderDTO));
        if (i > 1) {
            shoppingCartServices.deleteCartDataOfUser(userId);
        }
    }

    public List<OrderDTO> getAllOrder() throws DBException {
        return orderDAO.getAllOrders()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> getAllOrder(int userid, Roles roles) throws DBException {
        return orderDAO.getAllPreviousOrders(userid, roles)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> getAllOrder(OrderStatus orderStatus) throws DBException {
        return orderDAO.getAllOrders(orderStatus)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
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

    public OrderDTO getRecentOrderOfUser(int userId) throws DBException {
        return orderMapper.toDTO(orderDAO.getRecentOrderOfUser(userId));
    }

    public List<OrderDTO> getOrderAssignedToDP(int deliveryPersonId) throws DBException {
        return orderDAO.getOrderAssignedToDP(deliveryPersonId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> getCurrentOrderOfUser(int userId) throws DBException {
        return orderDAO.getCurrentOrderOfUser(userId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}
