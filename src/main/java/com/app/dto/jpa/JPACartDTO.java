package com.app.dto.jpa;

import com.app.dto.FoodItemDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JPACartDTO {
    private JPAUserDTO user;
    private FoodItemDTO foodItem;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private short quantity;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double beforeDiscountPrice;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double afterDiscountPrice;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double totalPrice;

    public JPAUserDTO getUser() {
        return user;
    }

    public void setUser(JPAUserDTO user) {
        this.user = user;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "JPACartDTO{" +
                "user=" + user +
                ", foodItem=" + foodItem +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", beforeDiscountPrice=" + beforeDiscountPrice +
                ", afterDiscountPrice=" + afterDiscountPrice +
                '}';
    }
}
