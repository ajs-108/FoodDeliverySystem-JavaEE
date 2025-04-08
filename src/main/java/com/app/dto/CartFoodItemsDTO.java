package com.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL,
        content = JsonInclude.Include.NON_DEFAULT)
public class CartFoodItemsDTO {
    private FoodItemDTO foodItemDTO;
    private int quantity;
    private double beforeDiscountPrice;
    private double afterDiscountPrice;

    public FoodItemDTO getFoodItemDTO() {
        return foodItemDTO;
    }

    public void setFoodItemDTO(FoodItemDTO foodItemDTO) {
        this.foodItemDTO = foodItemDTO;
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
        return "CartFoodItemsDTO{" +
                "foodItemDTO=" + foodItemDTO +
                ", quantity=" + quantity +
                ", beforeDiscountPrice=" + beforeDiscountPrice +
                ", afterDiscountPrice=" + afterDiscountPrice +
                '}';
    }
}
