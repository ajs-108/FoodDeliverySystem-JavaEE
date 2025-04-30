package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IOrderFoodItemRepository;
import com.app.dto.jpa.order_food_items.GetOrderFoodItemsDTO;
import com.app.model.jpa.JPAOrderFoodItems;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderFoodItemsRepository implements IOrderFoodItemRepository {
    @Override
    public void save(JPAOrderFoodItems orderFoodItems) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(orderFoodItems);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<GetOrderFoodItemsDTO> findByOrder(int orderId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderFoodItemsDTO> findAllQuery =
                    em.createQuery("""
                                    select fi.foodName, fi.price, fi.discount, fi.imagePath, ofi.quantity, ofi.foodItemsTotal
                                    from orderFoodItems ofi
                                    join ofi.foodItem fi
                                    where ofi.order.orderId = ?1
                                    """, GetOrderFoodItemsDTO.class)
                            .setParameter(1, orderId);
            tx.begin();
            List<GetOrderFoodItemsDTO> orderFoodItems = findAllQuery.getResultList();
            tx.commit();
            return orderFoodItems;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }
}
