package com.app.dao.jdbc;

import com.app.common.exception.DBException;
import com.app.model.jdbc.CartFoodItems;

public interface IOrderFoodItemsDAO {
    int addFoodItem(CartFoodItems cartFoodItems, int orderId) throws DBException;
}
