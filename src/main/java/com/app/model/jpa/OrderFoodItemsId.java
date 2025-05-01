package com.app.model.jpa;

import com.app.model.common.FoodItem;

import java.io.Serializable;
import java.util.Objects;

public class OrderFoodItemsId implements Serializable {
    private JPAOrder order;
    private FoodItem foodItem;

    public OrderFoodItemsId() {
    }

    public OrderFoodItemsId(JPAOrder order, FoodItem foodItem) {
        this.order = order;
        this.foodItem = foodItem;
    }

    public JPAOrder getOrder() {
        return order;
    }

    public void setOrder(JPAOrder order) {
        this.order = order;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderFoodItemsId)) return false;
        OrderFoodItemsId that = (OrderFoodItemsId) o;
        return Objects.equals(order, that.order) && Objects.equals(foodItem, that.foodItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, foodItem);
    }

    @Override
    public String toString() {
        return "OrderFoodItemsId{" +
                "order=" + order +
                ", foodItem=" + foodItem +
                '}';
    }
}
