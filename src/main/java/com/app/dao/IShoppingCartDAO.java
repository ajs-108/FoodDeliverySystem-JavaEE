package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.ShoppingCart;

public interface IShoppingCartDAO {
    void addFoodItem(ShoppingCart shoppingCart) throws DBException;

    void removeFoodItem(int userId, int foodItemId) throws DBException;

    void updateQuantity(ShoppingCart shoppingCart) throws DBException;

    ShoppingCart getShoppingCart(int userId) throws DBException;

    void deleteCartOfUser(int userId) throws DBException;
}
