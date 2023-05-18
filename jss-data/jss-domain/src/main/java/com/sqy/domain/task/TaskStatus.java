package com.sqy.domain.task;

import jakarta.annotation.Nullable;

import java.util.Arrays;

public enum TaskStatus {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    CLOSED;

    @Nullable
    public static TaskStatus getProjectMemberRole(@Nullable String taskStatus) {
        if (taskStatus == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(status -> status.name().equals(taskStatus))
                .findAny()
                .orElse(null);
    }
}
