package com.app.model.jpa;

import com.app.model.common.FoodItem;
import jakarta.persistence.*;

@Entity(name = "orderFoodItems")
@Table(name = "order_food_items")
@IdClass(OrderFoodItemsId.class)
public class JPAOrderFoodItems {

    @Id
    @ManyToOne(targetEntity = JPAOrder.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id",
            nullable = false)
    private JPAOrder order;

    @Id
    @ManyToOne(targetEntity = FoodItem.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "food_item_id", referencedColumnName = "food_item_id",
            nullable = false)
    private FoodItem foodItem;

    @Column(name = "quantity", nullable = false)
    private short quantity;

    @Column(name = "food_items_total")
    private double foodItemsTotal;

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
        return "JPAOrderFoodItems{" +
                "order=" + order +
                ", foodItem=" + foodItem +
                ", quantity=" + quantity +
                ", foodItemsTotal=" + foodItemsTotal +
                '}';
    }
}
