package com.sqy.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public record TaskNewStatusDto(
        @JsonProperty("id")
        @Schema(description = "Идентификационный номер.")
        long id,

        @JsonProperty("new_task_status")
        @Schema(description = "Новый статус.")
        TaskStatus newTaskStatus
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskNewStatusDto(long id, TaskStatus newTaskStatus) {
        this.id = id;
        this.newTaskStatus = Objects.requireNonNull(newTaskStatus);
    }
}
