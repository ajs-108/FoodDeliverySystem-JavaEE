package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.Order;

import java.util.List;

public interface IOrderDAO {
    void placeOrder(int userId, Order order) throws DBException;
    List<Order> getAllOrder() throws DBException;
}
