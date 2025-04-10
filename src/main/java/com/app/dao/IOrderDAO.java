package com.app.dao;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.DBException;
import com.app.model.Order;
import com.app.model.ShoppingCart;

import java.util.List;

public interface IOrderDAO {
    int placeOrder(ShoppingCart shoppingCart, Order order) throws DBException;

    List<Order> getAllOrders() throws DBException;

    List<Order> getAllPreviousOrders(int userId, Roles roles) throws DBException;

    List<Order> getAllOrders(OrderStatus orderStatus) throws DBException;

    Order getOrder(int orderId) throws DBException;

    void changeOrderStatus(int orderId, OrderStatus orderStatus) throws DBException;

    void assignDeliveryPerson(int orderId, int deliveryPersonId) throws DBException;

    Order getRecentOrderOfUser(int userId) throws DBException;
}
