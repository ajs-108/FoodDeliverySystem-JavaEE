package com.app.model.jpa;

import com.app.model.FoodItem;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {
    private JPAUser user;
    private FoodItem foodItem;

    public CartId() {
    }

    public CartId(JPAUser user, FoodItem foodItem) {
        this.user = user;
        this.foodItem = foodItem;
    }

    public JPAUser getUser() {
        return user;
    }

    public void setUser(JPAUser user) {
        this.user = user;
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
        if (!(o instanceof CartId cartId)) return false;
        return Objects.equals(user, cartId.user) && Objects.equals(foodItem, cartId.foodItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, foodItem);
    }
}
