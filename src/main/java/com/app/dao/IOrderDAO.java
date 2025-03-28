package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.Order;

public interface IOrderDAO {
    void placeOrder(int userId, Order order) throws DBException;
}
