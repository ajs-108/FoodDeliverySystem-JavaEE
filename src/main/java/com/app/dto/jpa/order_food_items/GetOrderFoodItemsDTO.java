package com.app.dto.jpa.order_food_items;

public class GetOrderFoodItemsDTO {
    private String foodName;
    private Double price;
    private Double discount;
    private String imagePath;
    private Short quantity;
    private Double foodItemTotal;

    public GetOrderFoodItemsDTO() {
    }

    public GetOrderFoodItemsDTO(String foodName, Double price, Double discount, String imagePath,
                                Short quantity, Double foodItemTotal) {
        this.foodName = foodName;
        this.price = price;
        this.discount = discount;
        this.imagePath = imagePath;
        this.quantity = quantity;
        this.foodItemTotal = foodItemTotal;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Double getFoodItemTotal() {
        return foodItemTotal;
    }

    public void setFoodItemTotal(Double foodItemTotal) {
        this.foodItemTotal = foodItemTotal;
    }

    @Override
    public String toString() {
        return "GetOrderFoodItemsDTO{" +
                "foodName='" + foodName + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", imagePath='" + imagePath + '\'' +
                ", quantity=" + quantity +
                ", FoodItemTotal=" + foodItemTotal +
                '}';
    }
}
