package com.app.mapper;

import com.app.dto.ReviewDTO;
import com.app.model.Review;

public class ReviewMapper {
    private UserMapper userMapper = new UserMapper();
    private FoodItemMapper foodItemMapper = new FoodItemMapper();

    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        if (review == null) {
            return null;
        }
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setOrderId(review.getOrderId());
        reviewDTO.setUserDTO(userMapper.toDTO(review.getUser()));
        reviewDTO.setFoodItemDTO(foodItemMapper.toDTO(review.getFoodItem()));
        reviewDTO.setRating(review.getRating());
        reviewDTO.setReview(review.getUserReview());
        return reviewDTO;
    }

    public Review toReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        if (reviewDTO == null) {
            return null;
        }
        review.setReviewId(reviewDTO.getReviewId());
        review.setOrderId(reviewDTO.getOrderId());
        review.setUser(userMapper.toUser(reviewDTO.getUserDTO()));
        review.setFoodItem(foodItemMapper.toFoodItem(reviewDTO.getFoodItemDTO()));
        review.setRating(reviewDTO.getRating());
        review.setUserReview(reviewDTO.getReview());
        return review;
    }
}
