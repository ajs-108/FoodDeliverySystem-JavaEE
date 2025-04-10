package com.app.model;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private User user;
    private User deliveryPerson;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
    private PaymentStatus paymentStatus;
    private List<OrderFoodItems> orderFoodItems;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(User deliveryPerson) {
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

    public List<OrderFoodItems> getOrderFoodItems() {
        return orderFoodItems;
    }

    public void setOrderFoodItems(List<OrderFoodItems> orderFoodItems) {
        this.orderFoodItems = orderFoodItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", deliveryPerson=" + deliveryPerson +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", orderDateTime=" + orderDateTime +
                ", paymentStatus=" + paymentStatus +
                ", orderFoodItems=" + orderFoodItems +
                '}';
    }
}
