package com.app.model;

public class CartFoodItems {
    private FoodItem foodItem;
    private int quantity;
    private double priceBeforeDiscount;
    private double priceAfterDiscount;

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
        return "CartFoodItems{" +
                "foodItem=" + foodItem +
                ", quantity=" + quantity +
                '}';
    }
}
