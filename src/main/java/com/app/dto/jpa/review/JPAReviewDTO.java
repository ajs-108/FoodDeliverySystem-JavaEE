package com.app.dto.jpa.review;

import com.app.dto.FoodItemDTO;
import com.app.dto.jpa.JPAUserDTO;
import com.app.dto.jpa.order.JPAOrderDTO;

public class JPAReviewDTO {
    private int reviewId;
    private JPAUserDTO user;
    private FoodItemDTO foodItem;
    private JPAOrderDTO order;
    private short rating;
    private String userReview;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public JPAUserDTO getUser() {
        return user;
    }

    public void setUser(JPAUserDTO user) {
        this.user = user;
    }

    public FoodItemDTO getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItemDTO foodItem) {
        this.foodItem = foodItem;
    }

    public JPAOrderDTO getOrder() {
        return order;
    }

    public void setOrder(JPAOrderDTO order) {
        this.order = order;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    @Override
    public String toString() {
        return "JPAReviewDTO{" +
                "reviewId=" + reviewId +
                ", user=" + user +
                ", foodItem=" + foodItem +
                ", order=" + order +
                ", rating=" + rating +
                ", userReview='" + userReview + '\'' +
                '}';
    }
}
