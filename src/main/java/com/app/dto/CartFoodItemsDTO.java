package com.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartFoodItemsDTO {
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
        return "CartFoodItemsDTO{" +
                "foodItemDTO=" + foodItemDTO +
                ", quantity=" + quantity +
                '}';
    }
}
