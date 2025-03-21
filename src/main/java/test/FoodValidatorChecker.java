package test;

import common.exception.ApplicationException;
import controller.validation.FoodItemValidator;
import dto.FoodItemDTO;
import model.Category;

public class FoodValidatorChecker {

    public static void main(String[] args) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();
        Category category = new Category();
        foodItemDTO.setFoodItemId(2);
        foodItemDTO.setFoodName("Vanilla Cake");
        foodItemDTO.setFoodDescription("");
        foodItemDTO.setCategory(category);
        foodItemDTO.setPrice(23453);
        foodItemDTO.setImagePath(" fhgfh");
        try {
            FoodItemValidator.validateFoodItem(foodItemDTO);
            System.out.println("success");
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        }
    }
}
