package com.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartDTO {
    private int userId;
    private List<CartFoodItemsDTO> cartFoodItemsDTOList;
    private double totalPrice;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<CartFoodItemsDTO> getCartFoodItemsDTOList() {
        return cartFoodItemsDTOList;
    }

    public void setCartFoodItemsDTOList(List<CartFoodItemsDTO> cartFoodItemsDTOList) {
        this.cartFoodItemsDTOList = cartFoodItemsDTOList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "userId=" + userId +
                ", cartFoodItemsDTOList=" + cartFoodItemsDTOList +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
