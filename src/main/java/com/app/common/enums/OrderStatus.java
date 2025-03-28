package com.app.common.enums;

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
}
