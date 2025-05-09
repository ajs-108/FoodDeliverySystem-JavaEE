package com.app.dao.jdbc;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.DBException;
import com.app.model.jdbc.Order;
import com.app.model.jdbc.ShoppingCart;

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

    List<Order> getOrderAssignedToDP(int deliveryPersonId) throws DBException;

    List<Order> getCurrentOrderOfUser(int userId) throws DBException;
}
