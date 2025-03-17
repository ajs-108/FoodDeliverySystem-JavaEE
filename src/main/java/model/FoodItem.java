package entity;

import java.time.LocalDateTime;

public class FoodItem {

    private int foodItemId;
    private String foodName;
    private String foodDescription;
    private double price;
    private double discount;
    private boolean isAvailable;
    private int categoryId;
//    private LocalDateTime createdOn;
//    private LocalDateTime updatedOn;
    private String imagePath;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
                ", isAvailable=" + isAvailable +
                ", categoryId=" + categoryId +
                ", imagePath='" + imagePath + '\'' +
                ", rating=" + rating +
                '}';
    }
}
