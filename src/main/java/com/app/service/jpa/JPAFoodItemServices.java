package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IFoodItemRepository;
import com.app.dao.jpa.impl.FoodItemRepository;
import com.app.dto.FoodItemDTO;
import com.app.mapper.FoodItemMapper;

import java.util.List;

public class JPAFoodItemServices {
    private IFoodItemRepository foodItemRepo;
    private FoodItemMapper foodItemMapper;

    public JPAFoodItemServices() {
        foodItemRepo = new FoodItemRepository();
        foodItemMapper = new FoodItemMapper();
    }

    public List<FoodItemDTO> findAll() throws DBException {
        return foodItemRepo.findAll()
                .stream()
                .map(foodItemMapper::toDTO)
                .toList();
    }
}
