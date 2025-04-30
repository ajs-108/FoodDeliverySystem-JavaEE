package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.dto.jpa.order_food_items.GetOrderFoodItemsDTO;

import java.util.List;

public interface IOrderFoodItemRepository {
    public List<GetOrderFoodItemsDTO> findByOrder(int orderId) throws DBException;
}
