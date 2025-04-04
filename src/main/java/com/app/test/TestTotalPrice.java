package com.app.test;

import com.app.dto.CartFoodItemsDTO;
import com.app.dto.FoodItemDTO;
import com.app.service.ShoppingCartServices;

import java.util.ArrayList;
import java.util.List;

public class TestTotalPrice {

    public static void main(String[] args) {
        ShoppingCartServices shoppingCartServices = new ShoppingCartServices();
        List<CartFoodItemsDTO> cartFoodItemsDTOList = new ArrayList<>();
        CartFoodItemsDTO cart1 = new CartFoodItemsDTO();
        CartFoodItemsDTO cart2 = new CartFoodItemsDTO();
        CartFoodItemsDTO cart3 = new CartFoodItemsDTO();
        FoodItemDTO foodItem1 = new FoodItemDTO();
        FoodItemDTO foodItem2 = new FoodItemDTO();
        FoodItemDTO foodItem3 = new FoodItemDTO();
        foodItem1.setPrice(30);
        foodItem1.setDiscount(33.334);
        foodItem2.setPrice(70);
        foodItem2.setDiscount(0);
        foodItem3.setPrice(160);
        foodItem3.setDiscount(0);
        cart1.setFoodItemDTO(foodItem1);
        cart1.setQuantity(2);
        cart2.setFoodItemDTO(foodItem2);
        cart2.setQuantity(2);
        cart3.setFoodItemDTO(foodItem3);
        cart3.setQuantity(1);
        cartFoodItemsDTOList.add(cart1);
        cartFoodItemsDTOList.add(cart2);
        cartFoodItemsDTOList.add(cart3);
        double totalPrice = 0;
        for (CartFoodItemsDTO cartFoodItemsDTO : cartFoodItemsDTOList) {
            totalPrice += shoppingCartServices.calculateTotalPrice(cartFoodItemsDTO.getFoodItemDTO().getPrice(),
                    cartFoodItemsDTO.getFoodItemDTO().getDiscount(), cartFoodItemsDTO.getQuantity());

        }
        System.out.println(totalPrice);
    }
}
