package com.app.model;

public class OrderFoodItems {
    private FoodItem foodItem;
    private int quantity;

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

    @Override
    public String toString() {
        return "OrderFoodItems{" +
                "foodItem=" + foodItem +
                ", quantity=" + quantity +
                '}';
    }
}
