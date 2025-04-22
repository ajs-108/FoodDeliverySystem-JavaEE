package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IOrderRepository;
import com.app.model.jpa.JPAOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderRepository implements IOrderRepository {

    @Override
    public List<JPAOrder> getAllOrders() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<JPAOrder> findAllQuery =
                    em.createQuery("SELECT o from order o", JPAOrder.class);
            tx.begin();
            List<JPAOrder> orderList = findAllQuery.getResultList();
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
