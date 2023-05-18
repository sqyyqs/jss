package com.sqy.domain.project;

import jakarta.annotation.Nullable;

import java.util.Arrays;

public enum ProjectStatus {
    DRAFT,
    IN_PROGRESS,
    IN_TESTING,
    COMPLETED;

    @Nullable
    public static ProjectStatus getTaskType(@Nullable String name) {
        if (name == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(status -> status.name().equals(name))
                .findAny()
                .orElse(null);
    }
}
