package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IFoodItemRepository;
import com.app.model.FoodItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class FoodItemRepository implements IFoodItemRepository {
    public List<FoodItem> findAll() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<FoodItem> findAllQuery =
                    em.createQuery("SELECT fi FROM foodItem fi JOIN FETCH fi.category",
                            FoodItem.class);
            tx.begin();
            List<FoodItem> foodItems = new ArrayList<>();
            for(FoodItem foodItem : findAllQuery.getResultList()) {
                FoodItem item = new FoodItem();
                item.setFoodItemId(foodItem.getFoodItemId());
                item.setFoodName(foodItem.getFoodName());
                item.setFoodDescription(foodItem.getFoodDescription());
                item.setPrice(foodItem.getPrice());
                item.setDiscount(foodItem.getDiscount());
                item.setIsAvailable(foodItem.getIsAvailable());
                item.setCategory(foodItem.getCategory());
                item.setCreatedOn(foodItem.getCreatedOn());
                item.setUpdatedOn(foodItem.getUpdatedOn());
                item.setImagePath(foodItem.getImagePath());
                item.setRating(foodItem.getRating());
                foodItems.add(item);
            }
            tx.commit();
            return foodItems;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }
}
