package com.app.dto;

import com.app.model.FoodItem;

public class OrderFoodItemsDTO {
    private FoodItemDTO foodItem;
    private int quantity;

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
        return "OrderFoodItems{" +
                "foodItem=" + foodItem +
                ", quantity=" + quantity +
                '}';
    }
}
