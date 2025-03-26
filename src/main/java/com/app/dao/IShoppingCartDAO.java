package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.ShoppingCart;

import java.util.List;

public interface IShoppingCartDAO {
    void addFoodItem(ShoppingCart shoppingCart) throws DBException;

    void removeFoodItem(int userId, int foodItemId) throws DBException;

    void updateQuantity(ShoppingCart shoppingCart) throws DBException;

    List<ShoppingCart> getShoppingCart(int userId) throws DBException;
}
