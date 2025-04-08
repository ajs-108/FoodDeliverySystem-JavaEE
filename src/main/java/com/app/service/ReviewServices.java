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

    public ReviewServices() {
        this.reviewDAO = new ReviewDAOImpl();
        this.reviewMapper = new ReviewMapper();
        this.foodItemServices = new FoodItemServices();
    }

    public void postReview(List<ReviewDTO> reviewDTOList) throws DBException, ApplicationException {
        for (ReviewDTO reviewDTO : reviewDTOList) {
            ReviewValidator.validateReview(reviewDTO);
            reviewDAO.addReview(reviewMapper.toReview(reviewDTO));
            foodItemServices.updateRatings(reviewDTO.getFoodItemDTO().getFoodItemId(), reviewDTO.getRating());
        }
    }

    public List<ReviewDTO> getAllReview() throws DBException {
        return reviewDAO.getAllReview()
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    public ReviewDTO getReview(int reviewId) throws DBException {
        return reviewMapper.toDTO(reviewDAO.getReview(reviewId));
    }

    public void updateReview(ReviewDTO reviewDTO) throws DBException {
        reviewDAO.updateReview(reviewMapper.toReview(reviewDTO));
    }

    public void deleteReview(int reviewId) throws DBException {
        reviewDAO.deleteReview(reviewId);
    }
}
