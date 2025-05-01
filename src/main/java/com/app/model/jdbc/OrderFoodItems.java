package com.app.model.jdbc;

import com.app.model.common.FoodItem;

public class OrderFoodItems {
    private FoodItem foodItem;
    private int quantity;
    private double foodItemsTotal;

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getFoodItemsTotal() {
        return foodItemsTotal;
    }

    public void setFoodItemsTotal(double foodItemsTotal) {
        this.foodItemsTotal = foodItemsTotal;
    }

    @Override
    public String toString() {
        return "OrderFoodItems{" +
                "foodItem=" + foodItem +
                ", quantity=" + quantity +
                ", foodItemsTotal=" + foodItemsTotal +
                '}';
    }
}
