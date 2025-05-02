package com.app.dao.jpa.impl;

import com.app.common.enums.AccountStatus;
import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IUserRepository;
import com.app.model.jpa.JPAUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserRepository implements IUserRepository {
    private static final String EMAIL = "email";

    @Override
    public void save(JPAUser user) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            Query query = em.createNativeQuery("""
                            insert into user_(first_name, last_name, email, password_, phone_number, address, role_id)
                            values (?1,?2,?3,?4,?5,?6,?7)
                            """, JPAUser.class)
                    .setParameter(1, user.getFirstName())
                    .setParameter(2, user.getLastName())
                    .setParameter(3, user.getEmail())
                    .setParameter(4, user.getPassword())
                    .setParameter(5, user.getPhoneNumber())
                    .setParameter(6, user.getAddress())
                    .setParameter(7, user.getRole().getRoleId());
            tx = em.getTransaction();
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

    @Override
    public List<JPAUser> findAll(int roleId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<JPAUser> findAll =
                    em.createQuery("""
                                    SELECT u FROM user u JOIN FETCH role r
                                    WHERE r.roleId = :roleId
                                    """, JPAUser.class)
                            .setParameter("roleId", roleId);
            tx.begin();
            List<JPAUser> jpaUsers = findAll.getResultList();
            tx.commit();
            return jpaUsers;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public JPAUser find(int userId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<JPAUser> find = em.createQuery("""
                            SELECT u FROM user u JOIN FETCH role r
                            WHERE u.userId = :id
                            """, JPAUser.class)
                    .setParameter("id", userId);
            tx.begin();
            JPAUser jpaUser = find.getSingleResultOrNull();
            tx.commit();
            return jpaUser;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public JPAUser find(String email) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<JPAUser> find = em.createQuery("""
                            SELECT u FROM user u JOIN FETCH role r
                            WHERE u.email = :email
                            """, JPAUser.class)
                    .setParameter(EMAIL, email);
            tx.begin();
            JPAUser jpaUser = find.getSingleResult();
            tx.commit();
            return jpaUser;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public JPAUser findLoginCredentials(String email) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<JPAUser> find =
                    em.createQuery("""
                                    SELECT u.email, u.password FROM user u
                                    WHERE email = :email
                                    """, JPAUser.class)
                            .setParameter(EMAIL, email);
            tx.begin();
            JPAUser jpaUser = find.getSingleResult();
            tx.commit();
            return jpaUser;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void update(int userId, JPAUser user) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query update =
                    em.createQuery("""
                                    UPDATE user u set u.firstName = ?1, u.lastName = ?2, u.phoneNumber = ?3,
                                    u.address = ?4
                                    WHERE u.userId = ?5
                                    """)
                            .setParameter(1, user.getFirstName())
                            .setParameter(2, user.getLastName())
                            .setParameter(3, user.getPhoneNumber())
                            .setParameter(4, user.getAddress())
                            .setParameter(5, userId);
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
    public void updatePassword(String email, String newPassword) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query update =
                    em.createQuery("""
                                    UPDATE user u set u.password = :pass
                                    WHERE u.email = :email
                                    """)
                            .setParameter("pass", newPassword)
                            .setParameter(EMAIL, email);
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
    public void updateAccountStatus(int userId, AccountStatus accountStatus) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query update =
                    em.createQuery("""
                                    UPDATE user u set u.accountStatus = :status
                                    WHERE u.userId = :id
                                    """)
                            .setParameter("status", accountStatus)
                            .setParameter("id", userId);
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
}
