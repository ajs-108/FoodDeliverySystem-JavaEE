package com.app.dto.jpa.order;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.dto.jpa.order_food_items.GetOrderFoodItemsDTO;

import java.time.LocalDateTime;
import java.util.List;

public class GetOrderDTO {
    private Integer orderId;
    private Integer userId;
    private String firstName;
    private String lastNAme;
    private Integer deliveryPersonId;
    private String dpFirstName;
    private String dpLastName;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTimes;
    private PaymentStatus paymentStatus;
    private List<GetOrderFoodItemsDTO> orderFoodItems;

    public GetOrderDTO() {
    }

    public GetOrderDTO(Integer orderId, Integer userId, String firstName, String lastNAme,
                       Integer deliveryPersonId, String dpFirstName, String dpLastName,
                       Double totalPrice, OrderStatus orderStatus, LocalDateTime orderDateTimes,
                       PaymentStatus paymentStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastNAme = lastNAme;
        this.deliveryPersonId = deliveryPersonId;
        this.dpFirstName = dpFirstName;
        this.dpLastName = dpLastName;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDateTimes = orderDateTimes;
        this.paymentStatus = paymentStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNAme() {
        return lastNAme;
    }

    public void setLastNAme(String lastNAme) {
        this.lastNAme = lastNAme;
    }

    public Integer getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(Integer deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public String getDpFirstName() {
        return dpFirstName;
    }

    public void setDpFirstName(String dpFirstName) {
        this.dpFirstName = dpFirstName;
    }

    public String getDpLastName() {
        return dpLastName;
    }

    public void setDpLastName(String dpLastName) {
        this.dpLastName = dpLastName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDateTimes() {
        return orderDateTimes;
    }

    public void setOrderDateTimes(LocalDateTime orderDateTimes) {
        this.orderDateTimes = orderDateTimes;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<GetOrderFoodItemsDTO> getOrderFoodItems() {
        return orderFoodItems;
    }

    public void setOrderFoodItems(List<GetOrderFoodItemsDTO> orderFoodItems) {
        this.orderFoodItems = orderFoodItems;
    }

    @Override
    public String toString() {
        return "GetOrderDTO{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastNAme='" + lastNAme + '\'' +
                ", deliveryPersonId=" + deliveryPersonId +
                ", dpFirstName='" + dpFirstName + '\'' +
                ", dpLastName='" + dpLastName + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", orderDateTimes=" + orderDateTimes +
                ", paymentStatus=" + paymentStatus +
                ", orderFoodItems=" + orderFoodItems +
                '}';
    }
}
