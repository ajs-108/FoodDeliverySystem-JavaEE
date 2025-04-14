package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IJPAFoodItemDAO;
import com.app.model.FoodItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JPAFoodItemDAO implements IJPAFoodItemDAO {
    public List<FoodItem> findAll() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<FoodItem> findAllQuery =
                    em.createQuery("SELECT fi from FoodItem fi", FoodItem.class);
            tx.begin();
            List<FoodItem> foodItems = findAllQuery.getResultList();
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
