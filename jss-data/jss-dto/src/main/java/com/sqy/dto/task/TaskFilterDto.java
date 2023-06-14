package com.sqy.dto.task;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Set;

public record TaskFilterDto(
        @Nullable
        @JsonProperty("value")
        @Schema(description = "Значения для поиска в названиях(необязательно).")
        String value,

        @Nullable
        @JsonProperty("statuses")
        @Schema(description = "Набор допустимых для поиска статусов задач(необязательно).")
        Set<TaskStatus> permissibleStatuses,

        @Nullable
        @JsonProperty("start_period")
        @Schema(description = "Начало отсчета поиска(необязательно).")
        LocalDateTime startOfPeriod,

        @Nullable
        @JsonProperty("end_period")
        @Schema(description = "Конец отсчета поиска(необязательно).")
        LocalDateTime endOfPeriod,

        @Nullable
        @JsonProperty("performer_id")
        @Schema(description = "Идентификационный номер исполнителя(необязательно).")
        Long performerId,

        @Nullable
        @JsonProperty("author_id")
        @Schema(description = "Идентификационный номер автора(необязательно).")
        Long authorId
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskFilterDto(@Nullable String value, @Nullable Set<TaskStatus> permissibleStatuses,
                         @Nullable LocalDateTime startOfPeriod, @Nullable LocalDateTime endOfPeriod,
                         @Nullable Long performerId, @Nullable Long authorId) {
        this.value = value;
        this.permissibleStatuses = permissibleStatuses;
        this.endOfPeriod = endOfPeriod;
        this.startOfPeriod = startOfPeriod;
        this.performerId = performerId;
        this.authorId = authorId;
    }
}
