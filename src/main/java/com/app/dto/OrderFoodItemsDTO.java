package com.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderFoodItemsDTO {
    private FoodItemDTO foodItemDTO;
    private int quantity;

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

    @Override
    public String toString() {
        return "OrderFoodItems{" +
                "foodItemDTO=" + foodItemDTO +
                ", quantity=" + quantity +
                '}';
    }
}
