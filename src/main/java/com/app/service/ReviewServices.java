package com.app.service;

import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.controller.validation.ReviewValidator;
import com.app.dao.IReviewDAO;
import com.app.dao.impl.ReviewDAOImpl;
import com.app.dto.ReviewDTO;
import com.app.mapper.ReviewMapper;

import java.util.List;

public class ReviewServices {
    private IReviewDAO reviewDAO;
    private ReviewMapper reviewMapper;
    private FoodItemServices foodItemServices;
    private int i;

    public ReviewServices() {
        this.reviewDAO = new ReviewDAOImpl();
        this.reviewMapper = new ReviewMapper();
        this.foodItemServices = new FoodItemServices();
    }

    public void postReview(List<ReviewDTO> reviewDTOList) throws DBException, ApplicationException {
        for (ReviewDTO reviewDTO : reviewDTOList) {
            ReviewValidator.validateReview(reviewDTO);
            i += reviewDAO.addReview(reviewMapper.toReview(reviewDTO));
            if (i > 0) {
                updateFoodRating(reviewDTO.getFoodItemDTO().getFoodItemId());
            }
        }
    }

    private void updateFoodRating(int foodItemId) throws DBException {
        List<Double> ratingList = reviewDAO.getFoodRatings(foodItemId);
        foodItemServices.updateRatings(foodItemId, ratingList);
    }

    public List<ReviewDTO> getAllReviews() throws DBException {
        return reviewDAO.getAllReviews()
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    public List<ReviewDTO> getAllReviewsOfUser(int userId) throws DBException {
        return reviewDAO.getAllReviewsOfUser(userId)
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    public ReviewDTO getReview(int reviewId) throws DBException {
        return reviewMapper.toDTO(reviewDAO.getReview(reviewId));
    }

    public ReviewDTO getReview(int userId, int foodItemId, int orderId) throws DBException {
        return reviewMapper.toDTO(reviewDAO.getReview(userId, foodItemId, orderId));
    }

    public void updateReview(ReviewDTO reviewDTO) throws DBException {
        reviewDAO.updateReview(reviewMapper.toReview(reviewDTO));
    }

    public void deleteReview(int reviewId) throws DBException {
        reviewDAO.deleteReview(reviewId);
    }
}
