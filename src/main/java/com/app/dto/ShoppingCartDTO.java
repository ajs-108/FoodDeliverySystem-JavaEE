package com.app.dto;

import com.app.model.FoodItem;
import com.app.model.User;

import java.util.List;

public class ShoppingCartDTO {
    private FoodItem foodItem;
    private User user;
    private int quantity;

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
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
                "foodItemList=" + foodItem +
                ", user=" + user +
                ", quantity=" + quantity +
                '}';
    }
}
