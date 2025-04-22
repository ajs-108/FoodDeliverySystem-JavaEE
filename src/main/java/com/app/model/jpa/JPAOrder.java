package com.app.model.jpa;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.model.FoodItem;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "order")
@Table(name = "order_")
public class JPAOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @ManyToOne(targetEntity = JPAUser.class, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private JPAUser user;

    @ManyToOne(targetEntity = JPAUser.class, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_person_id", referencedColumnName = "user_id", insertable = false)
    private JPAUser deliveryPerson;

    @Column(name = "total_price", nullable = false, insertable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_date_time")
    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @ManyToMany(targetEntity = FoodItem.class, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_food_items",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_item_id", referencedColumnName = "food_item_id")
    )
    private List<FoodItem> foodItems;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public JPAUser getUser() {
        return user;
    }

    public void setUser(JPAUser user) {
        this.user = user;
    }

    public JPAUser getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(JPAUser deliveryPerson) {
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

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @Override
    public String toString() {
        return "JPAOrder{" +
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
