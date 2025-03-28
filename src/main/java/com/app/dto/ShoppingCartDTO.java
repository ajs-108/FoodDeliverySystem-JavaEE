package com.app.dto;

public class ShoppingCartDTO {
    private int userId;
    private FoodItemDTO foodItem;
    private int quantity;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public FoodItemDTO getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItemDTO foodItem) {
        this.foodItem = foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "userId=" + userId +
                ", foodItemDTO=" + foodItem +
                ", quantity=" + quantity +
                '}';
    }
}
