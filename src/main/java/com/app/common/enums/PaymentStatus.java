package com.app.common.enums;

import java.util.Objects;

public enum PaymentStatus {
    PAID,
    NOT_PAID;

    public static PaymentStatus toEnum(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (Objects.equals(paymentStatus.name(), status)) {
                return paymentStatus;
            }
        }
        return null;
    }

    public static boolean isPaymentStatus(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (Objects.equals(paymentStatus.name(), status)) {
                return true;
            }
        }
        return false;
    }
}
