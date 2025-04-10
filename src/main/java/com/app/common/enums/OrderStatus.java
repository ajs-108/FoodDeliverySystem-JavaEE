package com.app.common.enums;

import java.util.List;
import java.util.Objects;

public enum OrderStatus {
    ORDER_RECEIVED,
    PREPARING,
    READY_FOR_DELIVERY,
    NOT_ASSIGNED,
    ASSIGNED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    public static OrderStatus toEnum(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (Objects.equals(orderStatus.name(), status)) {
                return orderStatus;
            }
        }
        return null;
    }

    public static boolean isOrderStatus(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (Objects.equals(orderStatus.name(), status)) {
                return true;
            }
        }
        return false;
    }

    public static List<OrderStatus> getAllOrderStatus() {
         return List.of(OrderStatus.values());
    }
}
