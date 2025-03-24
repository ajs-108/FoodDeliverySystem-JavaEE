package com.app.model;

import java.util.List;

public class OrderFoodItems {
    private List<FoodItem> foodItemList;
    private Order order;
    private int quantity;

    public List<FoodItem> getFoodItemList() {
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
                "foodItemList=" + foodItemList +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }
}
