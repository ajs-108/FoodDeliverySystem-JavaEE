package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.IReviewRepository;
import com.app.dto.jpa.review.GetReviewDTO;
import com.app.model.jpa.JPAReview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ReviewRepository implements IReviewRepository {
    @Override
    public void save(JPAReview review) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(review);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<GetReviewDTO> findAll() throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetReviewDTO> findAllQuery =
                    em.createQuery("""
                            select r.reviewId, u.userId, u.firstName, fi.foodItemId,
                            fi.foodName, fi.isAvailable, fi.imagePath, o.orderId,
                            r.rating, r.userReview
                            from review r
                            join r.user u
                            join r.foodItem fi
                            join r.order o
                            """, GetReviewDTO.class);
            tx.begin();
            List<GetReviewDTO> reviewList = findAllQuery.getResultList();
            tx.commit();
            return reviewList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<GetReviewDTO> findAllReviewOfUser(int userId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetReviewDTO> findAllQuery =
                    em.createQuery("""
                                    select r.reviewId, u.userId, u.firstName, fi.foodItemId,
                                    fi.foodName, fi.isAvailable, fi.imagePath, o.orderId,
                                    r.rating, r.userReview
                                    from review r
                                    join r.user u
                                    join r.foodItem fi
                                    join r.order o
                                    where u.userId = ?1
                                    """, GetReviewDTO.class)
                            .setParameter(1, userId);
            tx.begin();
            List<GetReviewDTO> reviewList = findAllQuery.getResultList();
            tx.commit();
            return reviewList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<Double> fetchFoodRatings(int foodItemId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<Double> findRatingsQuery =
                    em.createQuery("""
                                    select r.rating
                                    from review r
                                    join r.foodItem fi
                                    where fi.foodItemId = ?1
                                    """, Double.class)
                            .setParameter(1, foodItemId);
            tx.begin();
            List<Double> ratingList = findRatingsQuery.getResultList();
            tx.commit();
            return ratingList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public GetReviewDTO findById(int reviewId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetReviewDTO> findQuery =
                    em.createQuery("""
                                    select r.reviewId, u.userId, u.firstName, fi.foodItemId,
                                    fi.foodName, fi.isAvailable, fi.imagePath, o.orderId,
                                    r.rating, r.userReview
                                    from review r
                                    join r.user u
                                    join r.foodItem fi
                                    join r.order o
                                    where r.reviewId = ?1
                                    """, GetReviewDTO.class)
                            .setParameter(1, reviewId);
            tx.begin();
            GetReviewDTO review = findQuery.getSingleResult();
            tx.commit();
            return review;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public GetReviewDTO findByIds(int userId, int foodItemId, int orderId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            TypedQuery<GetReviewDTO> findQuery =
                    em.createQuery("""
                                    select r.reviewId, r.rating
                                    from review r
                                    join r.user u
                                    join r.foodItem fi
                                    join r.order o
                                    where u.userId = ?1 and fi.foodItemId = ?2 and o.orderId = ?3
                                    """, GetReviewDTO.class)
                            .setParameter(1, userId)
                            .setParameter(2, foodItemId)
                            .setParameter(3, orderId);
            tx.begin();
            GetReviewDTO review = findQuery.getSingleResult();
            tx.commit();
            return review;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void update(JPAReview review) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.merge(review);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void delete(JPAReview review) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.remove(review);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }
}
