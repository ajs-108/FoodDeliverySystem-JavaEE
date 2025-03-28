package com.app.service;

import com.app.common.exception.DBException;
import com.app.dao.IShoppingCartDAO;
import com.app.dao.impl.ShoppingCartDAO;
import com.app.dto.ShoppingCartDTO;
import com.app.mapper.ShoppingCartMapper;

import java.util.List;

public class ShoppingCartServices {
    private IShoppingCartDAO shoppingCartDAO;
    private ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartServices() {
        this.shoppingCartDAO = new ShoppingCartDAO();
        this.shoppingCartMapper = new ShoppingCartMapper();
    }

    public void addFoodItem(ShoppingCartDTO shoppingCartDTO) throws DBException {
        shoppingCartDAO.addFoodItem(shoppingCartMapper.toShoppingCart(shoppingCartDTO));
    }

    public void removeFoodItem(int userId, int foodItemId) throws DBException {
        shoppingCartDAO.removeFoodItem(userId, foodItemId);
    }

    public void updateQuantity(ShoppingCartDTO shoppingCartDTO) throws DBException {
        shoppingCartDAO.updateQuantity(shoppingCartMapper.toShoppingCart(shoppingCartDTO));
    }

    public List<ShoppingCartDTO> showShoppingCart(int userId) throws DBException {
        return shoppingCartDAO.getShoppingCart(userId)
                .stream()
                .map(shoppingCartMapper::toDTO)
                .toList();
    }

    public boolean isFoodItemExists(ShoppingCartDTO shoppingCartDTO) throws DBException {
        for (ShoppingCartDTO cart : showShoppingCart(shoppingCartDTO.getUserId())) {
            if (shoppingCartDTO.getFoodItem().getFoodItemId() == cart.getFoodItem().getFoodItemId()) {
                return true;
            }
        }
        return false;
    }
}
