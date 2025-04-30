package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.dto.jpa.order.GetOrderDTO;

import java.util.List;

public interface IOrderRepository {
    List<GetOrderDTO> findAll() throws DBException;
}
