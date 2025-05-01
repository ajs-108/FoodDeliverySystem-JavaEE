package com.app.dao.jdbc;

import com.app.common.exception.DBException;
import com.app.model.jdbc.Review;

import java.util.List;

public interface IReviewDAO {
    int addReview(Review review) throws DBException;

    List<Review> getAllReviews() throws DBException;

    List<Review> getAllReviewsOfUser(int userId) throws DBException;

    List<Double> getFoodRatings(int foodItemId) throws DBException;

    Review getReview(int reviewId) throws DBException;

    Review getReview(int userId, int foodItemId, int orderId) throws DBException;

    void updateReview(Review review) throws DBException;

    void deleteReview(int reviewId) throws DBException;
}
