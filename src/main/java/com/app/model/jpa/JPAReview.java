package com.app.model.jpa;

import com.app.model.FoodItem;
import jakarta.persistence.*;


@Entity(name = "review")
@Table(name = "review_rating_table")
public class JPAReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @ManyToOne(targetEntity = JPAUser.class, cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
            nullable = false)
    private JPAUser user;

    @ManyToOne(targetEntity = FoodItem.class, cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "food_item_id", referencedColumnName = "food_item_id",
            nullable = false)
    private FoodItem foodItem;

    @ManyToOne(targetEntity = JPAOrder.class, cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id",
            nullable = false)
    private JPAOrder order;

    @Column(name = "rating")
    private short rating;

    @Column(name = "review", length = 250)
    private String userReview;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public JPAUser getUser() {
        return user;
    }

    public void setUser(JPAUser user) {
        this.user = user;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public JPAOrder getOrder() {
        return order;
    }

    public void setOrder(JPAOrder order) {
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
        return "JPAReview{" +
                "reviewId=" + reviewId +
                ", user=" + user +
                ", foodItem=" + foodItem +
                ", order=" + order +
                ", rating=" + rating +
                ", userReview='" + userReview + '\'' +
                '}';
    }
}
