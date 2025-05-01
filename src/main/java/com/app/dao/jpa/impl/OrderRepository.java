package com.app.dao.jpa.impl;

import com.app.common.enums.OrderStatus;
import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IOrderRepository;
import com.app.dto.jpa.order.GetOrderDTO;
import com.app.model.jpa.JPAOrder;
import com.app.model.jpa.JPAUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderRepository implements IOrderRepository {

    @Override
    public Integer save(JPAOrder order) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(order);
            tx.commit();
            return order.getOrderId();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<GetOrderDTO> findAll() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findAllQuery =
                    em.createQuery("""
                            select o.orderId, u.userId, u.firstName, u.lastName, dp.userId,
                            dp.firstName, dp.lastName, o.totalPrice, o.orderStatus, o.orderDateTime,
                            o.paymentStatus
                            from order o
                            join o.user u
                            join o.deliveryPerson dp
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

    @Override
    public List<GetOrderDTO> findAll(OrderStatus orderStatus) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findAllQuery =
                    em.createQuery("""
                                    select o.orderId, u.userId, u.firstName, u.lastName, dp.userId,
                                    dp.firstName, dp.lastName, o.totalPrice, o.orderStatus, o.orderDateTime,
                                    o.paymentStatus
                                    from order o
                                    join o.user u
                                    join o.deliveryPerson dp
                                    where o.orderStatus = ?1
                                    """, GetOrderDTO.class)
                            .setParameter(1, orderStatus);
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

    @Override
    public List<GetOrderDTO> findAllPreviousOrdersOfUser(int userId, int roleId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findAllQuery =
                    em.createQuery("""
                                    select o.orderId, u.userId, u.firstName, u.lastName, dp.userId,
                                    dp.firstName, dp.lastName, o.totalPrice, o.orderStatus, o.orderDateTime,
                                    o.paymentStatus
                                    from order o
                                    join o.user u
                                    join o.deliveryPerson dp
                                    where u.userId = ?1 and u.role.roleId = ?2
                                    """, GetOrderDTO.class)
                            .setParameter(1, userId)
                            .setParameter(2, roleId);
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

    @Override
    public JPAOrder find(int orderId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            JPAOrder order = em.find(JPAOrder.class, orderId);
            tx.commit();
            return order;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public GetOrderDTO findById(int orderId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findQuery =
                    em.createQuery("""
                                    select o.orderId, u.userId, u.firstName, u.lastName, dp.userId,
                                    dp.firstName, dp.lastName, o.totalPrice, o.orderStatus, o.orderDateTime,
                                    o.paymentStatus
                                    from order o
                                    join o.user u
                                    join o.deliveryPerson dp
                                    where o.orderId = ?1
                                    """, GetOrderDTO.class)
                            .setParameter(1, orderId);
            tx.begin();
            GetOrderDTO order = findQuery.getSingleResult();
            tx.commit();
            return order;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public GetOrderDTO findRecentOrderOfUser(int userId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findQuery =
                    em.createQuery("""
                                    select o.orderId, o.totalPrice
                                    from order o
                                    join o.user u
                                    where o.orderStatus = "ORDER_RECEIVED" and o.user.userId = ?1
                                    order by o.orderId desc
                                    """, GetOrderDTO.class)
                            .setParameter(1, userId)
                            .setMaxResults(1);
            tx.begin();
            GetOrderDTO order = findQuery.getSingleResult();
            tx.commit();
            return order;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<GetOrderDTO> findOrdersAssignedToDP(int deliveryPersonId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findQuery =
                    em.createQuery("""
                                    select o.orderId, u.userId, u.firstName, u.lastName, dp.userId,
                                    o.totalPrice, o.orderStatus, o.orderDateTime, o.paymentStatus
                                    from order o
                                    join o.user u
                                    join o.deliveryPerson dp
                                    where o.deliveryPerson.userId = ?1
                                    """, GetOrderDTO.class)
                            .setParameter(1, deliveryPersonId);
            tx.begin();
            List<GetOrderDTO> orderList = findQuery.getResultList();
            tx.commit();
            return orderList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<GetOrderDTO> findCurrentOrdersOfUser(int userId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetOrderDTO> findQuery =
                    em.createQuery("""
                                    select o.orderId, u.userId, u.firstName, u.lastName, dp.userId,
                                    dp.firstName, dp.lastName, o.totalPrice, o.orderStatus,
                                    o.orderDateTime, o.paymentStatus
                                    from order o
                                    join o.user u
                                    join o.deliveryPerson dp
                                    where o.orderStatus != "DELIVERED" and o.orderStatus != "CANCELLED"
                                    and  o.user.userId = ?1
                                    """, GetOrderDTO.class)
                            .setParameter(1, userId);
            tx.begin();
            List<GetOrderDTO> orderList = findQuery.getResultList();
            tx.commit();
            return orderList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void updateStatus(int orderId, OrderStatus orderStatus) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query updateStatusQuery =
                    em.createQuery("""
                                    update order o set o.orderStatus = ?1
                                    where o.orderId = ?2
                                    """)
                            .setParameter(1, orderStatus)
                            .setParameter(2, orderId);
            tx.begin();
            updateStatusQuery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void assignDeliveryPerson(int orderId, JPAUser deliveryPerson) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            Query updateDPQuery =
                    em.createQuery("""
                                    update order o set o.deliveryPerson = ?1
                                    where o.orderId = ?2
                                    """)
                            .setParameter(1, deliveryPerson)
                            .setParameter(2, orderId);
            tx.begin();
            updateDPQuery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }
}
