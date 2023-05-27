package com.sqy.dto.task;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.task.TaskStatus;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Set;

public record TaskFilterDto(
        @Nullable String value,
        @Nullable Set<TaskStatus> permissibleStatuses,
        @Nullable LocalDateTime startOfPeriod,
        @Nullable LocalDateTime endOfPeriod,
        @Nullable Long performerId,
        @Nullable Long authorId
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskFilterDto(@JsonProperty("value") @Nullable String value,
                         @JsonProperty("statuses") @Nullable Set<TaskStatus> permissibleStatuses,
                         @JsonProperty("start_period") @Nullable LocalDateTime startOfPeriod,
                         @JsonProperty("end_period") @Nullable LocalDateTime endOfPeriod,
                         @JsonProperty("performer_id") @Nullable Long performerId,
                         @JsonProperty("author_id") @Nullable Long authorId) {
        this.value = value;
        this.permissibleStatuses = permissibleStatuses;
        this.endOfPeriod = endOfPeriod;
        this.startOfPeriod = startOfPeriod;
        this.performerId = performerId;
        this.authorId = authorId;
    }
}
