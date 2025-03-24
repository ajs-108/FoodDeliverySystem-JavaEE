package com.app.model;

import java.util.List;

public class ShoppingCart {
    private List<FoodItem> foodItemList;
    private User user;
    private int quantity;

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "foodItemList=" + foodItemList +
                ", user=" + user +
                ", quantity=" + quantity +
                '}';
    }
}
