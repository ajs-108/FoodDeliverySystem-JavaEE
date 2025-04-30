package com.app.dto.jpa.order;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.dto.FoodItemDTO;
import com.app.dto.jpa.JPAUserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class JPAOrderDTO {
    private int orderId;
    private JPAUserDTO user;
    private JPAUserDTO deliveryPerson;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
    private PaymentStatus paymentStatus;
    private List<FoodItemDTO> foodItems;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public JPAUserDTO getUser() {
        return user;
    }

    public void setUser(JPAUserDTO user) {
        this.user = user;
    }

    public JPAUserDTO getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(JPAUserDTO deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<FoodItemDTO> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItemDTO> foodItems) {
        this.foodItems = foodItems;
    }

    @Override
    public String toString() {
        return "JPAOrderDTO{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", deliveryPerson=" + deliveryPerson +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", orderDateTime=" + orderDateTime +
                ", paymentStatus=" + paymentStatus +
                ", foodItems=" + foodItems +
                '}';
    }
}
