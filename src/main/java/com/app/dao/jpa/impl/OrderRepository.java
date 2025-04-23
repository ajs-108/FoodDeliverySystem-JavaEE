package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IOrderRepository;
import com.app.dto.jpa.GetOrderDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderRepository implements IOrderRepository {

    @Override
    public List<GetOrderDTO> getAllOrders() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findAllQuery =
                    em.createQuery("""
                            select ofi from orderFoodItems ofi
                            """, GetOrderDTO.class);
            tx.begin();
            List<GetOrderDTO> orderList = findAllQuery.getResultList();
            tx.commit();
            return orderList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }
}
