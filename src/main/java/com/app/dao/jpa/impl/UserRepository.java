package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IUserRepository;
import com.app.model.jpa.JPAUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserRepository implements IUserRepository {

    @Override
    public List<JPAUser> findAll(int roleId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<JPAUser> findAllQuery =
                    em.createQuery("SELECT u FROM user u where role.roleId = :roleId", JPAUser.class)
                            .setParameter("roleId", roleId);
            tx.begin();
            List<JPAUser> jpaUsers = findAllQuery.getResultList();
            tx.commit();
            return jpaUsers;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }
}
