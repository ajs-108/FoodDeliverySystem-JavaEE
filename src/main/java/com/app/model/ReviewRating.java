package com.app.model;

public class ReviewRating {
    private int reviewId;
    private User user;
    private FoodItem foodItem;
    private int orderId;
    private int rating;
    private String review;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
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
    public String toString() {
        return "ReviewRating{" +
                "reviewId=" + reviewId +
                ", user=" + user +
                ", foodItem=" + foodItem +
                ", orderId=" + orderId +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
