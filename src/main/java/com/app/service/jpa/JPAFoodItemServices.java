package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IFoodItemRepository;
import com.app.dao.jpa.impl.FoodItemRepository;
import com.app.model.FoodItem;

import java.util.List;

public class JPAFoodItemServices {
    private IFoodItemRepository foodItemRepo;

    public JPAFoodItemServices() {
        foodItemRepo = new FoodItemRepository();
    }

    public List<FoodItem> findAll() throws DBException {
        return foodItemRepo.findAll();
    }
}
