package com.app.model.common;

import com.app.model.jpa.JPAOrder;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "foodItem")
@Table(name = "food_item")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_item_id", nullable = false)
    private int foodItemId;

    @Column(name = "food_name", nullable = false, length = 30)
    private String foodName;

    @Column(name = "food_description", nullable = false)
    private String foodDescription;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "discount")
    private double discount;

    @Column(name = "is_available", nullable = false,
            columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isAvailable;

    @ManyToOne(targetEntity = Category.class, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id",
            nullable = false)
    private Category category;

    @Column(name = "created_on", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdOn;

    @Column(name = "updated_on",
            columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedOn;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "rating", nullable = false,
            columnDefinition = "DOUBLE DEFAULT 1")
    private double rating;

    @ManyToMany(mappedBy = "foodItems")
    private List<JPAOrder> order;

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn == null ? null : createdOn.toLocalDateTime();
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn == null ? null : updatedOn.toLocalDateTime();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<JPAOrder> getOrder() {
        return order;
    }

    public void setOrder(List<JPAOrder> order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "foodItemId=" + foodItemId +
                ", foodName='" + foodName + '\'' +
                ", foodDescription='" + foodDescription + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", getIsAvailable=" + isAvailable +
                ", category=" + category +
                ", imagePath='" + imagePath + '\'' +
                ", rating=" + rating +
                '}';
    }
}
