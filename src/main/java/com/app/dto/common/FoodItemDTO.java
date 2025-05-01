package com.app.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodItemDTO {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int foodItemId;
    private String foodName;
    private String foodDescription;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double price;
    private double discount;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean isAvailable;
    private CategoryDTO category;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String imagePath;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double rating;

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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
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

    @Override
    public String toString() {
        return "FoodItem{" +
                "foodItemId=" + foodItemId +
                ", foodName='" + foodName + '\'' +
                ", foodDescription='" + foodDescription + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", getIsAvailable=" + isAvailable +
                ", categoryDTO=" + category +
                ", imagePath='" + imagePath + '\'' +
                ", rating=" + rating +
                '}';
    }
}
