package com.app.common.enums;

import java.util.Objects;

public enum AccountStatus {
    ACTIVATED,
    DEACTIVATED;

    public static AccountStatus toEnum(String status) {
        for (AccountStatus accountStatus : AccountStatus.values()) {
            if (Objects.equals(accountStatus.name(), status)) {
                return accountStatus;
            }
        }
        return null;
    }

    public static boolean isStatusExists(String status) {
        for (AccountStatus accountStatus : AccountStatus.values()) {
            if (Objects.equals(accountStatus.name(), status)) {
                return true;
            }
        }
        return false;
    }
}
