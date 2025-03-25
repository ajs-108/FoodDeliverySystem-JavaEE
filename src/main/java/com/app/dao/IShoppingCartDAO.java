package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.ShoppingCart;

public interface IShoppingCartDAO {
    void addFoodItem(ShoppingCart shoppingCart) throws DBException;

    void removeFoodItem(int foodItemId) throws DBException;

    void updateQuantity(int foodItemId, int quantity) throws DBException;
}
