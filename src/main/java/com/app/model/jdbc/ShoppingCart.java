package com.app.model.jdbc;

import java.util.List;

public class ShoppingCart {
    private int userId;
    private List<CartFoodItems> cartFoodItemsList;
    private double totalPrice;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<CartFoodItems> getCartFoodItemsList() {
        return cartFoodItemsList;
    }

    public void setCartFoodItemsList(List<CartFoodItems> cartFoodItemsList) {
        this.cartFoodItemsList = cartFoodItemsList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "userId=" + userId +
                ", cartFoodItemsList=" + cartFoodItemsList +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
