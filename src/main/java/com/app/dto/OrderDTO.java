package com.app.dto;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL,
        content = JsonInclude.Include.NON_DEFAULT)
public class OrderDTO {
    private int orderId;
    private UserDTO user;
    private int deliveryPersonId;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
    private PaymentStatus paymentStatus;
    private List<OrderFoodItemsDTO> orderFoodItems;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(int deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
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

    public List<OrderFoodItemsDTO> getOrderFoodItems() {
        return orderFoodItems;
    }

    public void setOrderFoodItems(List<OrderFoodItemsDTO> orderFoodItems) {
        this.orderFoodItems = orderFoodItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", deliveryPersonId=" + deliveryPersonId +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", orderDateTime=" + orderDateTime +
                ", paymentStatus=" + paymentStatus +
                ", orderFoodItems=" + orderFoodItems +
                '}';
    }
}
