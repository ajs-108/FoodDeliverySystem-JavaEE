package com.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDTO {
    private int reviewId;
    private UserDTO userDTO;
    private FoodItemDTO foodItemDTO;
    private int orderId;
    private int rating;
    private String review;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public FoodItemDTO getFoodItemDTO() {
        return foodItemDTO;
    }

    public void setFoodItemDTO(FoodItemDTO foodItemDTO) {
        this.foodItemDTO = foodItemDTO;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String
    toString() {
        return "ReviewDTO{" +
                "reviewId=" + reviewId +
                ", userDTO=" + userDTO +
                ", foodItemDTO=" + foodItemDTO +
                ", orderId=" + orderId +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
