package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.dto.jpa.GetOrderDTO;
import com.app.model.jpa.JPAOrder;
import com.app.model.jpa.JPAOrderFoodItems;

import java.util.List;

public interface IOrderRepository {
    List<GetOrderDTO> getAllOrders() throws DBException;
}
