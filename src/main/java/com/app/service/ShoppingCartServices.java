package com.app.service;

import com.app.common.exception.DBException;
import com.app.dao.IShoppingCartDAO;
import com.app.dao.impl.ShoppingCartDAOImpl;
import com.app.dto.CartFoodItemsDTO;
import com.app.dto.ShoppingCartDTO;
import com.app.mapper.ShoppingCartMapper;

import java.util.List;
import java.util.Objects;

public class ShoppingCartServices {
    private IShoppingCartDAO shoppingCartDAO;
    private ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartServices() {
        this.shoppingCartDAO = new ShoppingCartDAOImpl();
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

    public ShoppingCartDTO showShoppingCart(int userId) throws DBException {
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDTO(shoppingCartDAO.getShoppingCart(userId));
        List<CartFoodItemsDTO> cartFoodItemsDTOList = shoppingCartDTO.getCartFoodItemsDTOList();
        double totalPrice = 0;
        if (cartFoodItemsDTOList != null) {
            for (CartFoodItemsDTO cartFoodItemsDTO : cartFoodItemsDTOList) {
                totalPrice += calculateTotalPrice(cartFoodItemsDTO.getFoodItemDTO().getPrice(),
                        cartFoodItemsDTO.getFoodItemDTO().getDiscount(), cartFoodItemsDTO.getQuantity());
            }
        }
        shoppingCartDTO.setTotalPrice(totalPrice);
        return shoppingCartDTO;
    }

    public boolean isFoodItemExists(ShoppingCartDTO shoppingCartDTO) throws DBException {
        List<CartFoodItemsDTO> cartFoodItemsListDTO =
                showShoppingCart(shoppingCartDTO.getUserId()).getCartFoodItemsDTOList();
        if (cartFoodItemsListDTO != null) {
            for (CartFoodItemsDTO cartFoodItemsDTO : cartFoodItemsListDTO) {
                if (Objects.equals(cartFoodItemsDTO.getFoodItemDTO().getFoodItemId(),
                        shoppingCartDTO.getCartFoodItemsDTOList().get(0).getFoodItemDTO().getFoodItemId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isUserExists(ShoppingCartDTO shoppingCartDTO) throws DBException {
        ShoppingCartDTO shoppingCart =
                showShoppingCart(shoppingCartDTO.getUserId());
        return Objects.equals(shoppingCart.getUserId(),
                shoppingCartDTO.getUserId());
    }

    public void deleteCartDataOfUser(int userId) throws DBException {
        shoppingCartDAO.deleteCartOfUser(userId);
    }

    public double calculateTotalPrice(double price, double discount, int quantity) {
        double totalPrice = 0;
        double discountedPrice = Math.round((price - (price * (discount/100))) * quantity);
        totalPrice += discountedPrice;
        return totalPrice;
    }
}
