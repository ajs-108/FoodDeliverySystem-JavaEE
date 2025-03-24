package com.app.model;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private User customer;
    private User deliveryPerson;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
    private PaymentStatus paymentStatus;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + customer +
                ", deliveryPerson=" + deliveryPerson +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", order_date_time=" + orderDateTime +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
