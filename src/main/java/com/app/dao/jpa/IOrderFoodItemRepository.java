package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.dto.jpa.order_food_items.GetOrderFoodItemsDTO;
import com.app.model.jpa.JPACart;
import com.app.model.jpa.JPAOrderFoodItems;

import java.util.List;

public interface IOrderFoodItemRepository {

    void save(JPAOrderFoodItems orderFoodItems) throws DBException;

    List<GetOrderFoodItemsDTO> findByOrder(int orderId) throws DBException;
}
