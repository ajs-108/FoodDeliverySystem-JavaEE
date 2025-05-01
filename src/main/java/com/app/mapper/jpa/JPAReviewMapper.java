package com.app.mapper.jpa;

import com.app.dto.jpa.review.JPAReviewDTO;
import com.app.mapper.common.FoodItemMapper;
import com.app.model.jpa.JPAReview;

public class JPAReviewMapper {
    private JPAUserMapper userMapper = new JPAUserMapper();
    private FoodItemMapper foodItemMapper = new FoodItemMapper();
    private JPAOrderMapper orderMapper = new JPAOrderMapper();

    public JPAReviewDTO toDTO(JPAReview review) {
        JPAReviewDTO reviewDTO = new JPAReviewDTO();
        if (review == null) {
            return null;
        }
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setUser(userMapper.toDTO(review.getUser()));
        reviewDTO.setFoodItem(foodItemMapper.toDTO(review.getFoodItem()));
        reviewDTO.setOrder(orderMapper.toDTO(review.getOrder()));
        reviewDTO.setRating(review.getRating());
        reviewDTO.setUserReview(review.getUserReview());
        return reviewDTO;
    }

    public JPAReview toReview(JPAReviewDTO reviewDTO) {
        JPAReview review = new JPAReview();
        if (reviewDTO == null) {
            return null;
        }
        review.setReviewId(reviewDTO.getReviewId());
        review.setUser(userMapper.toUser(reviewDTO.getUser()));
        review.setFoodItem(foodItemMapper.toFoodItem(reviewDTO.getFoodItem()));
        review.setOrder(orderMapper.toOrder(reviewDTO.getOrder()));
        review.setRating(reviewDTO.getRating());
        review.setUserReview(reviewDTO.getUserReview());
        return review;
    }
}
