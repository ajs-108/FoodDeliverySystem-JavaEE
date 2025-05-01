package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IFoodItemRepository;
import com.app.model.common.FoodItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class FoodItemRepository implements IFoodItemRepository {
    @Override
    public void save(FoodItem foodItem) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(foodItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public FoodItem findById(int foodItemId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            FoodItem foodItem = em.find(FoodItem.class, foodItemId);
            tx.commit();
            return foodItem;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public FoodItem findFoodItemFromMenu(int foodItemId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            Query query = em.createNativeQuery("""
                    select * from menu where food_item_id = ?1;
                    """).setParameter(1, foodItemId);
            tx = em.getTransaction();
            tx.begin();
            FoodItem foodItem = (FoodItem) query.getSingleResult();
            tx.commit();
            return foodItem;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    public List<FoodItem> findAll() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<FoodItem> findAllQuery =
                    em.createQuery("SELECT fi FROM foodItem fi JOIN FETCH fi.category",
                            FoodItem.class);
            tx.begin();
            List<FoodItem> foodItems = new ArrayList<>();
            for (FoodItem foodItem : findAllQuery.getResultList()) {
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

    @Override
    public List<FoodItem> findMenu() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query query = em.createNativeQuery("""
                    select * from menu m, category c
                    where m.category_id = c.category_id
                    """);
            tx.begin();
            List<FoodItem> foodItems = new ArrayList<>();
            for (FoodItem foodItem : (List<FoodItem>) query.getResultList()) {
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

    @Override
    public void update(FoodItem foodItem) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.merge(foodItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void updateAvailability(int foodItemId, boolean isAvailable) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query update = em.createQuery("""
                            UPDATE foodItem fi set fi.isAvailable = ?1
                            WHERE fi.foodItemId = ?2
                            """)
                    .setParameter(1, isAvailable)
                    .setParameter(2, foodItemId);

            tx.begin();
            update.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void updateRating(int foodItemId, double rating) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query update = em.createQuery("""
                            UPDATE foodItem fi set fi.rating = ?1
                            WHERE fi.foodItemId = ?2
                            """)
                    .setParameter(1, rating)
                    .setParameter(2, foodItemId);

            tx.begin();
            update.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void removeFromMenu(int foodItemId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query query = em.createNativeQuery("""
                            delete from menu where food_item_id = ?1
                            """)
                    .setParameter(1, foodItemId);
            tx.begin();
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }
}
