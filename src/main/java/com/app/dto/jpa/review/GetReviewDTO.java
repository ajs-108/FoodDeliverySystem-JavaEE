package com.app.dto.jpa.review;

public class GetReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private String firstName;
    private Integer foodItemId;
    private String foodName;
    private Boolean isAvailable;
    private String imagePath;
    private Integer orderId;
    private Short rating;
    private String userReview;

    public GetReviewDTO() {
    }

    public GetReviewDTO(Integer reviewId, Short rating) {
        this.reviewId = reviewId;
        this.rating = rating;
    }

    public GetReviewDTO(Integer reviewId, Integer userId, String firstName, Integer foodItemId, String foodName,
                        Boolean isAvailable, String imagePath, Integer orderId, Short rating, String userReview) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.firstName = firstName;
        this.foodItemId = foodItemId;
        this.foodName = foodName;
        this.isAvailable = isAvailable;
        this.imagePath = imagePath;
        this.orderId = orderId;
        this.rating = rating;
        this.userReview = userReview;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
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
        return "GetReviewDTO{" +
                "reviewId=" + reviewId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", foodItemId=" + foodItemId +
                ", foodName='" + foodName + '\'' +
                ", isAvailable=" + isAvailable +
                ", imagePath='" + imagePath + '\'' +
                ", orderId=" + orderId +
                ", rating=" + rating +
                ", userReview='" + userReview + '\'' +
                '}';
    }
}
