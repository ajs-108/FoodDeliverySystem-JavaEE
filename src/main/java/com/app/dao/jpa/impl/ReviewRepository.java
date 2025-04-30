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
        return null;
    }

    @Override
    public List<Double> fetchFoodRatings(int foodItemId) throws DBException {
        return null;
    }

    @Override
    public GetReviewDTO findById(int reviewId) throws DBException {
        return null;
    }

    @Override
    public GetReviewDTO findByIds(int userId, int foodItemId, int orderId) throws DBException {
        return null;
    }

    @Override
    public void update(JPAReview review) throws DBException {

    }

    @Override
    public void delete(int reviewId) throws DBException {

    }
}
