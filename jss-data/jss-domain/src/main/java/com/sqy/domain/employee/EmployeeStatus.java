package com.sqy.domain.employee;

import jakarta.annotation.Nullable;

import java.util.Arrays;

public enum EmployeeStatus {
    ACTIVE,
    DELETED;

    @Nullable
    public static EmployeeStatus getEmployeeStatus(@Nullable String statusName) {
        if (statusName == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(status -> status.name().equals(statusName))
                .findAny()
                .orElse(null);
    }
}
