package com.app.dto.jdbc;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int orderId;
    private UserDTO user;
    private UserDTO deliveryPerson;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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

    public UserDTO getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(UserDTO deliveryPerson) {
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
                ", deliveryPerson=" + deliveryPerson +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", orderDateTime=" + orderDateTime +
                ", paymentStatus=" + paymentStatus +
                ", orderFoodItems=" + orderFoodItems +
                '}';
    }
}
