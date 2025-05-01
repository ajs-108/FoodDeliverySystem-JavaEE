package com.app.model.jdbc;

import com.app.model.common.FoodItem;

public class CartFoodItems {
    private FoodItem foodItem;
    private int quantity;
    private double beforeDiscountPrice;
    private double afterDiscountPrice;

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

    public double getBeforeDiscountPrice() {
        return beforeDiscountPrice;
    }

    public void setBeforeDiscountPrice(double beforeDiscountPrice) {
        this.beforeDiscountPrice = beforeDiscountPrice;
    }

    public double getAfterDiscountPrice() {
        return afterDiscountPrice;
    }

    public void setAfterDiscountPrice(double afterDiscountPrice) {
        this.afterDiscountPrice = afterDiscountPrice;
    }

    @Override
    public String toString() {
        return "CartFoodItems{" +
                "foodItem=" + foodItem +
                ", quantity=" + quantity +
                ", beforeDiscountPrice=" + beforeDiscountPrice +
                ", afterDiscountPrice=" + afterDiscountPrice +
                '}';
    }
}
