package com.app.dto.jpa.order_food_items;

import com.app.dto.FoodItemDTO;
import com.app.dto.jpa.order.JPAOrderDTO;

public class JPAOrderFoodItemsDTO {
    private JPAOrderDTO order;
    private FoodItemDTO foodItem;
    private short quantity;
    private double foodItemsTotal;

    public JPAOrderDTO getOrder() {
        return order;
    }

    public void setOrder(JPAOrderDTO order) {
        this.order = order;
    }

    public FoodItemDTO getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItemDTO foodItem) {
        this.foodItem = foodItem;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
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
        return "JPAOrderFoodItemsDTO{" +
                "order=" + order +
                ", foodItem=" + foodItem +
                ", quantity=" + quantity +
                ", foodItemsTotal=" + foodItemsTotal +
                '}';
    }
}
