package com.app.test;

import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.controller.validation.FoodItemValidator;
import com.app.dto.FoodItemDTO;
import com.app.model.Category;

public class FoodValidatorChecker {

    public static void main(String[] args) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();
        foodItemDTO.setFoodItemId(2);
        foodItemDTO.setFoodName("Vanilla Cake");
        foodItemDTO.setFoodDescription("");
        foodItemDTO.setPrice(23453);
        foodItemDTO.setImagePath(" fhgfh");
        try {
            FoodItemValidator.validateFoodItem(foodItemDTO);
            System.out.println("success");
        } catch (ApplicationException | DBException e) {
            System.out.println(e.getMessage());
        }
    }
}
