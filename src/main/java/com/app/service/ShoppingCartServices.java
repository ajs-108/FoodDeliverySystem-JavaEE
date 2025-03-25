package com.app.service;

import com.app.common.exception.DBException;
import com.app.dao.IFoodItemDAO;
import com.app.dao.impl.FoodItemDAOImpl;
import com.app.dto.ShoppingCartDTO;
import com.app.mapper.FoodItemMapper;
import com.app.model.ShoppingCart;

public class ShoppingCartServices {
    private IFoodItemDAO foodItemDAO;
    private FoodItemMapper foodItemMapper;

    public ShoppingCartServices() {
        this.foodItemDAO = new FoodItemDAOImpl();
        this.foodItemMapper = new FoodItemMapper();
    }

    public void addFoodItem(ShoppingCartDTO shoppingCartDTO) throws DBException {

    }
}
