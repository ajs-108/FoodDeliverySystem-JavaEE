package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.Review;

import java.util.List;

public interface IReviewDAO {
    void addReview(Review review) throws DBException;

    List<Review> getAllReview() throws DBException;

    Review getReview(int reviewId) throws DBException;
}
