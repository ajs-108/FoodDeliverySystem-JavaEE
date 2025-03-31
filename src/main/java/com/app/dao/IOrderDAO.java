package com.app.dao;

import com.app.common.enums.OrderStatus;
import com.app.common.exception.DBException;
import com.app.model.Order;

import java.util.List;

public interface IOrderDAO {
    void placeOrder(int userId, Order order) throws DBException;
    List<Order> getAllOrder() throws DBException;
    Order getOrder(int orderId, int userId) throws DBException;
    void changeOrderStatus(int orderId, OrderStatus orderStatus) throws DBException;
}
