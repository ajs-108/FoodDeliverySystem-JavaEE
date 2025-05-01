package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.dto.jpa.review.GetReviewDTO;
import com.app.model.jpa.JPAReview;

import java.util.List;

public interface IReviewRepository {
    void save(JPAReview review) throws DBException;

    List<GetReviewDTO> findAll() throws DBException;

    List<GetReviewDTO> findAllReviewOfUser(int userId) throws DBException;

    List<Double> fetchFoodRatings(int foodItemId) throws DBException;

    GetReviewDTO findById(int reviewId) throws DBException;

    GetReviewDTO findByIds(int userId, int foodItemId, int orderId) throws DBException;

    void update(JPAReview review) throws DBException;

    void delete(JPAReview review) throws DBException;
}
