package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.CartFoodItems;

public interface IOrderFoodItemsDAO {
    int addFoodItem(CartFoodItems cartFoodItems, int orderId) throws DBException;
}
