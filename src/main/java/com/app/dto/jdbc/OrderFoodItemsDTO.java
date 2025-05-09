package com.app.dto.jdbc;

import com.app.dto.common.FoodItemDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderFoodItemsDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FoodItemDTO foodItemDTO;
    private int quantity;
    private double foodItemsTotal;

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

    public double getFoodItemsTotal() {
        return foodItemsTotal;
    }

    public void setFoodItemsTotal(double foodItemsTotal) {
        this.foodItemsTotal = foodItemsTotal;
    }

    @Override
    public String toString() {
        return "OrderFoodItemsDTO{" +
                "foodItemDTO=" + foodItemDTO +
                ", quantity=" + quantity +
                ", foodItemsTotal=" + foodItemsTotal +
                '}';
    }
}
