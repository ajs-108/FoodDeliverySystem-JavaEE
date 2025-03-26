package com.app.dto;

import com.app.model.FoodItem;

public class ShoppingCartDTO {
    private int userId;
    private FoodItemDTO foodItemDTO;
    private int quantity;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public FoodItemDTO getFoodItemDTO() {
        return foodItemDTO;
    }

    public void setFoodItemDTO(FoodItemDTO foodItemDTO) {
        this.foodItemDTO = foodItemDTO;
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
                ", foodItemDTO=" + foodItemDTO +
                ", quantity=" + quantity +
                '}';
    }
}
