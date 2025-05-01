package com.app.model.jpa;

import com.app.model.common.FoodItem;
import jakarta.persistence.*;

@Entity(name = "cart")
@Table(name = "shopping_cart")
@IdClass(CartId.class)
public class JPACart {

    @Id
    @OneToOne(targetEntity = JPAUser.class, cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
            nullable = false)
    private JPAUser user;

    @Id
    @OneToOne(targetEntity = FoodItem.class, cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "food_item_id", referencedColumnName = "food_item_id",
            nullable = false)
    private FoodItem foodItem;

    @Column(name = "quantity", nullable = false)
    private short quantity;

    @Transient
    private double totalPrice;

    @Transient
    private double beforeDiscountPrice;

    @Transient
    private double afterDiscountPrice;

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

    public short getQuantity() {

        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
        return "JPACart{" +
                "user=" + user +
                ", foodItem=" + foodItem +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", beforeDiscountPrice=" + beforeDiscountPrice +
                ", afterDiscountPrice=" + afterDiscountPrice +
                '}';
    }
}
