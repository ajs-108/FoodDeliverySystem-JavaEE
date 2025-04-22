package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.jpa.JPAOrder;

import java.util.List;

public interface IOrderRepository {
    List<JPAOrder> getAllOrders() throws DBException;
}
