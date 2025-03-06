package entity;

import java.time.LocalDateTime;

public class FoodItem {

    private int foodItemId;
    private String foodName;
    private String foodDescription;
    private double price;
    private int gram;
    private String size;
    private double discount;
    private boolean isAvailable;
    private Category category;
//    private LocalDateTime createdOn;
//    private LocalDateTime updatedOn;
    private String imagePath;
    private double rating;

    public FoodItem() {
    }

    public FoodItem(int foodItemId, String foodName, String foodDescription, double price, int gram, String size, double discount, boolean isAvailable, Category category, String imagePath, double rating) {
        this.foodItemId = foodItemId;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.price = price;
        this.gram = gram;
        this.size = size;
        this.discount = discount;
        this.isAvailable = isAvailable;
        this.category = category;
        this.imagePath = imagePath;
        this.rating = rating;
    }

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

    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
                ", gram=" + gram +
                ", size='" + size + '\'' +
                ", discount=" + discount +
                ", isAvailable=" + isAvailable +
                ", imagePath='" + imagePath + '\'' +
                ", rating=" + rating +
                '}';
    }
}
